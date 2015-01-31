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
    private final NetworkConnectionManager _networkConnectionManager;
    private final ConnectionSendQueue _sendQueue;

    public NetworkPacketHandler(NetworkConnection networkConnection, NetworkConnectionManager networkConnectionManager) {
        _networkConnection = networkConnection;
        _networkConnectionManager = networkConnectionManager;

        _sendQueue = new ConnectionSendQueue(this);
    }

    public void sendMessage(String message) {
        NetworkPacket packet = new NetworkPacket(NetworkPacketHandler.getNextSequenceID(), null, message, true);
        sendMessage(packet);
    }

    public void sendMessage(NetworkPacket packet) {
        _sendQueue.enqueuePacket(packet);
    }

    public String receiveMessage() {
        try {
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
                    this.sendDirectMessage(response);

                    return packet.getApplicationMessage();
                }
            }
        } catch(IOException e) {
            _networkConnectionManager.connectionClosed(_networkConnection);

            return null;
        }
    }

    private void sendDirectMessage(NetworkPacket response) {
        try {
            _networkConnection.sendBytes(response.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private NetworkPacket readMessage() throws IOException {
        NetworkPacket packet;

        while(true) {
            try {
                String read = new String(_networkConnection.readBytes(), Charset.forName("UTF-8"));

                packet = NetworkPacket.parse(read.getBytes());

                Logger.getLogger().trace(TAG, "Received network packet:", packet);

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

                //this.sendMessage(response, false);
                this.sendDirectMessage(response);
            } catch(MalformedNetworkPacketException e) {
                Logger.getLogger().debug(TAG, e.getMessage());
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
        private final NetworkPacketHandler _packetHandler;

        public ConnectionSendQueue(NetworkPacketHandler packetHandler) {
            _packetHandler = packetHandler;

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
                        Logger.getLogger().trace(TAG, "Sending network packet:", packet);

                        _networkConnection.sendBytes(packet.getBytes());

                        if(packet.shouldResend()) {
                            NetworkPacketManager.getInstance().sentNetworkPacket(packet, _packetHandler);
                        }
                    } catch (IOException e) {
                        _networkConnectionManager.connectionClosed(_networkConnection);
                        if(!(e instanceof ConnectionClosedException || e instanceof SocketException)) {
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
