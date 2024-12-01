package idata2304.group13.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * Handle messages.
 */
public class MessageHandler {

    /**
     * Parse a message from a string into a map of key-value pairs.
     *
     * @param message The message to parse
     * @return A map of key-value pairs
     */
    public Map<String, String> parseMessage(String message) {
        Map<String, String> parsedData = new HashMap<>();
        String[] data = message.split(";");

        // Split the data into key-value pairs and put them into a map
        for (String s : data) {
            String[] keyValue = s.split(":");
            if (keyValue.length == 2) {
                parsedData.put(keyValue[0], keyValue[1]);
            }
        }
        return parsedData;
    }


}
