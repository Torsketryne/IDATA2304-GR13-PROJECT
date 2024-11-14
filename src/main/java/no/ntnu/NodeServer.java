package no.ntnu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class NodeServer {

    private ConcurrentHashMap<Integer, Socket> activeNodes = new ConcurrentHashMap<>();

    // Method to add a new node to the activeNodes map
    public void addNode(int id, Socket socket) {
        activeNodes.put(id, socket);
        System.out.println("Node added with ID: " + id);
        // Start a new thread to handle communication with this node
        new Thread(() -> handleNode(socket, id)).start();
    }

    // Method to remove a node from the activeNodes map
    public void removeNode(int id) {
        Socket socket = activeNodes.remove(id);
        if (socket != null) {
            try {
                socket.close();
                System.out.println("Node removed with ID: " + id);
            } catch (IOException e) {
                System.out.println("Error closing socket for Node ID " + id + ": " + e.getMessage());
            }
        }
    }

    // Method to handle communication with a node
    private void handleNode(Socket socket, int id) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received from Node " + id + ": " + message);
                // Echo back the received message to the node
                writer.write("Acknowledged: " + message + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println("Error communicating with Node ID " + id + ": " + e.getMessage());
        } finally {
            // Remove the node when the connection is closed
            removeNode(id);
            System.out.println("Connection closed for Node ID " + id);
        }
    }

    // Method to broadcast a message to all connected nodes
    public void broadcast(String message) {
        for (Integer id : activeNodes.keySet()) {
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(activeNodes.get(id).getOutputStream()));
                writer.write(message + "\n");
                writer.flush();
                System.out.println("Broadcasted to Node " + id + ": " + message);
            } catch (IOException e) {
                System.out.println("Error broadcasting to Node ID " + id + ": " + e.getMessage());
            }
        }
    }
}
