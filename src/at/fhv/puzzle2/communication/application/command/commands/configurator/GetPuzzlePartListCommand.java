package at.fhv.puzzle2.communication.application.command.commands.configurator;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class GetPuzzlePartListCommand extends Command {
    private String _puzzleName;

    public GetPuzzlePartListCommand(ClientID clientID) {
        super(clientID, CommandType.GetPuzzlePartList);
    }

    public void setPuzzleName(String puzzleName) {
        _puzzleName = puzzleName;
    }

    public String getPuzzleName() {
        return _puzzleName;
    }
}
