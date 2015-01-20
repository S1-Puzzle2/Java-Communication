package at.fhv.puzzle2.communication;

import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.util.concurrent.BlockingQueue;

public class NetworkConnectionManager implements Runnable {
    private CommunicationManager _communicationManager;

    private final BlockingQueue<NetworkConnection> _newConnectionQueue;

    private Thread _localThread;
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
                synchronized (_newConnectionQueue) {
                    while(_newConnectionQueue.isEmpty()) {
                        try {
                            _newConnectionQueue.wait();
                        } catch (InterruptedException e) {
                            //This happens, when we interrupt the thread and all we need to do now is break out of the wait
                            break;
                        }
                    }

                    while(!_newConnectionQueue.isEmpty()) {
                        _communicationManager.newConnectionEstablished(_newConnectionQueue.take());
                    }
                }
            } catch (InterruptedException e) {
                    e.printStackTrace();
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
