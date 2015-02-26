package at.fhv.puzzle2.communication.application.command.commands.configurator;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class GetPuzzlePartListCommand extends Command {
    private int _puzzleID;

    public GetPuzzlePartListCommand(ClientID clientID) {
        super(clientID, CommandType.GetPuzzlePartList);
    }

    public int getPuzzleID() {
        return _puzzleID;
    }

    public void setPuzzleID(int puzzleID) {
        this._puzzleID = puzzleID;
    }
}
