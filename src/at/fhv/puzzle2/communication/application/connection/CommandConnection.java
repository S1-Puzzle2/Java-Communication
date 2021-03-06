package at.fhv.puzzle2.communication.application.connection;

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
import java.util.Optional;

public class CommandConnection {
    private static final String TAG = "communication.CommandConnection";

    private final ApplicationConnection _applicationConnection;

    public CommandConnection(ApplicationConnection applicationConnection) {
        _applicationConnection = applicationConnection;
    }

    public void sendCommand(Command command) {
        Logger.getLogger().debug(TAG, "Sending command:", command);
        _applicationConnection.sendApplicationMessage(new ApplicationMessage(command));
    }

    public Optional<Command> receiveCommand() {
        while(true) {
            Optional<ApplicationMessage> message = _applicationConnection.receiveMessage();

            //If we received an empty Optional-ApplicatioNmessage, then there is something wrong with the socket
            if(!message.isPresent()) {
                return Optional.empty();
            }

            //If parsing the command went wrong, we just wait for another message
            Optional<Command> commandOptional = parseCommand(message.get());
            if(commandOptional.isPresent()) {
                return commandOptional;
            }
        }
    }

    private Optional<Command> parseCommand(ApplicationMessage applicationMessage) {
        final Command command;
        try {
            command = CommandFactory.parseCommand(applicationMessage);
            command.setConnection(this);

            Logger.getLogger().debug(TAG, "Received command:", command);

            return Optional.of(command);
        } catch (UnknownCommandException e) {
            Logger.getLogger().warn(TAG, e.getMessage());

            UnknownCommand unknownCommand = new UnknownCommand(e.getMessage());
            sendCommand(unknownCommand);
        } catch(MalformedCommandException e) {
            Logger.getLogger().warn(TAG, e.getMessage());

            MalformedCommand malformedCommand = new MalformedCommand(e.getMessage());
            sendCommand(malformedCommand);
        }

        return Optional.empty();
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
