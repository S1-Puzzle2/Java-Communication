package at.fhv.puzzle2.communication.application.connection.encryption;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.application.connection.ApplicationConnection;
import at.fhv.puzzle2.communication.application.connection.ApplicationConnectionDecorator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.util.Optional;

public class EncryptedApplicationConnection extends ApplicationConnectionDecorator {
    private final Encryption _encryption;

    public EncryptedApplicationConnection(ApplicationConnection connection, Encryption encryption) {
        super(connection);

        _encryption = encryption;
    }

    @Override
    public void sendApplicationMessage(ApplicationMessage message) {
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
    public Optional<ApplicationMessage> receiveMessage() {
        Optional<ApplicationMessage> message = _connection.receiveMessage();

        return message.map(appMessage -> {
            byte[] messageBytes = appMessage.getMessage().getBytes(Charset.forName("UTF-8"));

            try {
                appMessage.setMessage(_encryption.decrypt(messageBytes));

                return appMessage;
            } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
                //TODO currently we catch it here, NOT a good idea
                e.printStackTrace();

                return null;
            }
        });
    }

    @Override
    public void close() {
        _connection.close();
    }
}
