package at.fhv.puzzle2.communication.application.command;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.LinkedHashMap;

public abstract class EmptyMessageDataRequest extends Command {
    protected EmptyMessageDataRequest(ClientID clientID, CommandType commandType) {
        super(clientID, commandType);
    }

    protected EmptyMessageDataRequest(CommandType messageType) {
        super(messageType);
    }

    @Override
    public String toJSONString() {
        return this.createJSONString(new LinkedHashMap<>());
    }
}
