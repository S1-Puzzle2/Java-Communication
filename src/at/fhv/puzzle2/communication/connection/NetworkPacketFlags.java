package at.fhv.puzzle2.communication.connection;


import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.LinkedHashMap;

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
        HashMap<String, Boolean> map = new LinkedHashMap<String, Boolean>();
        map.put("ack", _acknowledge);
        map.put("close", _close);
        map.put("error", _error);

        return JSONValue.toJSONString(map);
    }

    public static NetworkPacketFlags parseJSON(JSONObject json) {
        return new NetworkPacketFlags((boolean)json.get("ack"), (boolean)json.get("close"), (boolean)json.get("error"));
    }
}
