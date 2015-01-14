package at.fhv.puzzle2.communication.application.connection;

import at.fhv.puzzle2.communication.ApplicationConnectionManager;
import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.application.command.*;
import at.fhv.puzzle2.communication.ConnectionClosedException;
import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.io.IOException;

public class CommandConnection {
    private ApplicationConnectionManager _connectionManager;
    private ApplicationConnection _applicationConnection;

    public CommandConnection(ApplicationConnectionManager connectionManager, ApplicationConnection applicationConnection) {
        _connectionManager = connectionManager;
        _applicationConnection = applicationConnection;
    }

    public void sendCommand(Command command) {
        try {
            _applicationConnection.sendApplicationMessage(new ApplicationMessage(command.toJSONString()));
        } catch (IOException e) {
            if(e instanceof ConnectionClosedException) {
                _connectionManager.connectionClosed(this);
            } else {
                e.printStackTrace();
            }
        }
    }

    public AbstractCommand receiveCommand() {
        while(true) {
            try {
                final ApplicationMessage recievedMessage = _applicationConnection.receiveMessage();
                if(recievedMessage == null) {
                    return null;
                }

                final Command command = CommandFactory.parseCommand(recievedMessage);

                if(command instanceof AbstractCommand) {
                    return (AbstractCommand)command;
                }

                new Thread(() -> this.sendCommand(command)).start();

            } catch (IOException e) {
                if(e instanceof ConnectionClosedException) {
                    _connectionManager.connectionClosed(this);
                } else {
                    e.printStackTrace();
                }

                return null;
            }
        }
    }

    public void close() {
        try {
            _applicationConnection.close();
        } catch (IOException e) {
            //We dont really care about exceptions while closing the connection
            //e.printStackTrace();
        }
    }

    public NetworkConnection getUnderlyingConnection() {
        return _applicationConnection.getUnderlyingConnection();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof CommandConnection && this.getUnderlyingConnection().equals(((CommandConnection)object).getUnderlyingConnection());
    }
}
