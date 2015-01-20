package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.EmptyMessageDataResponse;
import at.fhv.puzzle2.communication.application.command.Response;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.LinkedHashMap;

public class PauseCommand extends EmptyMessageDataResponse {
    protected PauseCommand(ClientID clientID) {
        super(clientID, CommandType.Pause);
    }

    public PauseCommand(PauseCommand pauseCommand, ClientID clientID) {
        super(clientID, pauseCommand.getCommandType());
    }

    @Override
    public Response createCopyWithDifferentClientID(ClientID clientID) {
        return new PauseCommand(this, clientID);
    }
}