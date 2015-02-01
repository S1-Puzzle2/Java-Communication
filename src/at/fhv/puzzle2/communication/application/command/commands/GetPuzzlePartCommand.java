package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;
import at.fhv.puzzle2.communication.connection.networkPacket.NetworkPacketPriority;

public class GetPuzzlePartCommand extends Command {
    private int _puzzlePartID;

    public GetPuzzlePartCommand(ClientID clientID) {
        super(clientID, CommandType.GetPuzzlePart, NetworkPacketPriority.IMAGE_PRIORITY);
    }

    public GetPuzzlePartCommand(GetPuzzlePartCommand getPuzzlePartCommand, ClientID clientID) {
        super(clientID, getPuzzlePartCommand.getCommandType());

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
        _messageData.put(CommandConstants.PUZZLE_PART_ID, _puzzlePartID);

        return super.toJSONString();
    }
}
