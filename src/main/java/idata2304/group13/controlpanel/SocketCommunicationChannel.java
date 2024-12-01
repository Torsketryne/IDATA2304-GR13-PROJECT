package idata2304.group13.controlpanel;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

/**
 * A real communication channel. Emulates the node discovery (over the Internet).
 * In practice - spawn some events at specified time (specified delay).
 * Note: this class is used only for debugging, you can remove it in your final project!
 */
public class SocketCommunicationChannel implements CommunicationChannel{

  private final ControlPanelLogic logic;
  private static final int PORT = 1313;
  private static final String HOST = "localhost";
  private Socket socket;
  private String panelId;

  /**
   * Create a new real communication channel.
   *
   * @param logic The application logic of the control panel node.
   */
  public SocketCommunicationChannel(ControlPanelLogic logic) {
    this.logic = logic;
    this.panelId = "c" + new Random().nextInt(999);
  }

  public SocketCommunicationChannel(ControlPanelLogic logic, int customId) {
    this.logic = logic;
    this.panelId = "c" + customId;
  }

  @Override
  public boolean open() {
    boolean opened = true;
    try {
      this.socket = new Socket(HOST, PORT);
    } catch (IOException ioe) {
      opened = false;
      System.err.println("Failed to connect to socket: " + ioe);
    }
    return opened;
  }

  public void sendActuatorChange(int nodeId, int actuatorId, boolean isOn) {

  }
}