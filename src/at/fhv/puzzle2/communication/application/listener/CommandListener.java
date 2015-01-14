package at.fhv.puzzle2.communication.application.listener;

import at.fhv.puzzle2.communication.ApplicationConnectionManager;
import at.fhv.puzzle2.communication.application.command.AbstractCommand;
import at.fhv.puzzle2.communication.application.connection.CommandConnection;

import java.io.IOException;

public class CommandListener implements Runnable {
    private ApplicationConnectionManager _connectionManager;
    private CommandConnection _connection;
    private Thread _localThread;
    private volatile boolean _isRunning = false;

    public CommandListener(ApplicationConnectionManager connectionManager, CommandConnection connection) {
        _connectionManager = connectionManager;
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
            AbstractCommand command = _connection.receiveCommand();

            if(command != null) {
                _connectionManager.commandRecieved(command);
            } else {
                //We received a null message, so the connection is closed and this listener should stop
                _isRunning = false;
            }
        }
    }

    public CommandConnection getConnection() {
        return _connection;
    }
}
