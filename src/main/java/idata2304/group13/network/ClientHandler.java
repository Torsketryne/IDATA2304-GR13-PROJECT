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

public class ClientHandler implements Runnable{

  private Server server;
  private Socket socket;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;
  private MessageHandler messageHandler;
  private ProcessMessage processMessage;
  private NodeControlPanelRelations relationships;
  private HashMap<String, String> commandBuffer;
  private String clientId;

  public ClientHandler(Server server, Socket socket, NodeControlPanelRelations relationships, MessageHandler messageHandler) {
    this.server = server;
    initializeStreams(socket);
    this.clientId = readClientMessage();
    this.server.addClients(clientId, this);
    this.socket = socket;
    this.relationships = relationships;
    this.messageHandler = messageHandler;
    this.processMessage = new ProcessMessage(this);

  }

  private void initializeStreams(Socket socket) {
    try {
      socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      socketWriter = new PrintWriter(socket.getOutputStream(), true);
    } catch (IOException ioe) {
      System.err.println("Failed to prepare for sending and receiving messages: " + ioe);
    }
  }

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

  private String readClientMessage() {
    String commandStraightFromClient = null;
    try {
       commandStraightFromClient = socketReader.readLine();
    } catch (IOException ioe) {
      System.err.println("Failed to read a line: " + ioe);
    }
    return commandStraightFromClient;
  }

  private void writeResponseToClient(String response) {
    socketWriter.println(response);
  }

  public void createRelationship(String nodeId, String panelId) {
    relationships.addRelation(nodeId, panelId);
  }

  public boolean checkForOtherClient(String otherClientId) {
    boolean found = false;
    if (server.getClient(otherClientId) != null) {
      found = true;
    }
    return found;
  }
}
