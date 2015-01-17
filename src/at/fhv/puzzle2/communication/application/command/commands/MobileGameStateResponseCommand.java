package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Response;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MobileGameStateResponseCommand extends Response {
    private String _teamName;
    private String _gameName;

    private List<Integer> _puzzlePartList;

    public MobileGameStateResponseCommand(ClientID clientID) {
        super(clientID, CommandType.MobileGameStateResponse);

        _puzzlePartList = new ArrayList<>();
    }

    private MobileGameStateResponseCommand(MobileGameStateResponseCommand command, ClientID clientID) {
        super(clientID, command.getCommandType());

        _teamName = command.getTeamName();
        _gameName = command.getGameName();

        _puzzlePartList = command._puzzlePartList;
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

    @Override
    public Response createCopyWithDifferentClientID(ClientID clientID) {
        return new MobileGameStateResponseCommand(this, clientID);
    }
}
