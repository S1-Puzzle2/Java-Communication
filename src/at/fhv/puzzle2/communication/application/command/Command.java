package at.fhv.puzzle2.communication.application.command;


import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;
import at.fhv.puzzle2.communication.application.connection.CommandConnection;
import org.json.simple.JSONAware;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class Command implements JSONAware {
    private CommandConnection _connection;

    private ClientID _clientID = null;
    private final CommandType _commandType;

    protected Command(ClientID clientID, CommandType commandType) {
        _clientID = clientID;
        _commandType = commandType;
    }

    protected Command(CommandType messageType) {
        _commandType = messageType;
    }

    public void setConnection(CommandConnection connection) {
        _connection = connection;
    }

    public CommandConnection getConnection() {
        return _connection;
    }

    public ClientID getClientID() {
        return _clientID;
    }

    public CommandType getCommandType() {
        return _commandType;
    }

    protected String createJSONString(HashMap<String, Object> messageData) {
        HashMap<String, Object> message = new LinkedHashMap<>();

        if(_clientID == null) {
            message.put(CommandConstants.CLIENT_ID, null);
        } else {
            message.put(CommandConstants.CLIENT_ID, _clientID.toString());
        }
        message.put(CommandConstants.MESSAGE_TYPE, _commandType.getTypeString());

        message.put(CommandConstants.MESSAGE_DATA, messageData);

        return JSONValue.toJSONString(message);
    }
}
