package at.fhv.puzzle2.communication.application.command.dto;

import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class PuzzlePartDTO implements JSONAware {
    private int _id;
    private String _barCode;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getBarCode() {
        return _barCode;
    }

    public void setBarCode(String barCode) {
        this._barCode = barCode;
    }

    @Override
    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put(CommandConstants.ID, _id);
        json.put(CommandConstants.BAR_CODE, _barCode);

        return json.toJSONString();
    }
}
