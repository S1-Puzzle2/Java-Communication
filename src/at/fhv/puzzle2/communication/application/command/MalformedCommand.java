package at.fhv.puzzle2.communication.application.command;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
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

        command.put("msgType", "MALFORMED_COMMAND");

        data.put("message", _applicationMessage.getMessage());
        command.put("msgData", data);

        return JSONValue.toJSONString(command);
    }
}
