package idata2304.group13.tools;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


/**
 * Class for encrypting and decrypting strings.
 *
 * https://www.geeksforgeeks.org/encrypt-and-decrypt-string-file-using-java/
 */
public class Encryption {
    private static SecretKey secretKey;



    /**
     * Initialize the encryption key.
     */
    public void InitalizeEncryption() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
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
    public static String encrypt(String str){
        try {
            Cipher desCipher;
            desCipher = Cipher.getInstance("DES");
            byte[] text = str.getBytes("UTF8");
            desCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedText = desCipher.doFinal(text);
            String encryptedString = new String(encryptedText);
            return encryptedString;
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
    public static String decrypt(String str) {
        try {
            Cipher desCipher = Cipher.getInstance("DES");
            desCipher.init(Cipher.DECRYPT_MODE, secretKey);
            String decryptedString = new String(desCipher.doFinal(str.getBytes()));
            return decryptedString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
