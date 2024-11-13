import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;



public class ActuatorNode {
    private String nodeId;
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private Map<String, Boolean> nodeStatus;

    public ActuatorNode(){
        this.nodeId = nodeId;
        try{
            this.socket = new Socket(String.valueOf(ServerSystem.Node_Port), ServerSystem.Panel_Port);
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nodeStatus = new HashMap<>();
            this.initializeActuatorNode();

        }catch(IOException e){
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }

    // Method to initialize the actuator node
    private void initializeActuatorNode() {
        nodeStatus.put("Fan", false);
        nodeStatus.put("Heater", false);
    }

    // Method to get the status of the actuator node
    public Map<String, Boolean> getNodeStatus() {
        return nodeStatus;
    }

    //Method to receive and handle commands from the server
    public void receiveCommands() {
        try {
            String command;
            while ((command = reader.readLine()) != null) {
                System.out.println("Received command for " + this.nodeId + ": " + command);
                handleCommand(command);
            }
        } catch (IOException e) {
            System.out.println("Error receiving commands for Node ID " + this.nodeId + ": " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing socket for Node ID " + this.nodeId + ": " + e.getMessage());
            }
        }
    }

    // Simulated action based on the command received
    private void handleCommand(String command) {
        switch (command.toLowerCase()) {
            case "activate temperature":
                System.out.println("Activating temperature on " + nodeId);
                break;
            case "deactivate temperature":
                System.out.println("Deactivating temperature on " + nodeId);
                break;
            case "turn on light":
                System.out.println("Turning on light on " + nodeId);
                break;
            case "turn off light":
                System.out.println("Turning off light on " + nodeId);
                break;
            case "turn on nitrogen":
                System.out.println("Activate nitrogen on " + nodeId);
                break;
            case "turn off nitrogen":
                System.out.println("Deactivating nitrogen on " + nodeId);
                break;
            case "turn on humidity":
                System.out.println("Activate humidity on " + nodeId);
                break;
            case "turn off humidity":
                System.out.println("Deactivating humidity on " + nodeId);
                break;
            default:
                System.out.println("Unknown command received: " + command);
                break;
        }
    }
}

