package at.fhv.puzzle2.communication.application.command.commands;


import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.HashMap;

public class PuzzleListCommand extends Command {
    private HashMap<Integer, String> _puzzleMap;

    protected PuzzleListCommand(ClientID clientID) {
        super(clientID, CommandType.PuzzleList);
    }

    public void setPuzzleMap(HashMap<Integer, String> puzzleMap) {
        _puzzleMap = puzzleMap;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.PUZZLE_LIST, _puzzleMap);

        return super.toJSONString();
    }
}
