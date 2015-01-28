package at.fhv.puzzle2.communication.application.connection;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.communication.connection.networkPacket.NetworkPacketHandler;

import java.io.IOException;

public class BaseApplicationConnection implements ApplicationConnection {
    private final NetworkPacketHandler _packetManager;

    public BaseApplicationConnection(NetworkPacketHandler packetManager) {
        _packetManager = packetManager;
    }

    @Override
    public void sendApplicationMessage(ApplicationMessage applicationMessage) {
        _packetManager.sendMessage(applicationMessage.getMessage());
    }

    @Override
    public ApplicationMessage receiveMessage() throws IOException {
        return new ApplicationMessage(this, _packetManager.receiveMessage());
    }

    @Override
    public void close() throws IOException {
        _packetManager.close();
    }

    @Override
    public NetworkConnection getUnderlyingConnection() {
        return _packetManager.getUnderlyingConnection();
    }
}
