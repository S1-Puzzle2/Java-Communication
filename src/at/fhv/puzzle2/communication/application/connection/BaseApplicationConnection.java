package at.fhv.puzzle2.communication.application.connection;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.communication.connection.networkPacket.NetworkPacketHandler;

import java.util.Optional;

public class BaseApplicationConnection implements ApplicationConnection {
    private final NetworkPacketHandler _packetManager;

    public BaseApplicationConnection(NetworkPacketHandler packetManager) {
        _packetManager = packetManager;
    }

    @Override
    public void sendApplicationMessage(ApplicationMessage applicationMessage) {
        _packetManager.sendMessage(applicationMessage);
    }

    @Override
    public Optional<ApplicationMessage> receiveMessage() {
        Optional<String> message = _packetManager.receiveMessage();

        return message.map(string -> new ApplicationMessage(this, string));
    }

    @Override
    public void close() {
        _packetManager.close();
    }

    @Override
    public NetworkConnection getUnderlyingConnection() {
        return _packetManager.getUnderlyingConnection();
    }
}
