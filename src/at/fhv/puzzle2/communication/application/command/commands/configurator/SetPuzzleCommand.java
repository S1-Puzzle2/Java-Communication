package at.fhv.puzzle2.communication.application.command.commands.configurator;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class SetPuzzleCommand extends Command {
    private int _puzzleID;

    public SetPuzzleCommand(ClientID clientID) {
        super(clientID, CommandType.SetPuzzle);
    }

    public void setPuzzleID(int id) {
        _puzzleID = id;
    }

    public int getPuzzleID() {
        return _puzzleID;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.ID, _puzzleID);

        return super.toJSONString();
    }
}
