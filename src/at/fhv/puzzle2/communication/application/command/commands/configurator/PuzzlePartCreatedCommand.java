package at.fhv.puzzle2.communication.application.command.commands.configurator;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class PuzzlePartCreatedCommand extends Command {
    private boolean _success;
    private Integer _puzzlePartID = null;

    public PuzzlePartCreatedCommand(ClientID clientID) {
        super(clientID, CommandType.PuzzlePartCreated);
    }

    public void setSuccess(boolean success) {
        _success = success;
    }

    public void setPuzzlePartID(int id) {
        _puzzlePartID = id;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.SUCCESS, _success);
        _messageData.put(CommandConstants.ID, _puzzlePartID);

        return super.toJSONString();
    }
}
