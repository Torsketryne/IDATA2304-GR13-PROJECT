package idata2304.group13.network;

import idata2304.group13.tools.MessageHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

  private static final int PORT = 1313;
  private ServerSocket serverSocket;
  private MessageHandler messageHandler;
  private ConcurrentHashMap<String, ClientHandler> clients;

  public Server() {}

  public static void main(String[] args) {
    Server server = new Server();
    server.run();
  }

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
   *
   * @return
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
   *
   * @return
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

  private boolean stopServer() {
    return false;
  }
}
