package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.application.command.AbstractCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;

import java.util.LinkedHashMap;

public class PauseCommand extends AbstractCommand {
    protected PauseCommand(String clientID) {
        super(clientID, CommandTypeConstants.PAUSE_MESSAGE);
    }

    @Override
    public String toJSONString() {
        return createJSONString(new LinkedHashMap<>());
    }
}
