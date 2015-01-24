package at.fhv.puzzle2.communication.application.command.commands.error;

import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class UnknownCommand extends Command {
    private final String _applicationMessage;

    public UnknownCommand(String applicationMessage) {
        super(CommandType.Unknown);

        _applicationMessage = applicationMessage;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.APPLICATION_MESSAGE, _applicationMessage);

        return super.toJSONString();
    }
}
