package at.fhv.puzzle2.communication.application.command.commands.unity;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.List;

public class CheckPuzzleFinishedCommand extends Command {
    private List<Integer> _partList;

    public CheckPuzzleFinishedCommand(ClientID clientID) {
        super(clientID, CommandType.CheckPuzzleFinished);
    }

    public List<Integer> getPartList() {
        return _partList;
    }

    public void setPartList(List<Integer> partList) {
        _partList = partList;
    }
}
