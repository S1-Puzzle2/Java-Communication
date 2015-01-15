package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.application.command.AbstractCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;

public class GameStateResponseCommand extends AbstractCommand {
    public GameStateResponseCommand(String clientID) {
        super(clientID, CommandTypeConstants.GAME_STATE_RESPONSE_MESSAGE);
    }

    @Override
    public String toJSONString() {
        return null;
    }
}
