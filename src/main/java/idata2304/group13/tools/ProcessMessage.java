package idata2304.group13.tools;

import java.util.Map;

/**
 * Process a message from a map of key-value pairs.
 */
public class ProcessMessage {


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
}
