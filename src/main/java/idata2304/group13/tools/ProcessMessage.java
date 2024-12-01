package idata2304.group13.tools;

import idata2304.group13.network.NodeControlPanelRelations;
import java.util.Map;


/**
 * Process a message from a map of key-value pairs.
 */
public class ProcessMessage {

    private final NodeControlPanelRelations relations;

    public ProcessMessage(NodeControlPanelRelations relations) {
        this.relations = relations;
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

        if (sourceId == null || destId == null) {
            Logger.error("Missing ID for source or destination");
        } else {
            String nodeId = sourceId.contains("n") ? sourceId : destId;
            String panelId = sourceId.contains("c") ? sourceId : destId;

            this.relations.addRelation(nodeId, panelId);
            Logger.info("SensorNode " + nodeId + " is now connected with control panel " + panelId);
        }
    }
}
