package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.application.command.AbstractCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class RegisteredCommand extends AbstractCommand {
    private boolean _registered;

    protected RegisteredCommand(String playerID) {
        super(playerID, CommandTypeConstants.REGISTERED_MESSAGE);
    }

    public void setRegistered(boolean registered) {
        _registered = registered;
    }

    public boolean getRegistered() {
        return _registered;
    }

    @Override
    public String toJSONString() {
        HashMap<String, Object> messageData = new LinkedHashMap<>();
        messageData.put(CommandConstants.REGISTERED, _registered);

        return this.createJSONString(messageData);
    }
}
