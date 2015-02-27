package at.fhv.puzzle2.communication.connection.queue;

import at.fhv.puzzle2.communication.NetworkConnectionManager;
import at.fhv.puzzle2.communication.connection.MalformedNetworkPacketException;
import at.fhv.puzzle2.communication.connection.networkPacket.NetworkPacket;
import at.fhv.puzzle2.communication.connection.networkPacket.NetworkPacketHandler;
import at.fhv.puzzle2.communication.connection.networkPacket.NetworkPacketManager;
import at.fhv.puzzle2.logging.Logger;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ConnectionReceiveQueue implements Runnable {
    private static final String TAG = "communication.ConnectionReceiveQueue";
    private final NetworkPacketHandler _packetHandler;
    private final BlockingQueue<String> _queue;
    private final NetworkConnectionManager _networkConnectionManager;

    private volatile boolean _isRunning = true;
    private Thread _localThread;

    public ConnectionReceiveQueue(NetworkPacketHandler packetHandler, BlockingQueue<String> queue, NetworkConnectionManager networkConnectionManager) {
        _packetHandler = packetHandler;
        _queue = queue;
        _networkConnectionManager = networkConnectionManager;

        _localThread = new Thread(this);
        _localThread.start();
    }

    @Override
    public void run() {
            while(_isRunning) {
                try {
                    NetworkPacket packet = readMessage();

                    if(packet.getNetworkFlags() != null) {
                        System.out.println("A flags-packet");
                        //So it should be an acknowledge, an error or the connection gets closed
                        if(packet.getNetworkFlags().isAcknowledgePresent()) {
                            NetworkPacketManager.getInstance().receivedAcknowledge(packet);

                            //TODO remove from send queue too

                        } else if(packet.getNetworkFlags().isClosePresent() && packet.getNetworkFlags().getClose()) {
                            _networkConnectionManager.connectionClosed(_packetHandler.getUnderlyingConnection());
                        }
                    } else {
                        NetworkPacket response = NetworkPacket.createAcknowledge(packet, true);
                        _packetHandler.sendPacket(response);

                        synchronized (_queue) {
                            _queue.put(packet.getApplicationMessage());
                        }
                    }
                } catch(IOException e) {
                    _networkConnectionManager.connectionClosed(_packetHandler.getUnderlyingConnection());
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //Dont do anything here
                }
            }
    }

    private NetworkPacket readMessage() throws IOException {
        NetworkPacket packet;

        while(true) {
            try {
                packet = NetworkPacket.parse(_packetHandler.getUnderlyingConnection().readBytes());

                Logger.getLogger().trace(TAG, "Received network packet:", packet);

                if(packet.checkSumCorrect()) {
                    return packet;
                }

                if(packet.getNetworkFlags() != null) {
                    continue;
                }

                NetworkPacket response = NetworkPacket.createAcknowledge(packet, false);

                Logger.getLogger().warn(TAG, "Checksum failed for sequence id " + packet.getSequenceID());

                _packetHandler.sendPacket(response);
            } catch(MalformedNetworkPacketException e) {
                Logger.getLogger().error(TAG, e.getMessage());
            }
        }
    }

    public void closeReceiveQueue() {
        _isRunning = false;

        _localThread.interrupt();
    }
}
