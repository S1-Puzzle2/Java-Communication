package at.fhv.puzzle2.communication.connection.networkPacket;

import at.fhv.puzzle2.communication.ConnectionClosedException;
import at.fhv.puzzle2.communication.connection.MalformedNetworkPacketException;
import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

public class NetworkPacketHandler {
    private static int _sequenceID = 0;
    private NetworkConnection _networkConnection;

    public NetworkPacketHandler(NetworkConnection networkConnection) {
        _networkConnection = networkConnection;
    }

    public void sendMessage(String message) throws IOException {
        NetworkPacket packet = new NetworkPacket(NetworkPacketHandler.getNextSequenceID(), null, message);

        _networkConnection.sendBytes(packet.toJSONString().getBytes(Charset.forName("UTF-8")));

        //Store it inside NetworkPacketManager, which waits for an acknowledge or else sends the message again
        Date currentDate = new Date();
        NetworkPacketManager.getInstance().sentNetworkPacket(packet, _networkConnection, currentDate);
    }

    public String receiveMessage() throws IOException {
        NetworkPacket packet = readMessage();

        if(packet.getNetworkFlags() != null) {
            //So it should be an acknowledge, an error or the connection gets closed
            if(packet.getNetworkFlags().isAcknowledgePresent()) {
                NetworkPacketManager.getInstance().receivedAcknowledge(packet);

            } else if(packet.getNetworkFlags().isClosePresent() && packet.getNetworkFlags().getClose()) {
                _networkConnection.close();
            }
        } else {
            NetworkPacketFlags flags = new NetworkPacketFlags();
            flags.setAcknowledge(true);

            NetworkPacket response = NetworkPacket.createResponse(packet.getSequenceID(), flags);
            _networkConnection.sendBytes(response.getBytes());
        }

        return packet.getApplicationMessage();
    }

    private NetworkPacket readMessage() throws IOException {
        NetworkPacket packet;

        while(true) {
            try {
                packet = NetworkPacket.parse(_networkConnection.readBytes());

                if(packet.checkSumCorrect()) {
                    return packet;
                } else {
                    NetworkPacketFlags flags = new NetworkPacketFlags();

                    flags.setAcknowledge(false);
                    NetworkPacket response = NetworkPacket.createResponse(packet.getSequenceID(), flags);

                    _networkConnection.sendBytes(response.getBytes());
                }
            } catch(MalformedNetworkPacketException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static int getNextSequenceID() {
        return _sequenceID++;
    }

    public void close() throws IOException {
        _networkConnection.close();
    }

    public NetworkConnection getUnderlyingConnection() {
        return _networkConnection;
    }
}
