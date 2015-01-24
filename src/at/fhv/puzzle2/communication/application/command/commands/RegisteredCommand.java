package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MultipleReceiversCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class RegisteredCommand extends Command implements MultipleReceiversCommand {
    private boolean _registered;

    public RegisteredCommand(ClientID playerID) {
        super(playerID, CommandType.Registered);
    }

    private RegisteredCommand(RegisteredCommand registeredCommand, ClientID clientID) {
        super(clientID, registeredCommand.getCommandType());

        _registered = registeredCommand.getRegistered();
    }

    public void setRegistered(boolean registered) {
        _registered = registered;
    }

    boolean getRegistered() {
        return _registered;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.SUCCESS, _registered);

        return super.toJSONString();
    }

    @Override
    public Command createCopyWithDifferentClientID(ClientID clientID) {
        return new RegisteredCommand(this, clientID);
    }
}
