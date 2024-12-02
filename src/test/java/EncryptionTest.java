package idata2304.group13.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Encryption class.
 *
 * @author MoldyDaniel
 */
class EncryptionTest {
    private Encryption encryption;

    @BeforeEach
    void setUp() {
        encryption = new Encryption();
    }

    @Test
    void testEncrypt() {
        String str = "Hello, World!";
        String encrypted = encryption.encrypt(str);
        assertNotEquals(str, encrypted);
    }

    @Test
    void testDecrypt() {
        String str = "Hello, World!";
        String encrypted = encryption.encrypt(str);
        String decrypted = encryption.decrypt(encrypted);
        assertEquals(str, decrypted);
    }
}