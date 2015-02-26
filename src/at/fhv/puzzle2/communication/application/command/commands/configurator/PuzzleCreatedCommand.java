package at.fhv.puzzle2.communication.application.command.commands.configurator;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class PuzzleCreatedCommand extends Command {
    private boolean _success;
    private Integer _puzzleID = null;

    public PuzzleCreatedCommand(ClientID clientID) {
        super(clientID, CommandType.PuzzleCreated);
    }

    public void setSuccess(boolean success) {
        _success = success;
    }

    public void setPuzzleID(int id) {
        _puzzleID = id;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.SUCCESS, _success);
        _messageData.put(CommandConstants.ID, _puzzleID);

        return super.toJSONString();
    }
}
