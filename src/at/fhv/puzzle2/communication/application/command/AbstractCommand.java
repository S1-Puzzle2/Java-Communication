package at.fhv.puzzle2.communication.application.command;

import at.fhv.puzzle2.communication.application.connection.ApplicationConnection;
import at.fhv.puzzle2.communication.application.connection.CommandConnection;
import org.json.simple.JSONAware;

import java.util.HashMap;

public abstract class AbstractCommand implements Command {
    private String _playerID;
    private CommandConnection _sender;

    protected AbstractCommand(String playerID) {
        _playerID = playerID;
    }

    public CommandConnection getSender() {
        return _sender;
    }

    protected void setSender(CommandConnection sender) {
        _sender = sender;
    }

    public String getPlayerID() {
        return _playerID;
    }
}
