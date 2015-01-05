package at.fhv.puzzle2.communication.application.connection;

import at.fhv.puzzle2.communication.application.model.ApplicationMessage;
import at.fhv.puzzle2.communication.connection.NetworkPacketManager;

import java.io.IOException;

public class BaseApplicationConnection implements ApplicationConnection {
    private NetworkPacketManager _packetManager;

    public BaseApplicationConnection(NetworkPacketManager packetManager) {
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
