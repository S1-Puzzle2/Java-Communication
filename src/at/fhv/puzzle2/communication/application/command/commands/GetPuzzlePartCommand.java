package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.application.command.AbstractCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;

public class GetPuzzlePartCommand extends AbstractCommand {
    private int _puzzlePartID;

    public GetPuzzlePartCommand(String clientID) {
        super(clientID, CommandTypeConstants.GET_PUZZLE_PART_MESSAGE);
    }

    public GetPuzzlePartCommand(GetPuzzlePartCommand getPuzzlePartCommand, String clientID) {
        super(clientID, getPuzzlePartCommand.getMessageType());

        _puzzlePartID = getPuzzlePartCommand.getPuzzlePartID();
    }

    public void setPuzzlePartID(int id) {
        _puzzlePartID = id;
    }

    public int getPuzzlePartID() {
        return _puzzlePartID;
    }

    @Override
    public String toJSONString() {
        return null;
    }

    @Override
    public AbstractCommand createCopyWithDiffClientID(String clientID) {
        return new GetPuzzlePartCommand(this, clientID);
    }
}
