package at.fhv.puzzle2.communication.connection.networkPacket;

import at.fhv.puzzle2.communication.connection.MalformedNetworkPacketException;
import org.json.simple.JSONAware;
import org.json.simple.JSONValue;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class NetworkPacket implements JSONAware {
    private static final String CHECKSUM = "checkSum";
    private static final String SEQUENCE_ID = "seqID";
    private static final String APP_MSG = "appMsg";
    private static final String FLAGS = "flags";
    private int _sequenceID;
    private long _checkSum;
    private String _appMsg;

    private NetworkPacketFlags _flags;

    public NetworkPacket(int sequenceID, NetworkPacketFlags flags, String appMsg) {
        _sequenceID = sequenceID;
        _flags = flags;
        _appMsg = appMsg;
        _checkSum = this.generateChecksum();
    }

    private NetworkPacket() {

    }

    public static NetworkPacket createResponse(int sequenceID, NetworkPacketFlags flags) {
        return new NetworkPacket(sequenceID, flags, null);
    }

    public NetworkPacketFlags getNetworkFlags() {
        return _flags;
    }

    public int getSequenceID() {
        return _sequenceID;
    }

    public static NetworkPacket parse(byte[] packetData) throws MalformedNetworkPacketException {
        String packetString = new String(packetData, Charset.forName("UTF-8"));

        @SuppressWarnings("unchecked") HashMap<String, Object> packetJSON = (HashMap<String, Object>) JSONValue.parse(packetString);

        if(packetJSON == null) {
            throw new MalformedNetworkPacketException(packetString);
        }

        try {
            NetworkPacket packet = new NetworkPacket();
            packet._sequenceID = new BigDecimal((Long) packetJSON.get(SEQUENCE_ID)).intValueExact();
            packet._checkSum = (long) packetJSON.get(CHECKSUM);
            if(packetJSON.containsKey(FLAGS)) {
                packet._flags = NetworkPacketFlags.parse((HashMap<String, Object>) packetJSON.get(FLAGS));
            }

            if(packetJSON.containsKey(APP_MSG)) {
                packet._appMsg = (String) packetJSON.get(APP_MSG);
            }

            return packet;
        } catch(Exception e) {
            throw new MalformedNetworkPacketException(packetString);
        }
    }

    public boolean checkSumCorrect() {
        return generateChecksum() == this._checkSum;
    }

    long generateChecksum() {
        HashMap<String, Object> map = this.getJSONObject();
        Checksum checksum = new CRC32();

        map.remove(CHECKSUM);

        byte[] jsonBytes = JSONValue.toJSONString(map).getBytes(Charset.forName("UTF-8"));
        checksum.update(jsonBytes, 0, jsonBytes.length);

        return checksum.getValue();
    }

    private HashMap<String, Object> getJSONObject() {
        HashMap<String, Object> map = new LinkedHashMap<>();

        map.put(SEQUENCE_ID, _sequenceID);
        if(_flags != null) {
            map.put(FLAGS, _flags);
        }

        if(_appMsg != null) {
            map.put(APP_MSG, _appMsg);
        }

        map.put(CHECKSUM, _checkSum);

        return map;
    }

    @Override
    public String toJSONString() {
        return JSONValue.toJSONString(this.getJSONObject());
    }

    public String getApplicationMessage() {
        return _appMsg;
    }

    public byte[] getBytes() {
        return toJSONString().getBytes(Charset.forName("UTF-8"));
    }
}
