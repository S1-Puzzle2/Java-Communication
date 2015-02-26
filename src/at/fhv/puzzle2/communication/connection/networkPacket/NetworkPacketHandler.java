package at.fhv.puzzle2.communication.connection.networkPacket;

import at.fhv.puzzle2.communication.ConnectionClosedException;
import at.fhv.puzzle2.communication.NetworkConnectionManager;
import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.connection.MalformedNetworkPacketException;
import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.logging.Logger;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.concurrent.PriorityBlockingQueue;

public class NetworkPacketHandler {
    private static final String TAG = "communication.NetworkPacketHandler";

    private final NetworkConnection _networkConnection;
    private final NetworkConnectionManager _networkConnectionManager;
    private final ConnectionSendQueue _sendQueue;

    public NetworkPacketHandler(NetworkConnection networkConnection, NetworkConnectionManager networkConnectionManager) {
        _networkConnection = networkConnection;
        _networkConnectionManager = networkConnectionManager;

        _sendQueue = new ConnectionSendQueue(this);
    }

    public void sendMessage(ApplicationMessage message) {
        NetworkPacket packet = new NetworkPacket(message, SequenceIDGenerator.getNextSequenceID());

        sendPacket(packet);
    }

    public void sendPacket(NetworkPacket packet) {
        _sendQueue.enqueuePacket(packet);
    }

    public Optional<String> receiveMessage() {
        try {
            while(true) {
                NetworkPacket packet = readMessage();

                if(packet.getNetworkFlags() != null) {
                    //So it should be an acknowledge, an error or the connection gets closed
                    if(packet.getNetworkFlags().isAcknowledgePresent()) {
                        NetworkPacketManager.getInstance().receivedAcknowledge(packet);

                        //TODO remove from send queue too

                    } else if(packet.getNetworkFlags().isClosePresent() && packet.getNetworkFlags().getClose()) {
                        _networkConnection.close();
                    }
                } else {
                    NetworkPacket response = NetworkPacket.createAcknowledge(packet, true);
                    this.sendPacket(response);

                    return Optional.of(packet.getApplicationMessage());
                }
            }
        } catch(IOException e) {
            _networkConnectionManager.connectionClosed(_networkConnection);

            return Optional.empty();
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

                NetworkPacket response = NetworkPacket.createAcknowledge(packet, false);

                Logger.getLogger().debug(TAG, "Checksum failed for sequence id " + packet.getSequenceID());

                this.sendPacket(response);
            } catch(MalformedNetworkPacketException e) {
                Logger.getLogger().debug(TAG, e.getMessage());
            }
        }
    }

    public void close() {
        try {
            _networkConnection.close();

            _sendQueue.closeSendQueue();
        } catch (IOException e) {
            //Dont do anything
        }
    }

    public NetworkConnection getUnderlyingConnection() {
        return _networkConnection;
    }


    class ConnectionSendQueue implements Runnable {
        private static final String TAG = "communication.ConnectionSendQueue";

        private final PriorityBlockingQueue<FIFOElement<NetworkPacket>> _sendQueue;
        private volatile boolean _isRunning = true;
        private final Thread _localThread;
        private final NetworkPacketHandler _packetHandler;

        public ConnectionSendQueue(NetworkPacketHandler packetHandler) {
            _packetHandler = packetHandler;

            _sendQueue = new PriorityBlockingQueue<>();

            _localThread = new Thread(this);
            _localThread.start();
        }

        public void closeSendQueue() {
            _isRunning = false;

            _localThread.interrupt();
        }

        public void enqueuePacket(NetworkPacket packet) {
            //TODO we need to order the messages with the same priority (FIFO)
            if(!_sendQueue.offer(new FIFOElement<>(packet))) {
                Logger.getLogger().warn(TAG, "We lost packets, why?!");
            }
        }

        @Override
        public void run() {
            while(_isRunning) {
                try {
                    NetworkPacket packet = _sendQueue.take().getElement();

                    try {
                        Logger.getLogger().trace(TAG, "Sending network packet:", packet);

                        _networkConnection.sendBytes(packet.getBytes());

                        if(packet.shouldResend()) {
                            NetworkPacketManager.getInstance().sentNetworkPacket(packet, _packetHandler);
                        }
                    } catch (IOException e) {
                        _networkConnectionManager.connectionClosed(_networkConnection);
                        if(!(e instanceof ConnectionClosedException || e instanceof ClosedChannelException)) {
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
