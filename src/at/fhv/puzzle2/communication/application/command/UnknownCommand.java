package at.fhv.puzzle2.communication.application.command;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.application.connection.ApplicationConnection;
import at.fhv.puzzle2.communication.application.connection.CommandConnection;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class UnknownCommand implements Command {
    private ApplicationMessage _applicationMessage;

    public UnknownCommand( ApplicationMessage applicationMessage) {
        _applicationMessage = applicationMessage;
    }

    @Override
    public String toJSONString() {
        HashMap<String, Object> command = new LinkedHashMap<>();
        HashMap<String, Object> data = new LinkedHashMap<>();

        command.put("msgType", "UNKNOWN_COMMAND");

        data.put("message", _applicationMessage.getMessage());
        command.put("msgData", data);

        return JSONValue.toJSONString(command);
    }
}
