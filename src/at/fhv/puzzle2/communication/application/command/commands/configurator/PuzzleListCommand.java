package at.fhv.puzzle2.communication.application.command.commands.configurator;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.HashMap;

public class PuzzleListCommand extends Command {
    private HashMap<Integer, String> _puzzleList;

    protected PuzzleListCommand(ClientID clientID) {
        super(clientID, CommandType.PuzzleList);
    }

    public void setPuzzleList(HashMap<Integer, String> puzzleList) {
        _puzzleList = puzzleList;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.PUZZLE_LIST, _puzzleList);

        return super.toJSONString();
    }
}
