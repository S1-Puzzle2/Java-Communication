package at.fhv.puzzle2.communication.connection.queue;

import at.fhv.puzzle2.communication.ConnectionClosedException;
import at.fhv.puzzle2.communication.NetworkConnectionManager;
import at.fhv.puzzle2.communication.connection.networkPacket.FIFOElement;
import at.fhv.puzzle2.communication.connection.networkPacket.NetworkPacket;
import at.fhv.puzzle2.communication.connection.networkPacket.NetworkPacketHandler;
import at.fhv.puzzle2.communication.connection.networkPacket.NetworkPacketManager;
import at.fhv.puzzle2.logging.Logger;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.PriorityBlockingQueue;

public class ConnectionSendQueue implements Runnable {
    private static final String TAG = "communication.ConnectionSendQueue";

    private final PriorityBlockingQueue<FIFOElement<NetworkPacket>> _sendQueue;
    private final NetworkConnectionManager _networkConnectionManager;
    private volatile boolean _isRunning = true;
    private final Thread _localThread;
    private final NetworkPacketHandler _packetHandler;

    public ConnectionSendQueue(NetworkPacketHandler packetHandler, NetworkConnectionManager networkConnectionManager) {
        _packetHandler = packetHandler;

        _networkConnectionManager = networkConnectionManager;

        _sendQueue = new PriorityBlockingQueue<>();

        _localThread = new Thread(this);
        _localThread.setName("ConnectionSendQueue");
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

                    if(packet.shouldResend()) {
                        NetworkPacketManager.getInstance().sentNetworkPacket(packet, _packetHandler);
                    }

                    _packetHandler.getUnderlyingConnection().sendBytes(packet.getBytes());

                } catch (IOException e) {
                    _networkConnectionManager.connectionClosed(_packetHandler.getUnderlyingConnection());

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
