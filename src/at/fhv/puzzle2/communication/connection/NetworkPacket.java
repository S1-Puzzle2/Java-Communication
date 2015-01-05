package at.fhv.puzzle2.communication.connection;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class NetworkPacket implements JSONAware {
    private int _sequenceID;
    private long _checkSum;
    private String _appMsg;

    NetworkPacketFlags _flags;

    public NetworkPacket(int sequenceID, NetworkPacketFlags flags, String appMsg) {
        _sequenceID = sequenceID;
        _flags = flags;
        _appMsg = appMsg;
        _checkSum = this.generateChecksum();
    }

    public NetworkPacket() {

    }

    public int getSequenceID() {
        return _sequenceID;
    }

    public long getCheckSum() {
        return _checkSum;
    }

    public static NetworkPacket parse(JSONObject json) {
        NetworkPacket packet = new NetworkPacket();
        packet._sequenceID = new BigDecimal((Long) json.get("seqID")).intValueExact();
        packet._checkSum = (long) json.get("checkSum");
        if(json.containsKey("flags")) {
            packet._flags = NetworkPacketFlags.parseJSON((JSONObject) json.get("flags"));
        }

        if(json.containsKey("appMsg")) {
            packet._appMsg = (String) json.get("appMsg");
        }

        return packet;
    }

    public boolean checkSumCorrect() {
        return generateChecksum() == this._checkSum;
    }

    public long generateChecksum() {
        JSONObject json = this.getJSONObject();
        Checksum checksum = new CRC32();

        json.remove("checkSum");

        byte[] jsonBytes = json.toJSONString().getBytes(Charset.forName("UTF-8"));
        checksum.update(jsonBytes, 0, jsonBytes.length);

        return checksum.getValue();
    }

    private JSONObject getJSONObject() {
        JSONObject json = new JSONObject();

        json.put("seqID", _sequenceID);
        json.put("checkSum", _checkSum);

        if(_appMsg != null) {
            json.put("appMsg", _appMsg);
        }

        if(_flags != null) {
            json.put("flags", _flags);
        }

        return  json;
    }

    @Override
    public String toJSONString() {
        return this.getJSONObject().toJSONString();
    }

    public String getApplicationMessage() {
        return _appMsg;
    }
}
