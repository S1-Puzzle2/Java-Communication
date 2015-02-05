package at.fhv.puzzle2.communication.application.listener;

import at.fhv.puzzle2.communication.ApplicationConnectionManager;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.connection.CommandConnection;

import java.util.Optional;

public class CommandListener implements Runnable {
    private final ApplicationConnectionManager _connectionManager;
    private final CommandConnection _connection;
    private final Thread _localThread;
    private volatile boolean _isRunning = false;

    public CommandListener(ApplicationConnectionManager connectionManager, CommandConnection connection) {
        _connectionManager = connectionManager;
        _connection = connection;
        _localThread = new Thread(this);

        _isRunning = true;
        _localThread.start();
    }

    public void close() {
        if(_localThread != null) {
            _localThread.interrupt();
        }
    }

    @Override
    public void run() {
        while(_isRunning) {
            Optional<Command> command = _connection.receiveCommand();

            if(command.isPresent()) {
                _connectionManager.commandRecieved(command.get());
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
