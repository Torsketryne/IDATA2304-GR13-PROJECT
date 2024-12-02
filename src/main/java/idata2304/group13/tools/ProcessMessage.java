package idata2304.group13.tools;

import static java.lang.Integer.parseInt;

import idata2304.group13.controlpanel.SocketCommunicationChannel;
import idata2304.group13.greenhouse.Actuator;
import idata2304.group13.greenhouse.ActuatorInfo;
import idata2304.group13.network.ClientHandler;
import idata2304.group13.network.NodeControlPanelRelations;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Process a message from a map of key-value pairs.
 */
public class ProcessMessage {

    private ClientHandler clientHandler;
    private SocketCommunicationChannel socketCommunicationChannel;
    private int count;

    public ProcessMessage(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }
    public ProcessMessage(SocketCommunicationChannel socketCommunicationChannel) {
        this.socketCommunicationChannel = socketCommunicationChannel;
    }

    /**
     * Process a message from a map of key-value pairs.
     *
     * @param parsedData The message to process
     */
    public void ProcessMessage(Map<String,String> parsedData) {
        String MessageType = parsedData.get("MessageType");
        if (MessageType == null) {
            Logger.error("No command in message");
            return;
        }
        switch (MessageType) {
            case "Connect":
                makeConnection(parsedData);
                break;
            case "SensorData":

                break;
            case "ActuatorState":

                break;
            case "NodeChange":
                alterNode(parsedData);
                break;
            case "Stop":

                break;
            case "Message":
                printMessage(parsedData.get("Content"));
            default:
                Logger.error("Unknown command: " + MessageType);
        }

    }

    private void alterNode(Map<String, String> parsedData) {
        String nodeType = parsedData.get("NodeType");
        //String state = parsedData.get("State");

        if (nodeType == null) {
            Logger.error("Missing ID or state for node");
        } else {
            if (nodeType.equals("Sensor")) {

            } else if (nodeType.equals("Actuator")) {
                ActuatorInfo actuatorInfo = new ActuatorInfo(parseInt(parsedData.get("id")), parsedData.get("Type"), parsedData.get("State").equals("true"));
                count++;
            } else {
                Logger.error("Unknown node type: " + nodeType);
            }

        }
    }

    private void makeConnection(Map<String,String> parsedData) {
        String sourceId = parsedData.get("Source");
        String destId = parsedData.get("Dest");

        if (clientHandler.checkForOtherClient(destId)) {
            if (sourceId == null || destId == null) {
                clientHandler.writeResponseToClient("Missing one or both IDs");
            } else {
                String nodeId = sourceId.contains("n") ? sourceId : destId;
                String panelId = sourceId.contains("c") ? sourceId : destId;

                clientHandler.createRelationship(nodeId, panelId);
                clientHandler.writeResponseToClient("You have successfully connected to: " + destId);
            }
        } else {
            clientHandler.writeResponseToClient("Could not find any nodes with given ID");
        }
    }

    private void printMessage(String message) {
        System.out.println(message);
    }
}
