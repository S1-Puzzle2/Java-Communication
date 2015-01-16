package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.application.command.AbstractCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;

public class ReadyCommand extends EmptyMessageDataCommand {
    public ReadyCommand(String clientID) {
        super(clientID, CommandTypeConstants.READY_MESSAGE);
    }

    @Override
    public AbstractCommand createCopyWithDiffClientID(String clientID) {
        return null;
    }
}
