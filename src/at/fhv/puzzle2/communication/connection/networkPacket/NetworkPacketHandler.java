package at.fhv.puzzle2.communication.connection.networkPacket;

import at.fhv.puzzle2.communication.ConnectionClosedException;
import at.fhv.puzzle2.communication.NetworkConnectionManager;
import at.fhv.puzzle2.communication.connection.MalformedNetworkPacketException;
import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.logging.Logger;

import java.io.IOException;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkPacketHandler {
    private static final String TAG = "communication.NetworkPacketHandler";

    private volatile static int _sequenceID = 2333;
    private final NetworkConnection _networkConnection;
    private final ConnectionSendQueue _sendQueue;

    public NetworkPacketHandler(NetworkConnection networkConnection, NetworkConnectionManager networkConnectionManager) {
        _networkConnection = networkConnection;

        _sendQueue = new ConnectionSendQueue(networkConnectionManager);
    }

    public void sendMessage(String message) {
        NetworkPacket packet = new NetworkPacket(NetworkPacketHandler.getNextSequenceID(), null, message);
        sendMessage(packet, true);
    }

    public void sendMessage(NetworkPacket packet, boolean resend) {
        _sendQueue.enqueuePacket(packet);

        if(resend) {
            //Store it inside NetworkPacketManager, which waits for an acknowledge or else sends the message again
            NetworkPacketManager.getInstance().sentNetworkPacket(packet, this);
        }
    }

    public String receiveMessage() throws IOException {
        while(true) {
            NetworkPacket packet = readMessage();

            if(packet.getNetworkFlags() != null) {
                //So it should be an acknowledge, an error or the connection gets closed
                if(packet.getNetworkFlags().isAcknowledgePresent()) {
                    NetworkPacketManager.getInstance().receivedAcknowledge(packet);

                } else if(packet.getNetworkFlags().isClosePresent() && packet.getNetworkFlags().getClose()) {
                    _networkConnection.close();
                }
            } else {
                NetworkPacketFlags flags = new NetworkPacketFlags();
                flags.setAcknowledge(true);

                NetworkPacket response = NetworkPacket.createResponse(packet.getSequenceID(), flags);
                this.sendMessage(response, false);

                Logger.getLogger().debug(TAG, "Sending acknowledge true for sequence id " + packet.getSequenceID());

                return packet.getApplicationMessage();
            }
        }
    }

    private NetworkPacket readMessage() throws IOException {
        NetworkPacket packet;

        while(true) {
            try {
                String read = new String(_networkConnection.readBytes(), Charset.forName("UTF-8"));

                Logger.getLogger().trace(TAG, "Received network packet: " + read);

                packet = NetworkPacket.parse(read.getBytes());

                if(packet.checkSumCorrect()) {
                    return packet;
                }

                if(packet.getNetworkFlags() != null) {
                    continue;
                }

                NetworkPacketFlags flags = new NetworkPacketFlags();

                flags.setAcknowledge(false);
                NetworkPacket response = NetworkPacket.createResponse(packet.getSequenceID(), flags);

                Logger.getLogger().debug(TAG, "Checksum failed for sequence id " + packet.getSequenceID());

                this.sendMessage(response, false);
            } catch(MalformedNetworkPacketException e) {
                Logger.getLogger().debug(TAG, "Malformed packet received: " + e.getMessage());
            }
        }
    }

    private static int getNextSequenceID() {
        return _sequenceID++;
    }

    public void close() throws IOException {
        _networkConnection.close();

        _sendQueue.closeSendQueue();
    }

    public NetworkConnection getUnderlyingConnection() {
        return _networkConnection;
    }


    class ConnectionSendQueue implements Runnable {
        private static final String TAG = "communication.ConnectionSendQueue";

        private final BlockingQueue<NetworkPacket> _sendQueue;
        private volatile boolean _isRunning = true;
        private final Thread _localThread;

        private final NetworkConnectionManager _connectionManager;

        public ConnectionSendQueue(NetworkConnectionManager connectionManager) {
            _connectionManager = connectionManager;

            _sendQueue = new LinkedBlockingQueue<>();

            _localThread = new Thread(this);
            _localThread.start();
        }

        public void closeSendQueue() {
            _isRunning = false;

            _localThread.interrupt();
        }

        public void enqueuePacket(NetworkPacket packet) {
            if(!_sendQueue.offer(packet)) {
                Logger.getLogger().warn(TAG, "We lost packets, why?!");
            }
        }

        @Override
        public void run() {
            while(_isRunning) {
                try {
                    NetworkPacket packet = _sendQueue.take();

                    try {
                        Logger.getLogger().trace(TAG, "Sending network packet: " + packet.toJSONString());

                        _networkConnection.sendBytes(packet.getBytes());
                    } catch (IOException e) {
                        if(e instanceof ConnectionClosedException || e instanceof SocketException) {
                            _connectionManager.connectionClosed(_networkConnection);
                        } else {
                            e.printStackTrace();
                        }

                        break;
                    }
                } catch (InterruptedException e) {
                    //Do nothing here
                }
            }
        }
    }
}
