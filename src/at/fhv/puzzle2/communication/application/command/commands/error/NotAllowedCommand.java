package at.fhv.puzzle2.communication.application.command.commands.error;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class NotAllowedCommand extends Command {
    private CommandType _commandType;

    public NotAllowedCommand(ClientID clientID) {
        super(clientID, CommandType.NotAllowed);
    }

    public void setCommandType(CommandType type) {
        _commandType = type;
    }

    @Override
    public String toJSONString() {
        //_messageData.put(CommandConstants.NOT_ALLOWED_COMMAND_TYPE, _commandType.toString());

        return  super.toJSONString();
    }
}
