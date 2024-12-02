package idata2304.group13.network;

import idata2304.group13.tools.MessageHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple server to handle client connections.
 *
 * This server listen for incoming client connection on a specific port, accept connection and
 * assign each client to seperate thread for handling communication.
 */
public class Server {

  private static final int PORT = 1313;
  private ServerSocket serverSocket;
  private MessageHandler messageHandler;
  private ConcurrentHashMap<String, ClientHandler> clients;

  /**
   * Create a new Server instance.
   *
   * The server can start.
   */
  public Server() {}

  /**
   * The main entry point for the server application.
   *
   * This method creates a new server instance and start it.
   *
   * @param args Command-line argument
   */
  public static void main(String[] args) {
    Server server = new Server();
    server.run();
  }

  /**
   * Start the server and listens for the client connection.
   *
   * This method create a listening socket, waits for clients to connect,
   * and start a new thread for each client using the class.
   */
  public void run(){
    this.serverSocket = openListeningSocket();
    if (serverSocket != null) {
      NodeControlPanelRelations relationships = new NodeControlPanelRelations();
      messageHandler = new MessageHandler();
      clients = new ConcurrentHashMap<>();
      //boolean running = true;
      while (true) {
        Socket socket = acceptNextClient();
        if (socket != null) {
          Thread handleThread = new Thread(new ClientHandler(this, socket, relationships, messageHandler));
          handleThread.start();
        }
        //running = stopServer();
      }
    }
  }

  public void addClients(String clientId, ClientHandler client) {
    clients.put(clientId, client);
  }

  public ClientHandler getClient(String clientId) {
    return clients.get(clientId);
  }

  /**
   *Opens a socket to listen for incoming client connection.
   *
   * This method binds the server to specific port 1313 and starts listening for connection.
   *
   * @return A object for accepting client connections, if there is an error.
   * @author Girst, Torsketryne
   */
  private ServerSocket openListeningSocket(){
    ServerSocket welcomeSocket = null;
    try {
      welcomeSocket = new ServerSocket(PORT);
      System.out.println("Server is now listening to port: " + PORT);
    } catch (IOException ioe) {
      System.err.println("Failed to open a listening socket: " + ioe);
    }
    return welcomeSocket;
  }

  /**
   *Accepts the next client that connects to the server.
   *
   * This method wait for the client to connect, then return the client socket.
   *
   * @return A object representing the client's connection, if there is as error.
   * @author Girst, Torsketryne
   */
  private Socket acceptNextClient(){
    Socket socket = null;
    try {
      socket = serverSocket.accept();
      System.out.println("New client connected");
    } catch (IOException ioe) {
      System.err.println("Failed to accept a client: " + ioe);
    }
    return socket;
  }

  /**
   * Stops the server.
   *
   * @return Always return the value of false.
   */
  private boolean stopServer() {
    return false;
  }
}
