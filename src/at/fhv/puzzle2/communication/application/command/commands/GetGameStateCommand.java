package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;

public class GetGameStateCommand extends EmptyMessageDataCommand {
    public GetGameStateCommand(String clientID) {
        super(clientID, CommandTypeConstants.GET_GAME_STATE_MESSAGE);
    }
}
