package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;

/**
 * Created by sinz on 15.01.2015.
 */
public class ReadyCommand extends EmptyMessageDataCommand {
    public ReadyCommand(String clientID) {
        super(clientID, CommandTypeConstants.READY_MESSAGE);
    }
}
