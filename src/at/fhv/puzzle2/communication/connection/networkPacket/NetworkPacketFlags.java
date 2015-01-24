package at.fhv.puzzle2.communication.connection.networkPacket;


import org.json.simple.JSONAware;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class NetworkPacketFlags implements JSONAware {
    private static final String CLOSE = "close";
    private static final String ACK = "ack";

    private Boolean _acknowledge = null, _close = null;

    public NetworkPacketFlags(Boolean acknowledge, Boolean close) {
        _acknowledge = acknowledge;
        _close = close;
    }

    public NetworkPacketFlags() {

    }


    public Boolean getAcknowledge() {
        return _acknowledge;
    }

    public void setAcknowledge(Boolean acknowledge) {
        this._acknowledge = acknowledge;
    }

    public boolean isAcknowledgePresent() {
        return _acknowledge != null;
    }

    public Boolean getClose() {
        return _close;
    }

    public void setClose(Boolean close) {
        this._close = close;
    }

    public boolean isClosePresent() {
        return _close != null;
    }

    public String toJSONString() {
        HashMap<String, Boolean> map = new LinkedHashMap<>();
        if(_acknowledge != null) {
            map.put(ACK, _acknowledge);
        }

        if(_close != null) {
            map.put(CLOSE, _close);
        }

        return JSONValue.toJSONString(map);
    }

    public static NetworkPacketFlags parse(HashMap<String, Object> data) {
        NetworkPacketFlags flags = new NetworkPacketFlags();
        if(data.containsKey(ACK)) {
            flags._acknowledge = (Boolean) data.get(ACK);
        }

        if(data.containsKey(CLOSE)) {
            flags._close = (Boolean) data.get(CLOSE);
        }

        return flags;
    }
}
