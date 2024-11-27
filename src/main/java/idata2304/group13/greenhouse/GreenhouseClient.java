package idata2304.group13.greenhouse;
import java.io.*;
import java.net.*;

/**
 * A client for the greenhouse system.
 *
 * @author MoldyDaniel
 *
 * I have used the following sources to help me with this class:
 * <a href="https://www.geeksforgeeks.org/how-to-create-a-simple-tcp-client-server-connection-in-java/">Simple TCP client server connection</a>
 */
public class GreenhouseClient {

    /**
     * The host and port to connect to.
     * Also, the socket and streams for communication.
     */
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Create a new client for the greenhouse system.
     *
     * @param node The node to connect to.
     */
    public GreenhouseClient(SensorActuatorNode node) {
        try {
            clientSocket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("Connected to server at " + HOST + ":" + PORT);
        } catch (IOException e) {
            System.err.println("Error while running client: " + e.getMessage());
        }
    }



    /**
     * Send a message to the server.
     *
     * @param message The message to send.
     */

    public void sendMessage(String message) {
        out.println(message);
    }

    /**
     * Receive a message from the server.
     *
     * @return The message received.
     */
    public String receiveMessage() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.err.println("Error while receiving message: " + e.getMessage());
            return null;
        }
    }

    /**
     * Close the connection to the server.
     */
    public void close() {
        try {
            if (clientSocket != null){
                in.close();
                out.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error while closing socket: " + e.getMessage());
        }
    }

}
