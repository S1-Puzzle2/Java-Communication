package at.fhv.puzzle2.communication.application.listener;

import at.fhv.puzzle2.communication.CommunicationManager;
import at.fhv.puzzle2.communication.application.connection.ApplicationConnection;
import at.fhv.puzzle2.communication.application.model.ApplicationMessage;
import at.fhv.puzzle2.communication.connection.ConnectionClosedException;

import java.io.IOException;

public class MessageListener implements Runnable {
    private CommunicationManager _communicationManager;
    private ApplicationConnection _connection;
    private Thread _localThread;
    private volatile boolean _isRunning = false;

    public MessageListener(CommunicationManager communicationManager, ApplicationConnection connection) {
        _communicationManager = communicationManager;
        _connection = connection;
        _localThread = new Thread(this);

        _isRunning = true;
        _localThread.start();
    }

    public void close() throws IOException {
        if(_localThread != null) {
            _localThread.interrupt();
        }

        _connection.close();
    }

    @Override
    public void run() {
        while(_isRunning) {
            try {
                ApplicationMessage applicationMessage = _connection.readApplicationMessage();

                _communicationManager.messageReceived(applicationMessage);
            } catch (IOException e) {
                _communicationManager.connectionClosed(_connection);
                
                //We lost the connection, so we dont have to wait for other messages
                _isRunning = false;

                //ConnectionClosedException occurs, when the other side closes the connection
                if(!(e instanceof ConnectionClosedException)) {
                    e.printStackTrace();
                }
            }
        }
    }
}
