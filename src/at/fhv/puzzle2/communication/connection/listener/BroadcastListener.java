package at.fhv.puzzle2.communication.connection.listener;

import at.fhv.puzzle2.communication.connection.Broadcast;
import at.fhv.puzzle2.communication.connection.endpoint.DiscoverableEndPoint;

import java.io.IOException;
import java.net.SocketException;
import java.nio.charset.Charset;

public class BroadcastListener implements Listener, Runnable {
    private final DiscoverableEndPoint _discoverableEndPoint;

    private final Thread _localThread;
    private volatile boolean _isRunning = false;

    private final String _broadcastResponse;

    public BroadcastListener(DiscoverableEndPoint discoverableEndPoint, String broadcastResponse) {
        _discoverableEndPoint = discoverableEndPoint;

        _broadcastResponse = broadcastResponse;

        _localThread = new Thread(this);
    }

    public void startListening() throws IOException {
        if(!_isRunning) {
            _isRunning = true;

            _discoverableEndPoint.reserveBroadcastListener();

            _localThread.start();
        }
    }

    public void stopListening() throws IOException {
        if(_isRunning) {
            _isRunning = false;

            _discoverableEndPoint.freeBroadcastListener();

            _localThread.interrupt();
        }
    }


    @Override
    public void run() {
        while(_isRunning) {
            try {
                Broadcast broadcast = _discoverableEndPoint.receiveBroadcast();

                broadcast.getSender().sendBytes(_broadcastResponse.getBytes(Charset.forName("UTF-8")));
            } catch(SocketException e) {
                //This occurs, when we interrupt the socket, so dont do anything here
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
