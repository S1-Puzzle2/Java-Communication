package at.fhv.puzzle2.communication.application.command;

import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.connection.CommandConnection;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class AbstractCommand implements Command {
    private String _clientID;
    private String _messageType;
    private CommandConnection _sender;

    protected AbstractCommand(String clientID, String messageType) {
        _clientID = clientID;
        _messageType = messageType;
    }

    public CommandConnection getSender() {
        return _sender;
    }

    public void setSender(CommandConnection sender) {
        _sender = sender;
    }

    public String getClientID() {
        return _clientID;
    }

    public String getMessageType() {
        return _messageType;
    }

    protected String createJSONString(HashMap<String, Object> messageData) {
        HashMap<String, Object> message = new LinkedHashMap<>();

        message.put(CommandConstants.CLIENT_ID, _clientID);
        message.put(CommandConstants.MESSAGE_TYPE, _messageType);

        message.put(CommandConstants.MESSAGE_DATA, messageData);

        return JSONValue.toJSONString(message);
    }

    public abstract AbstractCommand createCopyWithDiffClientID(String clientID);
}
