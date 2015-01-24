package at.fhv.puzzle2.communication.connection.networkPacket;

import at.fhv.puzzle2.communication.connection.MalformedNetworkPacketException;
import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.io.IOException;
import java.nio.charset.Charset;

public class NetworkPacketHandler {
    private volatile static int _sequenceID = 2333;
    private final NetworkConnection _networkConnection;

    public NetworkPacketHandler(NetworkConnection networkConnection) {
        _networkConnection = networkConnection;
    }

    public void sendMessage(String message) throws IOException {
        NetworkPacket packet = new NetworkPacket(NetworkPacketHandler.getNextSequenceID(), null, message);
        sendMessage(packet, true);
    }

    public void sendMessage(NetworkPacket packet, boolean resend) throws IOException {
        _networkConnection.sendBytes(packet.toJSONString().getBytes(Charset.forName("UTF-8")));

        if(resend) {
            //Store it inside NetworkPacketManager, which waits for an acknowledge or else sends the message again
            NetworkPacketManager.getInstance().sentNetworkPacket(packet, this);
        }

        System.out.println("Sent network packet: " + packet.toJSONString());
    }

    public String receiveMessage() throws IOException {
        while(true) {
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
                this.sendMessage(response, false);

                return packet.getApplicationMessage();
            }
        }
    }

    private NetworkPacket readMessage() throws IOException {
        NetworkPacket packet;

        while(true) {
            try {
                String read = new String(_networkConnection.readBytes(), Charset.forName("UTF-8"));
                System.out.println("Received network packet: " + read);
                packet = NetworkPacket.parse(read.getBytes());

                if(packet.checkSumCorrect()) {
                    return packet;
                }

                if(packet.getNetworkFlags() != null) {
                    continue;
                }

                NetworkPacketFlags flags = new NetworkPacketFlags();

                flags.setAcknowledge(false);
                NetworkPacket response = NetworkPacket.createResponse(packet.getSequenceID(), flags);

                this.sendMessage(response, false);
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
