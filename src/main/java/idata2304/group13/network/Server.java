package idata2304.group13.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  private static final int PORT = 1313;
  private ServerSocket serverSocket;

  public Server() {}

  public static void main(String[] args) {
    Server server = new Server();
    server.run();
  }

  public void run(){
    this.serverSocket = openListeningSocket();

    boolean running = true;
    while(running) {
      Socket socket = acceptNextClient();
      if (socket != null) {
        Thread handleThread = new Thread(new ClientHandler(socket));
        handleThread.start();
      }
      running = stopServer();
    }
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
    } catch (IOException ioe) {
      System.err.println("Failed to accept a client: " + ioe);
    }
    return socket;
  }

  private boolean stopServer() {
    return false;
  }
}
