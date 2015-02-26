package at.fhv.puzzle2.communication.application.command.commands.configurator;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;
import at.fhv.puzzle2.communication.application.command.dto.PuzzleListDTO;

import java.util.List;

public class PuzzleListCommand extends Command {
    private List<PuzzleListDTO> _puzzleList;

    public PuzzleListCommand(ClientID clientID) {
        super(clientID, CommandType.PuzzleList);
    }

    public void setPuzzleList(List<PuzzleListDTO> puzzleList) {
        _puzzleList = puzzleList;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.PUZZLE_LIST, _puzzleList);

        return super.toJSONString();
    }
}
