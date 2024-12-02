package idata2304.group13.tools;

import idata2304.group13.network.ClientHandler;
import idata2304.group13.network.NodeControlPanelRelations;
import java.util.Map;
import java.util.Set;


/**
 * Process a message from a map of key-value pairs.
 */
public class ProcessMessage {

    private final ClientHandler clientHandler;

    public ProcessMessage(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
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
                //alterNode(parsedData);
                break;
            case "Stop":

                break;
            default:
                Logger.error("Unknown command: " + MessageType);
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
}
