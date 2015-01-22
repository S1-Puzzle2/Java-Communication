package at.fhv.puzzle2.communication.application.command.commands.mobile;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class SearchPuzzlePartCommand extends Command {
    private int _id;

    public SearchPuzzlePartCommand(ClientID clientID) {
        super(clientID, CommandType.SearchPuzzlePart);
    }

    public void setPartID(int id) {
        _id = id;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.PUZZLE_PART_ID, _id);

        return super.toJSONString();
    }
}
