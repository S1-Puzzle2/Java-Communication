package at.fhv.puzzle2.communication.application.command;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public abstract class Response extends Command {
    protected Response(ClientID clientID, CommandType commandType) {
        super(clientID, commandType);
    }

    protected Response(CommandType messageType) {
        super(messageType);
    }

    public abstract Response createCopyWithDifferentClientID(ClientID clientID);
}
