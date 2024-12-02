package idata2304.group13.tools;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * Class for encrypting and decrypting strings.
 *
 * This class uses the AES encryption algorithm.
 *
 * The code is based on the example from GeeksforGeeks:
 * <href>https://www.geeksforgeeks.org/encrypt-and-decrypt-string-file-using-java/</href>
 *
 * @author MoldyDaniel
 */
public class Encryption {
    private SecretKey secretKey;



    /**
     * Initialize the encryption key.
     */
    public Encryption() {
        initalizeEncryption();
    }

    private void initalizeEncryption() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            secretKey = keyGenerator.generateKey();
        } catch (Exception e) {
            System.out.println("Error initializing encryption key: " + e.getMessage());
        }
    }

    /**
     * Encrypt a string using a key.
     *
     * @param str The String to encrypt
     * @return The encrypted String
     */
    public String encrypt(String str){
        if (str == null) {
            throw new IllegalArgumentException("String to encrypt can't be null");
        }
        try {
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedByte = aesCipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedByte);
        } catch (Exception e) {
            System.out.println("Error encrypting string: " + e.getMessage());
        }
        return null;
    }



    /**
     * Decrypt a string using a key.
     * @param str The String to decrypt
     * @return The Decrypted String
     */
    public String decrypt(String str) {
        if (str == null) {
            throw new IllegalArgumentException("String to decrypt can't be null");
        }
        try {
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedByte = Base64.getDecoder().decode(str);
            return new String(aesCipher.doFinal(decryptedByte), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}