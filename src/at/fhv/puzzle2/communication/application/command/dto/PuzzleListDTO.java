package at.fhv.puzzle2.communication.application.command.dto;

import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class PuzzleListDTO implements JSONAware {
    private String _puzzleName;
    private int _puzzleID;

    public String getPuzzleName() {
        return _puzzleName;
    }

    public void setPuzzleName(String puzzleName) {
        this._puzzleName = puzzleName;
    }

    public int getPuzzleID() {
        return _puzzleID;
    }

    public void setPuzzleID(int puzzleID) {
        this._puzzleID = puzzleID;
    }

    @Override
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(CommandConstants.ID, _puzzleID);
        jsonObject.put(CommandConstants.NAME, _puzzleName);

        return jsonObject.toJSONString();
    }
}
