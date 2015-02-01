package at.fhv.puzzle2.communication.connection.networkPacket;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.connection.MalformedNetworkPacketException;
import at.fhv.puzzle2.logging.LoggedObject;
import org.json.simple.JSONAware;
import org.json.simple.JSONValue;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class NetworkPacket implements JSONAware, LoggedObject, Comparable<NetworkPacket> {
    private static final String CHECKSUM = "checkSum";
    private static final String SEQUENCE_ID = "seqID";
    private static final String APP_MSG = "appMsg";
    private static final String FLAGS = "flags";

    private int _sequenceID;
    private long _checkSum;
    private String _appMsg;

    private int _priority;

    private boolean _resend = false;

    private NetworkPacketFlags _flags;

    public NetworkPacket(int sequenceID, NetworkPacketFlags flags, String appMsg, int priority) {
        this(sequenceID, flags, appMsg, priority, false);
    }

    public NetworkPacket(int sequenceID, NetworkPacketFlags flags, String appMsg, int priority, boolean resend) {
        _sequenceID = sequenceID;
        _flags = flags;
        _appMsg = appMsg;
        _checkSum = this.generateChecksum();

        _priority = priority;
        _resend = resend;
    }

    private NetworkPacket() {

    }

    public NetworkPacket(ApplicationMessage message, int sequenceID) {
        this(sequenceID, null, message.getMessage(), message.getPriority(), true);
    }

    public boolean shouldResend() {
        return _resend;
    }

    public static NetworkPacket createAcknowledge(NetworkPacket packet, boolean checkSumMatch) {
        NetworkPacketFlags flags = new NetworkPacketFlags(checkSumMatch, null);

        return new NetworkPacket(packet.getSequenceID(), flags, null, NetworkPacketPriority.ACK_PRIORITY);
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

    @Override
    public String getLogString() {
        HashMap<String, Object> packetData = getJSONObject();
        //Dont print the App-Message, its base64 so we cant read it anyway
        if(_appMsg != null) {
            packetData.put(APP_MSG, LOG_DATA_OMITTED);
        }

        return JSONValue.toJSONString(packetData);
    }

    @Override
    public int compareTo(NetworkPacket networkPacket) {
        if(this._priority == networkPacket._priority) {
            return 0;
        } else if(this._priority > networkPacket._priority) {
            return -1;
        }

        return 1;
    }
}
