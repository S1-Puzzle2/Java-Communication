package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class RegisterCommand extends Command {
    private String _clientType;

    public RegisterCommand(ClientID clientID) {
        super(clientID, CommandType.Register);
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
}
