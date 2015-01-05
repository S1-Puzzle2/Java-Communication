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
        NetworkPacket packet = new NetworkPacket(NetworkPacketManager.getNextSequenceID(), null, message.getMessage());

        _networkConnection.sendBytes(packet.toJSONString().getBytes(Charset.forName("UTF-8")));
    }

    public ApplicationMessage receiveMessage() throws IOException {
        NetworkPacket packet;
        //TODO sent response if something failed, for now we just ignore malformed packets or wrong checksums
        do {
            packet = NetworkPacket.parse(retrieveMessage());
        } while(!packet.checkSumCorrect());

        //Send an acknowledge back
        String acknowledge = createAcknowledge(packet.getSequenceID());
        _networkConnection.sendBytes(acknowledge.getBytes(Charset.forName("UTF-8")));

        return new ApplicationMessage(packet.getApplicationMessage());
    }

    private JSONObject retrieveMessage() throws IOException {
        byte[] receivedBytes = _networkConnection.readBytes();

        return (JSONObject) JSONValue.parse(new String(receivedBytes, Charset.forName("UTF-8")));
    }

    private String createAcknowledge(int sequenceID) {
        NetworkPacket packet = new NetworkPacket(sequenceID, new NetworkPacketFlags(true, false, false), null);

        return packet.toJSONString();
    }

    private static int getNextSequenceID() {
        return _sequenceID++;
    }

    public void close() throws IOException {
        _networkConnection.close();
    }
}
