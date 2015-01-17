package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Response;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class RegisteredCommand extends Response {
    private boolean _registered;

    public RegisteredCommand(ClientID playerID) {
        super(playerID, CommandType.Registered);
    }

    public RegisteredCommand(RegisteredCommand registeredCommand, ClientID clientID) {
        super(clientID, registeredCommand.getCommandType());

        _registered = registeredCommand.getRegistered();
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
        messageData.put(CommandConstants.SUCCESS, _registered);

        return this.createJSONString(messageData);
    }

    @Override
    public Response createCopyWithDifferentClientID(ClientID clientID) {
        return new RegisteredCommand(this, clientID);
    }
}
