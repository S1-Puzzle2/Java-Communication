package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class UnknownCommand extends Command {
    private ApplicationMessage _applicationMessage;

    public UnknownCommand( ApplicationMessage applicationMessage) {
        super(CommandType.Unknown);

        _applicationMessage = applicationMessage;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.APPLICATION_MESSAGE, _applicationMessage.getMessage());

        return super.toJSONString();
    }
}
