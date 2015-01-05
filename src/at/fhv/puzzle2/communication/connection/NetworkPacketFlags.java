package at.fhv.puzzle2.communication.connection;


import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class NetworkPacketFlags implements JSONAware {
    private boolean _acknowledge = false, _close = false, _error = false;

    public NetworkPacketFlags(boolean acknowledge, boolean close, boolean error) {
        _acknowledge = acknowledge;
        _close = close;
        _error = error;
    }


    public boolean getAcknowledge() {
        return _acknowledge;
    }

    public void setAcknowledge(boolean acknowledge) {
        this._acknowledge = acknowledge;
    }

    public boolean getClose() {
        return _close;
    }

    public void setClose(boolean close) {
        this._close = close;
    }

    public boolean getError() {
        return _error;
    }

    public void setError(boolean error) {
        this._error = error;
    }

    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("ack", _acknowledge);
        json.put("close", _close);
        json.put("error", _error);

        return json.toJSONString();
    }

    public static NetworkPacketFlags parseJSON(JSONObject json) {
        return new NetworkPacketFlags((boolean)json.get("ack"), (boolean)json.get("close"), (boolean)json.get("error"));
    }
}
