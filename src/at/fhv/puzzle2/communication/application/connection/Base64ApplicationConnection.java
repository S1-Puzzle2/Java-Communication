package at.fhv.puzzle2.communication.application.connection;

import at.fhv.puzzle2.communication.application.ApplicationMessage;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;

public class Base64ApplicationConnection extends ApplicationConnectionDecorator {
    public Base64ApplicationConnection(ApplicationConnection connection) {
        super(connection);
    }

    @Override
    public void sendApplicationMessage(ApplicationMessage message) throws IOException {
        byte[] messageData = message.getMessage().getBytes(Charset.forName("UTF-8"));
        message.setMessage(Base64.getEncoder().encode(messageData));

        _connection.sendApplicationMessage(message);
    }

    @Override
    public ApplicationMessage receiveMessage() throws IOException {
        ApplicationMessage message = _connection.receiveMessage();

        message.setMessage(Base64.getDecoder().decode(message.getMessage()));

        System.out.println("\nReceived app-message: " + message.getMessage());

        return message;
    }

    @Override
    public void close() throws IOException {
        _connection.close();
    }
}
