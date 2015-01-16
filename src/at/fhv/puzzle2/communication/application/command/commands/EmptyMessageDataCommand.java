package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.application.command.AbstractCommand;

import java.util.LinkedHashMap;

public abstract class EmptyMessageDataCommand extends AbstractCommand {
    protected EmptyMessageDataCommand(String clientID, String messageType) {
        super(clientID, messageType);
    }

    private EmptyMessageDataCommand(EmptyMessageDataCommand command) {
        super(command.getClientID(), command.getMessageType());
    }

    @Override
    public String toJSONString() {
        return createJSONString(new LinkedHashMap<>());
    }
}
