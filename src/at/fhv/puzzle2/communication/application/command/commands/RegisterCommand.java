package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.application.command.AbstractCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class RegisterCommand extends AbstractCommand {
    private String _clientType;

    public RegisterCommand(String clientID) {
        super(clientID, CommandTypeConstants.REGISTER_MESSAGE);
    }

    public void setClientType(String type) {
        _clientType = type;
    }

    public String getClientType() {
        return _clientType;
    }

    @Override
    public String toJSONString() {
        HashMap<String, Object> messageData = new LinkedHashMap<>();
        messageData.put(CommandConstants.CLIENT_TYPE, _clientType);

        return this.createJSONString(messageData);
    }

    @Override
    public AbstractCommand createCopyWithDiffClientID(String clientID) {
        return null;
    }
}
