package at.fhv.puzzle2.communication.application.connection.encryption;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.application.connection.ApplicationConnection;
import at.fhv.puzzle2.communication.application.connection.ApplicationConnectionDecorator;
import at.fhv.puzzle2.communication.connection.NetworkConnection;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;

public class EncryptedApplicationConnection extends ApplicationConnectionDecorator {
    private Encryption _encryption;

    public EncryptedApplicationConnection(ApplicationConnection connection, Encryption encryption) {
        super(connection);

        _encryption = encryption;
    }

    @Override
    public void sendApplicationMessage(ApplicationMessage message) throws IOException {
        try {
            byte[] data = message.getMessage().getBytes(Charset.forName("UTF-8"));

            message.setMessage(_encryption.encrypt(data));

            _connection.sendApplicationMessage(message);
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            //TODO currently we catch it here, NOT a good idea
            e.printStackTrace();
        }
    }

    @Override
    public ApplicationMessage receiveMessage() throws IOException {
        ApplicationMessage message = _connection.receiveMessage();

        byte[] messageBytes = message.getMessage().getBytes(Charset.forName("UTF-8"));

        try {
            message.setMessage(_encryption.decrypt(messageBytes));

            return message;
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            //TODO currently we catch it here, NOT a good idea
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public void close() throws IOException {
        _connection.close();
    }
}
