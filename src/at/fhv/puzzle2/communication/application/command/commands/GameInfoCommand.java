package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;
import at.fhv.puzzle2.communication.application.command.dto.PuzzleDTO;

public class GameInfoCommand extends Command {
    private PuzzleDTO _puzzle = null;
    private String _firstTeamName;
    private String _secondTeamName;

    public GameInfoCommand(ClientID clientID) {
        super(clientID, CommandType.GameInfo);
    }

    public void setPuzzle(PuzzleDTO puzzle) {
        _puzzle = puzzle;
    }

    public void setFirstTeamName(String name) {
        _firstTeamName = name;
    }

    public void setSecondTeamName(String name) {
        _secondTeamName = name;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.PUZZLE, _puzzle);
        _messageData.put(CommandConstants.FIRST_TEAM_NAME, _firstTeamName);
        _messageData.put(CommandConstants.SECOND_TEAM_NAME, _secondTeamName);

        return super.toJSONString();
    }
}
