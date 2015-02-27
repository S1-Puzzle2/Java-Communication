package at.fhv.puzzle2.communication.connection.networkPacket;

import at.fhv.puzzle2.communication.NetworkConnectionManager;
import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.communication.connection.queue.ConnectionReceiveQueue;
import at.fhv.puzzle2.communication.connection.queue.ConnectionSendQueue;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkPacketHandler {
    private static final String TAG = "communication.NetworkPacketHandler";

    private final NetworkConnection _networkConnection;
    private final ConnectionSendQueue _sendQueue;
    private final ConnectionReceiveQueue _receiveQueue;

    private final BlockingQueue<String> _blockingQueue = new LinkedBlockingQueue<>();

    public NetworkPacketHandler(NetworkConnection networkConnection, NetworkConnectionManager networkConnectionManager) {
        _networkConnection = networkConnection;

        _sendQueue = new ConnectionSendQueue(this, networkConnectionManager);
        _receiveQueue = new ConnectionReceiveQueue(this, _blockingQueue, networkConnectionManager);
    }

    public void sendMessage(ApplicationMessage message) {
        NetworkPacket packet = new NetworkPacket(message, SequenceIDGenerator.getNextSequenceID());

        sendPacket(packet);
    }

    public Optional<String> receiveMessage() {
        try {
            return Optional.of(_blockingQueue.take());
        } catch (InterruptedException e) {
            return Optional.empty();
        }
    }

    public void sendPacket(NetworkPacket packet) {
        _sendQueue.enqueuePacket(packet);
    }



    public void close() {
        try {
            _sendQueue.closeSendQueue();
            _receiveQueue.closeReceiveQueue();

            _networkConnection.close();

            _sendQueue.closeSendQueue();
        } catch (IOException e) {
            //Dont do anything
        }
    }

    public NetworkConnection getUnderlyingConnection() {
        return _networkConnection;
    }
}
