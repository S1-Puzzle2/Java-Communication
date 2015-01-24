package at.fhv.puzzle2.communication.application.command.commands.error;

import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class MalformedCommand extends Command {
    private final String _applicationMessage;

    public MalformedCommand(String applicationMessage) {
        super(CommandType.Malformed);
        _applicationMessage = applicationMessage;
    }
    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.APPLICATION_MESSAGE, _applicationMessage);

        return super.toJSONString();
    }
}
