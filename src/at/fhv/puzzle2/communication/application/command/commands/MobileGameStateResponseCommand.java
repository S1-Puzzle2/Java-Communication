package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MultipleReceiversCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.ArrayList;
import java.util.List;

public class MobileGameStateResponseCommand extends Command implements MultipleReceiversCommand {
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

    String getTeamName() {
        return _teamName;
    }

    public void setTeamName(String teamName) {
        this._teamName = teamName;
    }

    String getGameName() {
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
        _messageData.put("teamName", _teamName);
        _messageData.put("gameName", _gameName);
        _messageData.put("puzzleParts", _puzzlePartList);

        return super.toJSONString();
    }

    @Override
    public Command createCopyWithDifferentClientID(ClientID clientID) {
        return new MobileGameStateResponseCommand(this, clientID);
    }
}
