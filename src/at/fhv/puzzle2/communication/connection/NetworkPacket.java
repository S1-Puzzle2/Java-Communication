package at.fhv.puzzle2.communication.connection;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;
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

    public static NetworkPacket parse(byte[] packetData) throws MalformedNetworkPacketException {
        String packetString = new String(packetData, Charset.forName("UTF-8"));
        HashMap<String, Object> packetJSON = (HashMap<String, Object>) JSONValue.parse(packetString);

        if(packetJSON == null) {
            throw new MalformedNetworkPacketException(packetString);
        }

        try {
            NetworkPacket packet = new NetworkPacket();
            packet._sequenceID = new BigDecimal((Long) packetJSON.get("seqID")).intValueExact();
            packet._checkSum = (long) packetJSON.get("checkSum");
            if(packetJSON.containsKey("flags")) {
                packet._flags = NetworkPacketFlags.parseJSON((JSONObject) packetJSON.get("flags"));
            }

            if(packetJSON.containsKey("appMsg")) {
                packet._appMsg = (String) packetJSON.get("appMsg");
            }

            return packet;
        } catch(Exception e) {
            throw new MalformedNetworkPacketException(packetString);
        }
    }

    public boolean checkSumCorrect() {
        return generateChecksum() == this._checkSum;
    }

    public long generateChecksum() {
        HashMap<String, Object> map = this.getJSONObject();
        Checksum checksum = new CRC32();

        map.remove("checkSum");

        byte[] jsonBytes = JSONValue.toJSONString(map).getBytes(Charset.forName("UTF-8"));
        checksum.update(jsonBytes, 0, jsonBytes.length);

        return checksum.getValue();
    }

    private HashMap<String, Object> getJSONObject() {
        HashMap<String, Object> map = new LinkedHashMap<>();

        map.put("seqID", _sequenceID);
        if(_flags != null) {
            map.put("flags", _flags);
        }

        if(_appMsg != null) {
            map.put("appMsg", _appMsg);
        }

        map.put("checkSum", _checkSum);

        return map;
    }

    @Override
    public String toJSONString() {
        return JSONValue.toJSONString(this.getJSONObject());
    }

    public String getApplicationMessage() {
        return _appMsg;
    }
}
