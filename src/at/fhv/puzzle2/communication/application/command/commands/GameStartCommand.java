package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;

public class GameStartCommand extends EmptyMessageDataCommand {
    protected GameStartCommand(String clientID) {
        super(clientID, CommandTypeConstants.GAME_START_MESSAGE);
    }
}
