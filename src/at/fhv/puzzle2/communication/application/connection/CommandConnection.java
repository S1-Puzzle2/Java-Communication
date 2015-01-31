package at.fhv.puzzle2.communication.application.connection;

import at.fhv.puzzle2.communication.ApplicationConnectionManager;
import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.CommandFactory;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.UnknownCommandException;
import at.fhv.puzzle2.communication.application.command.commands.error.MalformedCommand;
import at.fhv.puzzle2.communication.application.command.commands.error.UnknownCommand;
import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.logging.Logger;

import java.util.Objects;

public class CommandConnection {
    private static final String TAG = "communication.CommandConnection";

    private final ApplicationConnectionManager _connectionManager;
    private final ApplicationConnection _applicationConnection;

    public CommandConnection(ApplicationConnectionManager connectionManager, ApplicationConnection applicationConnection) {
        _connectionManager = connectionManager;
        _applicationConnection = applicationConnection;
    }

    public void sendCommand(Command command) {
        Logger.getLogger().debug(TAG, "Sending command:", command);
        _applicationConnection.sendApplicationMessage(new ApplicationMessage(command.toJSONString()));
    }

    public Command receiveCommand() {
        while(true) {
            final ApplicationMessage receivedMessage = _applicationConnection.receiveMessage();

            if(receivedMessage == null) {
                return null;
            }


            final Command command;
            try {
                command = CommandFactory.parseCommand(receivedMessage);
                command.setConnection(this);

                Logger.getLogger().debug(TAG, "Received command:", command);

                return command;
            } catch (UnknownCommandException e) {
                Logger.getLogger().warn(TAG, e.getMessage());

                UnknownCommand unknownCommand = new UnknownCommand(e.getMessage());
                sendCommand(unknownCommand);
            } catch(MalformedCommandException e) {
                Logger.getLogger().warn(TAG, e.getMessage());

                MalformedCommand malformedCommand = new MalformedCommand(e.getMessage());
                sendCommand(malformedCommand);
            }
        }
    }

    public void close() {
        _applicationConnection.close();
    }

    public NetworkConnection getUnderlyingConnection() {
        return _applicationConnection.getUnderlyingConnection();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof CommandConnection && Objects.equals(this.getUnderlyingConnection(), ((CommandConnection)object).getUnderlyingConnection());
    }

}
