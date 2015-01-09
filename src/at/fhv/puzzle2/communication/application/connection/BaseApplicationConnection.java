package at.fhv.puzzle2.communication.application.connection;

import at.fhv.puzzle2.communication.application.model.ApplicationMessage;
import at.fhv.puzzle2.communication.connection.networkPacket.NetworkPacketHandler;

import java.io.IOException;

public class BaseApplicationConnection implements ApplicationConnection {
    private NetworkPacketHandler _packetManager;

    public BaseApplicationConnection(NetworkPacketHandler packetManager) {
        _packetManager = packetManager;
    }

    @Override
    public void sendApplicationMessage(ApplicationMessage message) throws IOException {
        _packetManager.sendApplicationMessage(message);
    }

    @Override
    public ApplicationMessage readApplicationMessage() throws IOException {
        ApplicationMessage message = _packetManager.receiveMessage();

        //We need to know up the stack where we got this from
        message.setSender(this);

        return message;
    }

    @Override
    public void close() throws IOException {
        _packetManager.close();
    }
}
