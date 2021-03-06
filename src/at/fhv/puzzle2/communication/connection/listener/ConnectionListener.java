package at.fhv.puzzle2.communication.connection.listener;

import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.communication.connection.endpoint.ListenableEndPoint;

import java.io.IOException;
import java.nio.channels.ClosedByInterruptException;
import java.util.concurrent.BlockingQueue;

public class ConnectionListener implements Listener, Runnable {
    private final ListenableEndPoint _endPoint;
    private volatile boolean _isRunning = false;
    private final BlockingQueue<NetworkConnection> _queue;
    private final Thread _localThread;

    public ConnectionListener(ListenableEndPoint endPoint, BlockingQueue<NetworkConnection> newConnectionQueue) {
        _endPoint = endPoint;
        _queue = newConnectionQueue;

        _localThread = new Thread(this);
    }

    /**
     * Starts listening for new connections
     */
    public void startListening() throws IOException {
        if (!_isRunning) {
            _isRunning = true;

            _endPoint.reserveListener();
            _localThread.start();
        }
    }

    public void run() {
        try {
            while (_isRunning) {
                NetworkConnection connection = _endPoint.acceptNetworkConnection();
                if (connection != null) {
                    synchronized (_queue) {
                        _queue.add(connection);
                    }
                } else {
                    //We got null, so we wont get a connection anymore
                    _isRunning = false;
                }
            }
        } catch(ClosedByInterruptException e) {
            //Dont do anything here, happens when we interrupt the ServerSocketChannel
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tells the listener to stop listening for connections and interrupt the listener,
     * if its waiting for a new connection
     *
     * @throws IOException
     */
    public void stopListening() throws IOException {
        if (_isRunning) {
            _isRunning = false;

            this._localThread.interrupt();

            _endPoint.freeListener();

        }
    }

    public boolean isRunning() {
        return _isRunning;
    }
}