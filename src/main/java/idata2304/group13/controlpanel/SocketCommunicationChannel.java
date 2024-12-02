package idata2304.group13.controlpanel;

import idata2304.group13.tools.MessageHandler;
import idata2304.group13.tools.ProcessMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

/**
 * A real communication channel. Emulates the node discovery (over the Internet).
 * In practice - spawn some events at specified time (specified delay).
 * Note: this class is used only for debugging, you can remove it in your final project!
 */
public class SocketCommunicationChannel extends Thread implements CommunicationChannel{

  private final ControlPanelLogic logic;
  private static final int PORT = 1313;
  private static final String HOST = "localhost";
  private BufferedReader socketReader;
  private PrintWriter socketWriter;
  private Socket socket;
  private String panelId;
  private Thread listeningThread;

  /**
   * Create a new real communication channel.
   *
   * @param logic The application logic of the control panel node.
   */
  public SocketCommunicationChannel(ControlPanelLogic logic) {
    this.logic = logic;
    this.panelId = "c" + new Random().nextInt(999);
    initializeStreams(socket);
    socketWriter.println(panelId);
  }

  public SocketCommunicationChannel(ControlPanelLogic logic, int customId) {
    this.logic = logic;
    this.panelId = "c" + customId;
  }

  private void initializeStreams(Socket socket) {
    try {
      socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      socketWriter = new PrintWriter(socket.getOutputStream(), true);
    } catch (IOException ioe) {
      System.err.println("Failed to prepare for sending and receiving messages: " + ioe);
    }
  }

  private void startListening() {
    listeningThread = new Thread(() -> {
      try {
        String message;
        ProcessMessage processMessage = new ProcessMessage(this);
        while ((message = socketReader.readLine()) != null) {
          processMessage.ProcessMessage(new MessageHandler().parseMessage(message));
        }
      } catch (IOException e) {
        System.err.println("Error reading from socket: " + e.getMessage());
      } finally {
        close();
      }
    });
    listeningThread.start();
  }


  private void close() {
    try {
      if (socket != null && !socket.isClosed()) {
        socket.close();
      }
    } catch (IOException e) {
      System.err.println("Error closing socket: " + e.getMessage());
    }
  }

  @Override
  public boolean open() {
    boolean opened = true;
    try {
      this.socket = new Socket(HOST, PORT);
      initializeStreams(socket);
      socketWriter.println(panelId);
      startListening();
    } catch (IOException ioe) {
      opened = false;
      System.err.println("Failed to connect to socket: " + ioe);
    }
    return opened;
  }

  public void sendActuatorChange(int nodeId, int actuatorId, boolean isOn) {

  }
}