package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.application.command.AbstractCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class GameStateResponseCommand extends AbstractCommand {
    private String _teamName;
    private String _gameName;

    private List<Integer> _puzzlePartList;

    public GameStateResponseCommand(String clientID) {
        super(clientID, CommandTypeConstants.GAME_STATE_RESPONSE_MESSAGE);

        _puzzlePartList = new ArrayList<>();
    }

    public String getTeamName() {
        return _teamName;
    }

    public void setTeamName(String teamName) {
        this._teamName = teamName;
    }

    public String getGameName() {
        return _gameName;
    }

    public void setGameName(String gameName) {
        this._gameName = gameName;
    }

    public void setPuzzlePartList(List<Integer> puzzlePartList) {
        this._puzzlePartList = puzzlePartList;
    }

    @Override
    public String toJSONString() {
        HashMap<String, Object> messageData = new LinkedHashMap<>();
        messageData.put("teamName", _teamName);
        messageData.put("gameName", _gameName);
        messageData.put("puzzleParts", _puzzlePartList);

        return this.createJSONString(messageData);
    }
}
