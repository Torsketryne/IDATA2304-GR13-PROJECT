import org.junit.jupiter.api.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

public class ServerSystemTest {

    private ServerSystem serverSystem;
    private ConcurrentHashMap<Integer, Socket> testActiveNodes;
    private ConcurrentHashMap<Integer, Socket> testActivePanels;

    @BeforeEach
    void setUp() {
        serverSystem = new ServerSystem();
        testActiveNodes = new ConcurrentHashMap<>();
        testActivePanels = new ConcurrentHashMap<>();
    }

    @Test
    void testStartServers() {
        serverSystem.startServers();
        assertNotNull(serverSystem);
    }





}
