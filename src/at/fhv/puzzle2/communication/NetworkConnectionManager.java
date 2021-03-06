package at.fhv.puzzle2.communication;

import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.util.concurrent.BlockingQueue;

public class NetworkConnectionManager implements Runnable {
    private final CommunicationManager _communicationManager;

    private final BlockingQueue<NetworkConnection> _newConnectionQueue;

    private final Thread _localThread;
    private volatile boolean _isRunning;

    public NetworkConnectionManager(CommunicationManager communicationManager, BlockingQueue<NetworkConnection> newConnectionQueue) {
        _communicationManager = communicationManager;

        _newConnectionQueue = newConnectionQueue;

        _isRunning = true;

        _localThread = new Thread(this);
        _localThread.start();
    }

    @Override
    public void run() {
        while(_isRunning) {
            try {
                _communicationManager.newConnectionEstablished(_newConnectionQueue.take());
            } catch (InterruptedException e) {
                //This happens, when we interrupt the thread
            }
        }
    }

    public void close() {
        _isRunning = false;

        _localThread.interrupt();
    }

    public void connectionClosed(NetworkConnection connection) {
        _communicationManager.connectionClosed(connection);
    }
}
