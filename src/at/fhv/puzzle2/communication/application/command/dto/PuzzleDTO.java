package at.fhv.puzzle2.communication.application.command.dto;

import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import java.util.List;

public class PuzzleDTO implements JSONAware {
    private String _name;
    private List<Integer> _partsList;

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public List<Integer> getPartsList() {
        return _partsList;
    }

    public void setPartsList(List<Integer> partsList) {
        this._partsList = partsList;
    }

    @Override
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CommandConstants.NAME, _name);
        jsonObject.put(CommandConstants.PART_LIST, _partsList);

        return jsonObject.toJSONString();
    }
}
