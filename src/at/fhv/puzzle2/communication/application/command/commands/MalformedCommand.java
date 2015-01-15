package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MalformedCommand implements Command {
    private ApplicationMessage _applicationMessage;

    public MalformedCommand(ApplicationMessage applicationMessage) {
        _applicationMessage = applicationMessage;
    }
    @Override
    public String toJSONString() {
        HashMap<String, Object> command = new LinkedHashMap<>();
        HashMap<String, Object> data = new LinkedHashMap<>();

        command.put(CommandConstants.MESSAGE_TYPE, CommandTypeConstants.MALFORMED_COMMAND_MESSAGE);

        data.put(CommandConstants.APPLICATION_MESSAGE, _applicationMessage.getMessage());
        command.put(CommandConstants.MESSAGE_DATA, data);

        return JSONValue.toJSONString(command);
    }
}
