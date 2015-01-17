package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.EmptyMessageDataResponse;
import at.fhv.puzzle2.communication.application.command.Response;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class GameStartResponseCommand extends EmptyMessageDataResponse {
    protected GameStartResponseCommand(ClientID clientID) {
        super(clientID, CommandType.GameStart);
    }

    private GameStartResponseCommand(GameStartResponseCommand command, ClientID clientID) {
        super(clientID, command.getCommandType());
    }

    @Override
    public Response createCopyWithDifferentClientID(ClientID clientID) {
        return new GameStartResponseCommand(this, clientID);
    }
}
