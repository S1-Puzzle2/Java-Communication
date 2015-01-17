package at.fhv.puzzle2.communication.application.command;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.LinkedHashMap;

public abstract class EmptyMessageDataResponse extends Response {
    protected EmptyMessageDataResponse(ClientID clientID, CommandType commandType) {
        super(clientID, commandType);
    }

    private EmptyMessageDataResponse(EmptyMessageDataResponse command) {
        super(command.getClientID(), command.getCommandType());
    }

    @Override
    public String toJSONString() {
        return createJSONString(new LinkedHashMap<>());
    }
}
