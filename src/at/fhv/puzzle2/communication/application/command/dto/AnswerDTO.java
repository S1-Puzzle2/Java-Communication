package at.fhv.puzzle2.communication.application.command.dto;

import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class AnswerDTO implements JSONAware {
    private String _text;
    private int _id;

    public String getText() {
        return _text;
    }

    public void setText(String text) {
        this._text = text;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    @Override
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CommandConstants.ID, _id);
        jsonObject.put(CommandConstants.TEXT, _text);

        return jsonObject.toJSONString();
    }
}
