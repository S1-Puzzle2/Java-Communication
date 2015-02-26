package at.fhv.puzzle2.communication.application.command.commands.configurator;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class CreatePuzzleCommand extends Command {
    private String _puzzleName;

    public CreatePuzzleCommand(ClientID clientID) {
        super(clientID, CommandType.CreatePuzzle);
    }

    public void setPuzzleName(String name) {
        _puzzleName = name;
    }

    public String getPuzzleName() {
        return _puzzleName;
    }
}
