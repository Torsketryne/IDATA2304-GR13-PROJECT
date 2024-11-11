import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class NodeServer {

    private ConcurrentHashMap<Integer, Socket> Nodes = new ConcurrentHashMap<>();

    public void addNode(int id, Socket socket) {
        Nodes.put(id, socket);
        System.out.println("Node added with ID: " + id);

        // Start a new thread to handle communication with this node
        new Thread(() -> handleNode(socket, id)).start();
    }

    public void removeNode(int id) {
        Socket socket = Nodes.remove(id);
        if (socket != null) {
            try {
                socket.close();
                System.out.println("Node removed with ID: " + id);
            } catch (IOException e) {
                System.out.println("Error closing socket for Node ID " + id + ": " + e.getMessage());
            }
        }
    }

    private void handleNode(Socket socket, int id) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received from Node " + id + ": " + message);
                writer.write("Acknowledged: " + message + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println("Error communicating with Node ID " + id + ": " + e.getMessage());
        } finally {
            removeNode(id);
            System.out.println("Connection closed for Node ID " + id);
        }
    }
}
