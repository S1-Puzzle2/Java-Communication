package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.application.command.AbstractCommand;

import java.util.LinkedHashMap;

/**
 * Created by sinz on 15.01.2015.
 */
public class EmptyMessageDataCommand extends AbstractCommand {
    protected EmptyMessageDataCommand(String clientID, String messageType) {
        super(clientID, messageType);
    }

    @Override
    public String toJSONString() {
        return createJSONString(new LinkedHashMap<>());
    }
}
