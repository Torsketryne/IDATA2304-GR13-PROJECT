package idata2304.group13.greenhouse;
import idata2304.group13.tools.Logger;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private static final int PORT = 1313;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private String nodeId;
    private final SensorActuatorNode node;

    /**
     * Create a new client for the greenhouse system.
     */
    public GreenhouseClient(SensorActuatorNode node) {
        this.nodeId = "n" + new Random().nextInt(999);
        this.node = node;
    }
    public GreenhouseClient(int customId, SensorActuatorNode node) {
        this.nodeId = "n" + customId;
        this.node = node;
    }

    public void run() {
        try {
            clientSocket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(nodeId);
            Logger.info("Connected to server at " + HOST + ":" + PORT);
            connectToPanel("c0");
        } catch (IOException e) {
            Logger.error("Error while running client: " + e.getMessage());
        }
    }

    public void run(String panelId) {
        try {
            clientSocket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(nodeId);
            Logger.info("Connected to server at " + HOST + ":" + PORT);
            connectToPanel(panelId);
            sendSensorActuatorInfo();
        } catch (IOException e) {
            Logger.error("Error while running client: " + e.getMessage());
        }
    }

    public void sendSensorActuatorInfo() {
        for (String command : turnListOfActuatorsIntoListOfCommands()) {
            String messageType = "MessageType:NodeChange";
            out.println(messageType + command);
        }
        for (String command : turnListOfSensorsIntoListOfCommands()) {
            String messageType = "MessageType:NodeChange";
            out.println(messageType + command);
        }
    }

    public List<String> turnListOfActuatorsIntoListOfCommands() {
        ArrayList<String> listOfCommands = new ArrayList<>();
        for (Actuator actuator : node.getActuators()) {
            String command = ";Attachment:Actuator;NodeId:" + actuator.getNodeId() + ";Id:" + actuator.getId() + ";Type:" + actuator.getType() + ";State:" + actuator.isOn();
            listOfCommands.add(command);
        }
        return listOfCommands;
    }

    public List<String> turnListOfSensorsIntoListOfCommands() {
        ArrayList<String> listOfCommands = new ArrayList<>();
        for (Sensor sensor : node.getSensors()) {
            String command = ";Attachment:Sensor;id:" + sensor.getId() + ";Type:" + sensor.getType() + ";Value:" + sensor.getReading().getValue() + ";Unit:" + sensor.getReading().getUnit();
            listOfCommands.add(command);
        }
        return listOfCommands;
    }

    /**
     * Send a message to the server.
     *
     * @param message The message to send.
     */

    public void sendMessage(String message) {
        try {
            out.println(message);
        } catch (Exception e) {
            System.err.println("Error while sending message: " + e.getMessage());
        }
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

    public void connectToPanel(String panelId) {
        sendMessage("MessageType:Connect;" + "Source:" + this.nodeId + ";" + "Dest:" + panelId);
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
