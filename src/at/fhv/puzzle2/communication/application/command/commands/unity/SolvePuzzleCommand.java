package at.fhv.puzzle2.communication.application.command.commands.unity;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.LinkedList;
import java.util.List;

public class SolvePuzzleCommand extends Command {
    private List<Integer> _unlockedParts = new LinkedList<>();

    protected SolvePuzzleCommand(ClientID clientID) {
        super(clientID, CommandType.SolvePuzzle);
    }

    public void setUnlockedPartsList(List<Integer> unlockedPartsList) {
        _unlockedParts = unlockedPartsList;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.UNLOCKED_PARTS_LIST, _unlockedParts);

        return super.toJSONString();
    }
}
