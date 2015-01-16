package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.application.command.AbstractCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;

public class GetGameStateCommand extends EmptyMessageDataCommand {
    public GetGameStateCommand(String clientID) {
        super(clientID, CommandTypeConstants.GET_GAME_STATE_MESSAGE);
    }

    public GetGameStateCommand(GetGameStateCommand command, String clientID) {
        super(clientID, command.getMessageType());
    }

    @Override
    public AbstractCommand createCopyWithDiffClientID(String clientID) {
        return new GetGameStateCommand(this, clientID);
    }
}
