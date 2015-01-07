package at.fhv.puzzle2.communication.connection;

import at.fhv.puzzle2.communication.application.model.ApplicationMessage;

import java.io.IOException;
import java.nio.charset.Charset;

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
        NetworkPacket packet = null;
        //TODO sent response if something failed, for now we just ignore malformed packets or wrong checksums
        boolean isPacketCorrect = false;
        while(!isPacketCorrect) {
            try {
                packet = NetworkPacket.parse(_networkConnection.readBytes());

                if(packet.checkSumCorrect()) {
                    isPacketCorrect = true;
                }
            } catch(MalformedNetworkPacketException e) {
                System.out.println(e.getMessage());
                //TODO Packet is malformed, send error back
            }
        }

        //Send an acknowledge back
        String acknowledge = createAcknowledge(packet.getSequenceID());
        _networkConnection.sendBytes(acknowledge.getBytes(Charset.forName("UTF-8")));

        return new ApplicationMessage(packet.getApplicationMessage());
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
