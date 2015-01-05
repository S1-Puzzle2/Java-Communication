package at.fhv.puzzle2.communication.application.connection.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESEncryption implements Encryption {
    private Cipher _cipher;
    private SecretKeySpec _key;

    public AESEncryption(String key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        _cipher = Cipher.getInstance("AES/ECB/NoPadding");
        _key = new SecretKeySpec(key.getBytes(Charset.forName("UTF-8")), "AES");
    }

    @Override
    public byte[] encrypt(byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        _cipher.init(Cipher.ENCRYPT_MODE, _key);

        return _cipher.doFinal(data);
    }

    @Override
    public byte[] decrypt(byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        _cipher.init(Cipher.DECRYPT_MODE, _key);

        return _cipher.doFinal(data);
    }
}
