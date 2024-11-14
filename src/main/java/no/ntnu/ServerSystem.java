package no.ntnu;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * ServerSystem class to handle the server side of the system
 * This class creates two server threads for Nodes and Panels
 * It also handles the connection of Nodes and Panels
 * It stores the active Nodes and Panels in a ConcurrentHashMap
 * It also handles the communication between Nodes and Panels
 *
 * Ai as well as some other sources were used to help create this class here are the sources:
 * https://www.geeksforgeeks.org/concurrenthashmap-in-java/
 * https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
 * https://www.geeksforgeeks.org/socket-programming-in-java/
 * https://dzone.com/articles/breaking-static-dependency
 * I also used the last projects code as a reference to help create this class
 * TODO: Might seperate the Node and Panel server into seperate classes and maybe create a class for the communication between Nodes and Panels like NodeHandler and no.ntnu.ControlPanelHandler
 *
 * @version 1.0
 * @since 2024-10-7
 *
 */
public class ServerSystem {
    public static final int Node_Port = 8080;
    public static final int Panel_Port = 1313;
    private int increment = 0;

    //Maps to store Active Nodes and Panels
    private ConcurrentHashMap<Integer, Socket> activeNodes = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Socket> activePanels = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        //Create a instance of the ServerSystem and start the servers so i can avoid using static methods
        ServerSystem serverSystem = new ServerSystem();
        serverSystem.startServers();
    }
    public void startServers() {
        //Create seperate server threads for Nodes and Panels
        new Thread(() -> startServer(Node_Port, activeNodes,"Node")).start();
        new Thread(() -> startServer(Panel_Port, activePanels,"ControlPanel")).start();
    }

    private void startServer(int nodePort, ConcurrentHashMap<Integer, Socket> activeMap, String node) {
        try {
            ServerSocket serverSocket = new ServerSocket(nodePort);
            System.out.println(node + " Server started on port " + nodePort);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(node + " connected: " + socket);
                int id = activeMap.size();
                activeMap.put(id, socket);

                //Handle the connection in a new thread
                new Thread(() -> handleConnection(socket, activeMap, id)).start();
            }
        } catch (IOException e) {
            System.out.println("Error starting " + node + " server: " + e.getMessage());
        }
    }

    private void handleConnection(Socket socket, ConcurrentHashMap<Integer, Socket> activeMap, int id) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            //Handle the communication between the Node and Panel
            while (reader.readLine() != null) {
                String message = reader.readLine();
                System.out.println(socket + " " + id + " received: " + message);
                writer.write("Received: " + message + "\n");
                writer.flush();
            }

            //Remove the connection from the active map after the connection is closed
            activeMap.remove(id);
            System.out.println("Connection closed: " + socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            System.out.println("Connection closed: " + socket);
        }
    }

}
