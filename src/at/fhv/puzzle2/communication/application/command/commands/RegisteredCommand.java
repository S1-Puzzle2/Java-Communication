package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MultipleReceiversCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class RegisteredCommand extends Command implements MultipleReceiversCommand {
    private boolean _registered;
    private String _teamName = "";

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

    public void setTeamName(String teamName) {
        _teamName = teamName;
    }

    boolean getRegistered() {
        return _registered;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.SUCCESS, _registered);
        _messageData.put(CommandConstants.TEAM_NAME, _teamName);

        return super.toJSONString();
    }

    @Override
    public Command createCopyWithDifferentClientID(ClientID clientID) {
        return new RegisteredCommand(this, clientID);
    }
}
