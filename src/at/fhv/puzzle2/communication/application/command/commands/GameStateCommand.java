package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.LinkedList;
import java.util.List;

public class GameStateCommand extends Command {
    private String _puzzleName, _teamName;
    private List<Integer> _partsList = new LinkedList<>();

    public GameStateCommand(ClientID clientID) {
        super(clientID, CommandType.GameState);
    }

    public void setTeamName(String name) {
        _teamName = name;
    }

    public void setPuzzleName(String name) {
        _puzzleName = name;
    }

    public void setPartsList(List<Integer> partsList) {
        _partsList = partsList;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.PUZZLE_NAME, _puzzleName);
        _messageData.put(CommandConstants.TEAM_NAME, _teamName);

        _messageData.put(CommandConstants.IMAGE_LIST, _partsList);

        return super.toJSONString();
    }
}
