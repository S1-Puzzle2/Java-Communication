package at.fhv.puzzle2.communication.application.command.commands.configurator;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class SetPuzzleCommand extends Command {
    private Integer _puzzleID;

    public SetPuzzleCommand(ClientID clientID) {
        super(clientID, CommandType.SetPuzzle);
    }

    public void setPuzzleID(Integer id) {
        _puzzleID = id;
    }
}
