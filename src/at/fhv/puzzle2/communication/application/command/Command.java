package at.fhv.puzzle2.communication.application.command;


import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;
import at.fhv.puzzle2.communication.application.connection.CommandConnection;
import at.fhv.puzzle2.communication.connection.networkPacket.NetworkPacketPriority;
import at.fhv.puzzle2.logging.LoggedObject;
import org.json.simple.JSONAware;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class Command implements JSONAware, LoggedObject {
    private CommandConnection _connection;

    private ClientID _clientID;
    private final CommandType _commandType;

    protected final HashMap<String, Object> _messageData;
    private int _priority;

    protected Command(ClientID clientID, CommandType commandType) {
        this(clientID, commandType, NetworkPacketPriority.COMMAND_PRIORITY);
    }

    protected Command(ClientID clientID, CommandType commandType, int priority) {
        _messageData = new LinkedHashMap<>();

        _clientID = clientID;
        _commandType = commandType;

        _priority = priority;
    }

    protected Command(CommandType commandType) {
        this(null, commandType);
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

    public void setClientID(ClientID clientID) {
        _clientID = clientID;
    }

    public CommandType getCommandType() {
        return _commandType;
    }

    @Override
    public String toJSONString() {
        HashMap<String, Object> message = new LinkedHashMap<>();

        if (_clientID == null) {
            message.put(CommandConstants.CLIENT_ID, null);
        } else {
            message.put(CommandConstants.CLIENT_ID, _clientID.toString());
        }
        message.put(CommandConstants.MESSAGE_TYPE, _commandType.toString());

        message.put(CommandConstants.MESSAGE_DATA, _messageData);

        return JSONValue.toJSONString(message);
    }

    @Override
    public String getLogString() {
        return toJSONString();
    }

    public int getPriority() {
        return _priority;
    }
}
