package at.fhv.puzzle2.communication.application.connection;

import at.fhv.puzzle2.communication.ApplicationConnectionManager;
import at.fhv.puzzle2.communication.ConnectionClosedException;
import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.CommandFactory;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.UnknownCommandException;
import at.fhv.puzzle2.communication.application.command.commands.error.MalformedCommand;
import at.fhv.puzzle2.communication.application.command.commands.error.UnknownCommand;
import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.logging.Logger;

import java.io.IOException;
import java.net.SocketException;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandConnection {
    private static final String TAG = "communication.CommandConnection";

    private final ApplicationConnectionManager _connectionManager;
    private final ApplicationConnection _applicationConnection;

    public CommandConnection(ApplicationConnectionManager connectionManager, ApplicationConnection applicationConnection) {
        _connectionManager = connectionManager;
        _applicationConnection = applicationConnection;
    }

    public void sendCommand(Command command) {
        Logger.getLogger().debug(TAG, "Sending app-message: " + command.toJSONString());
        _applicationConnection.sendApplicationMessage(new ApplicationMessage(command.toJSONString()));
    }

    public Command receiveCommand() {
        while(true) {
            final ApplicationMessage receivedMessage = _applicationConnection.receiveMessage();

            if(receivedMessage == null) {
                return null;
            }

            Logger.getLogger().debug(TAG, "Received app-message: " + receivedMessage.getMessage());

            final Command command;
            try {
                command = CommandFactory.parseCommand(receivedMessage);
                command.setConnection(this);

                return command;
            } catch (UnknownCommandException e) {
                UnknownCommand unknownCommand = new UnknownCommand(e.getMessage());
                sendCommand(unknownCommand);
            } catch(MalformedCommandException e) {
                MalformedCommand malformedCommand = new MalformedCommand(e.getMessage());
                sendCommand(malformedCommand);
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
        return object instanceof CommandConnection && Objects.equals(this.getUnderlyingConnection(), ((CommandConnection)object).getUnderlyingConnection());
    }

}
