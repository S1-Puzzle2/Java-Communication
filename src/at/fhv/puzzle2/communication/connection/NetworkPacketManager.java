package at.fhv.puzzle2.communication.connection;

import at.fhv.puzzle2.communication.application.model.ApplicationMessage;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class NetworkPacketManager {
    private static int _sequenceID = 0;
    private NetworkConnection _networkConnection;

    public NetworkPacketManager(NetworkConnection networkConnection) {
        _networkConnection = networkConnection;
    }

    public void sendApplicationMessage(ApplicationMessage message) throws IOException {
        JSONObject packet = new JSONObject();

        packet.put("seqID", NetworkPacketManager.getNextSequenceID());
        packet.put("appMsg", message);
        packet.put("checkSum", 0L);

        _networkConnection.sendBytes(packet.toJSONString().getBytes(Charset.forName("UTF-8")));
    }

    public ApplicationMessage receiveMessage() throws IOException {
        byte[] packet = _networkConnection.readBytes();

        JSONObject packetJSON = (JSONObject) JSONValue.parse(new String(packet, Charset.forName("UTF-8")));

        //TODO check checksum
        String acknowledge = createAcknowledge(new BigDecimal((long)packetJSON.get("seqID")).intValueExact());

        _networkConnection.sendBytes(acknowledge.getBytes(Charset.forName("UTF-8")));

        return new ApplicationMessage((String) packetJSON.get("appMsg"));
    }

    private String createAcknowledge(int sequenceID) {
        JSONObject obj = new JSONObject();

        obj.put("seqID", sequenceID);
        obj.put("flags", new PacketFlags(true, false, false));


        Checksum checksum = new CRC32();

        byte[] jsonBytes = obj.toJSONString().getBytes(Charset.forName("UTF-8"));
        checksum.update(jsonBytes, 0, jsonBytes.length);

        obj.put("checkSum", checksum.getValue());

        return obj.toJSONString();
    }

    private static int getNextSequenceID() {
        return _sequenceID++;
    }

    public void close() throws IOException {
        _networkConnection.close();
    }
}
