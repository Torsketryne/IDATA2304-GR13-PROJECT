package idata2304.group13.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler implements Runnable{

  private Socket socket;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;
  private HashMap<String, String> commandBuffer;

  public ClientHandler(Socket socket) {
    this.socket = socket;
    initializeStreams(socket);
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
}
