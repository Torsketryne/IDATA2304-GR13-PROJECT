package idata2304.group13.run;

import idata2304.group13.controlpanel.CommunicationChannel;
import idata2304.group13.controlpanel.ControlPanelLogic;
import idata2304.group13.controlpanel.FakeCommunicationChannel;
import idata2304.group13.controlpanel.SocketCommunicationChannel;
import idata2304.group13.greenhouse.GreenhouseClient;
import idata2304.group13.greenhouse.GreenhouseServer;
import idata2304.group13.greenhouse.GreenhouseSimulator;
import idata2304.group13.gui.controlpanel.ControlPanelApplication;
import idata2304.group13.tools.Logger;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Starter class for the control panel.
 * Note: we could launch the Application class directly, but then we would have issues with the
 * debugger (JavaFX modules not found)
 */
public class ControlPanelStarter {
  private final int port;

    private GreenhouseSimulator simulator;

  public ControlPanelStarter(int port) {
    this.port = port;
  }

  /**
   * Entrypoint for the application.
   *
   * @param args Command line arguments, only the first one of them used: when it is "fake",
   *             emulate fake events, when it is either something else or not present,
   *             use real socket communication. Go to Run → Edit Configurations.
   *             Add "fake" to the Program Arguments field.
   *             Apply the changes.
   */
  public static void main(String[] args) {
    int port = validArgsCheck(args);

    ControlPanelStarter starter = new ControlPanelStarter(port);
    starter.start();
  }

  private void start() {
    ControlPanelLogic logic = new ControlPanelLogic();
    CommunicationChannel channel = initiateCommunication(logic, port);
    ControlPanelApplication.startApp(logic, channel);
    // This code is reached only after the GUI-window is closed
    Logger.info("Exiting the control panel application");
    stopCommunication();
  }

  private CommunicationChannel initiateCommunication(ControlPanelLogic logic, int port) {
    //while (true) {
      try {
        ServerSocket serverSocket = new ServerSocket(port);
      } catch (IOException ioe) {
        System.err.println("Error opening server socket: " + ioe.getMessage());
      }
      return initiateSocketCommunication(logic, port);
    //}
  }

  private CommunicationChannel initiateSocketCommunication(ControlPanelLogic logic, int port) {
    // TODO - here you initiate TCP/UDP socket communication
    // You communication class(es) may want to get reference to the logic and call necessary
    // logic methods when events happen (for example, when sensor data is received)

    GreenhouseSimulator simulator = new GreenhouseSimulator(false);
    simulator.initialize();
    simulator.start();

    CommunicationChannel realChannel = new SocketCommunicationChannel(logic, port);
    ControlPanelApplication.startApp(logic, realChannel);

    return realChannel;
  }

  private CommunicationChannel initiateFakeSpawner(ControlPanelLogic logic) {
    // Here we pretend that some events will be received with a given delay
    FakeCommunicationChannel spawner = new FakeCommunicationChannel(logic);
    logic.setCommunicationChannel(spawner);
    final int START_DELAY = 5;
    spawner.spawnNode("4;3_window", START_DELAY);
    spawner.spawnNode("1", START_DELAY + 1);
    spawner.spawnNode("1", START_DELAY + 2);
    spawner.advertiseSensorData("4;temperature=27.4 °C,temperature=26.8 °C,humidity=80 %", START_DELAY + 2);
    spawner.spawnNode("8;2_heater", START_DELAY + 3);
    spawner.advertiseActuatorState(4, 1, true, START_DELAY + 3);
    spawner.advertiseActuatorState(4, 1, false, START_DELAY + 4);
    spawner.advertiseActuatorState(4, 1, true, START_DELAY + 5);
    spawner.advertiseActuatorState(4, 2, true, START_DELAY + 5);
    spawner.advertiseActuatorState(4, 1, false, START_DELAY + 6);
    spawner.advertiseActuatorState(4, 2, false, START_DELAY + 6);
    spawner.advertiseActuatorState(4, 1, true, START_DELAY + 7);
    spawner.advertiseActuatorState(4, 2, true, START_DELAY + 8);
    spawner.advertiseSensorData("4;temperature=22.4 °C,temperature=26.0 °C,humidity=81 %", START_DELAY + 9);
    spawner.advertiseSensorData("1;humidity=80 %,humidity=82 %", START_DELAY + 10);
    spawner.advertiseRemovedNode(8, START_DELAY + 11);
    spawner.advertiseRemovedNode(8, START_DELAY + 12);
    spawner.advertiseSensorData("1;temperature=25.4 °C,temperature=27.0 °C,humidity=67 %", START_DELAY + 13);
    spawner.advertiseSensorData("4;temperature=25.4 °C,temperature=27.0 °C,humidity=82 %", START_DELAY + 14);
    spawner.advertiseSensorData("4;temperature=25.4 °C,temperature=27.0 °C,humidity=82 %", START_DELAY + 16);
    return spawner;
  }

  private static int validArgsCheck(String[] args) {
    int port = 1313;
    if (args.length == 0) {
      Logger.info("Using default port number: 1313");
    } else if (args.length == 1 && args[0].trim().matches("\\d+")) {
      port = Integer.parseInt(args[0]);
      Logger.info("Using provided port number: " + port);
    } else if (args.length > 1){
      throw new IllegalArgumentException("There cannot be more program arguments than one. Type only in port number or leave empty for default port");
    } else {
      throw new IllegalArgumentException("Program argument not understood. Please enter in either a number to be used as port number or nothing to use default port number");
    }
    return port;
  }

  private void stopCommunication() {
    // TODO - here you stop the TCP/UDP socket communication
    simulator.stopCommunication();
  }
}
