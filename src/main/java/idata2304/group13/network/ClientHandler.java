package idata2304.group13.network;

import idata2304.group13.tools.MessageHandler;
import idata2304.group13.tools.ProcessMessage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

/**
 * This class handles communication with a client.
 */
public class ClientHandler implements Runnable{

  private Socket socket;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;
  private MessageHandler messageHandler;
  private ProcessMessage processMessage;
  private NodeControlPanelRelations relationships;
  private HashMap<String, String> commandBuffer;

  /**
   * Creates a new ClientHandler instance.
   *
   * @param socket The socket conected to the client.
   * @param relationships helper object to manage the relationships between nodes and control panel.
   * @param messageHandler helper objet to parse message received from the client.
   */
  public ClientHandler(Socket socket, NodeControlPanelRelations relationships, MessageHandler messageHandler) {
    this.socket = socket;
    this.relationships = relationships;
    this.messageHandler = messageHandler;
    this.processMessage = new ProcessMessage(relationships);
    initializeStreams(socket);
  }

  /**
   * Initialize input and output stream from communication with the client.
   *
   * @param socket The client socket used for communication.
   */
  private void initializeStreams(Socket socket) {
    try {
      socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      socketWriter = new PrintWriter(socket.getOutputStream(), true);
    } catch (IOException ioe) {
      System.err.println("Failed to prepare for sending and receiving messages: " + ioe);
    }
  }

  /**
   * The main logic for handling client communication.
   *
   * This method continuously reads message from the client, process them,
   * and send the responses back until the client disconnects.
   */
  @Override
  public void run() {
    System.out.println(Thread.currentThread().getName() + " is now running");
    String clientCommand;

    do {
      clientCommand = readClientMessage();
      processMessage.ProcessMessage(messageHandler.parseMessage(clientCommand));


      if (!clientCommand.isEmpty()) {
        writeResponseToClient(clientCommand);
        System.out.println(clientCommand);
      }
    } while(clientCommand != null);
  }

  /**
   * Reads message send by client.
   *
   * @return The message from the client if there was an error.
   */
  private String readClientMessage() {
    String commandStraightFromClient = null;
    try {
       commandStraightFromClient = socketReader.readLine();
    } catch (IOException ioe) {
      System.err.println("Failed to read a line: " + ioe);
    }
    return commandStraightFromClient;
  }

  /**
   * Send a response back to the client.
   *
   * @param response The message to send to client.
   */
  private void writeResponseToClient(String response) {
    socketWriter.println(response);
  }
}
