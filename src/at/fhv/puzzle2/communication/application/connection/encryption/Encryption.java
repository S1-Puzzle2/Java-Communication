package at.fhv.puzzle2.communication.application.connection.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;

public interface Encryption {
    public byte[] encrypt(byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;
    public byte[] decrypt(byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;
}
