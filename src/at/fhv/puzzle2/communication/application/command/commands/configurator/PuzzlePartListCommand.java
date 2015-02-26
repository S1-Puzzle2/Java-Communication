package at.fhv.puzzle2.communication.application.command.commands.configurator;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;
import at.fhv.puzzle2.communication.application.command.dto.PuzzlePartDTO;

import java.util.LinkedList;
import java.util.List;

public class PuzzlePartListCommand extends Command {
    private int _id;
    private List<PuzzlePartDTO> _partList = new LinkedList<>();

    public PuzzlePartListCommand(ClientID clientID) {
        super(clientID, CommandType.PuzzlePartList);
    }

    public void setPartList(List<PuzzlePartDTO> partList) {
        _partList = partList;
    }

    public void setPuzzleID(int id) {
        _id = id;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.ID, _id);
        _messageData.put(CommandConstants.PART_LIST, _partList);

        return super.toJSONString();
    }
}
