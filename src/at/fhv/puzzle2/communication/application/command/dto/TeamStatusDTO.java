package at.fhv.puzzle2.communication.application.command.dto;

import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class TeamStatusDTO implements JSONAware {
    private String _mobileState;
    private String _unityState;

    private int _countRemainingParts = 0;
    private int _countRemainingQuestions = 0;

    public String getMobileState() {
        return _mobileState;
    }

    public void setMobileState(String mobileState) {
        this._mobileState = mobileState;
    }

    public String getUnityState() {
        return _unityState;
    }

    public void setUnityState(String unityState) {
        this._unityState = unityState;
    }

    public int getCountRemainingParts() {
        return _countRemainingParts;
    }

    public void setCountRemainingParts(int countRemainingParts) {
        this._countRemainingParts = countRemainingParts;
    }

    public int getCountRemainingQuestions() {
        return _countRemainingQuestions;
    }

    public void setCountRemainingQuestions(int countRemainingQuestions) {
        this._countRemainingQuestions = countRemainingQuestions;
    }

    @Override
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();

        JSONObject clientsObject = new JSONObject();
        clientsObject.put(CommandConstants.UNITY_CIENT, _unityState);
        clientsObject.put(CommandConstants.MOBILE_CLIENT, _mobileState);
        jsonObject.put(CommandConstants.CLIENTS,clientsObject);

        jsonObject.put(CommandConstants.COUNT_REMAINING_PARTS, _countRemainingParts);
        jsonObject.put(CommandConstants.COUNT_REMAINING_QUESTIONS, _countRemainingQuestions);

        return jsonObject.toJSONString();
    }
}
