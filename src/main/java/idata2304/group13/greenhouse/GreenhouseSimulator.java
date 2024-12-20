package idata2304.group13.greenhouse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import idata2304.group13.listeners.greenhouse.NodeStateListener;
import idata2304.group13.tools.Logger;

// Note: javadoc documentation was enhanced with the assistance of ChatGPT and Github Copilot for clarity and completeness.

/**
 * Application entrypoint - a simulator for a greenhouse.
 * <p>
 *     This class is responsible for simulating a greenhouse with multiple sensor and actuator nodes.
 *     It can start and stop the simulation, and notify listeners about the state of the nodes.
 *     </p>
 *
 * @author Girst, Torsketryne, MoldyDaniel
 */
public class GreenhouseSimulator {
  private final Map<Integer, SensorActuatorNode> nodes = new HashMap<>();
  private GreenhouseServer server;
  private final List<GreenhouseClient> clients = new LinkedList<>();
  private final List<PeriodicSwitch> periodicSwitches = new LinkedList<>();
  private final boolean fake;
  private static int count = 0;

  /**
   * Create a greenhouse simulator.
   *
   * @param fake When true, simulate a fake periodic events instead of creating
   *             socket communication
   *
   * @author Girst
   */
  public GreenhouseSimulator(boolean fake) {
    this.fake = fake;
  }

  /**
   * Initialise the greenhouse but don't start the simulation just yet.
   * <p>
   * This methods have examples of creating nodes with different parameters.
   * temperature, humidity, windows, fans, heaters
   * </p>
   *
   * @author Girst
   */
  public void initialize() {
    createNode(1, 2, 1, 0, 0);
    createNode(1, 0, 0, 2, 1);
    createNode(2, 0, 0, 0, 0);
    Logger.info("Greenhouse initialized");
  }

  /**
   * This methdos creates a new sensor/actuator node based on numbers of sensors and actuators.
   *
   * @param temperature The number of temperature sensors.
   * @param humidity    The number of humidity sensors.
   * @param windows     The number of windows.
   * @param fans        The number of fans.
   * @param heaters     The number of heaters.
   *
   * @author Girst
   */

  private void createNode(int temperature, int humidity, int windows, int fans, int heaters) {
    SensorActuatorNode node = DeviceFactory.createNode(
        temperature, humidity, windows, fans, heaters);
    nodes.put(node.getId(), node);
  }

  /**
   *
   * @param args Program arguments
   * @author Torsketryne
   */
  public static void main(String[] args) {
    GreenhouseSimulator greenhouseSimulator = new GreenhouseSimulator(false);

    greenhouseSimulator.initialize();
    greenhouseSimulator.start();
  }

  /**
   * Start a simulation of a greenhouse - all the sensor and actuator nodes inside it.
   * <p>
   * This method will initialize the communication and start all the nodes. and periodic switches.
   * </p>
   *
   * @author Girst
   */
  public void start() {
    initiateCommunication();
    for (SensorActuatorNode node : nodes.values()) {
      node.start();
    }
    for (PeriodicSwitch periodicSwitch : periodicSwitches) {
      periodicSwitch.start();
    }

    Logger.info("Simulator started");
  }

  /**
   * This method will initiate the communication with the greenhouse nodes.if fake will start fake periodic switches.
   * if real will start the real communication.
   *
   * @author Girst
   */
  private void initiateCommunication() {
    if (fake) {
      initiateFakePeriodicSwitches();
    } else {
      initiateRealCommunication();
    }
  }

  /**
   Start the real communication with the greenhouse nodes using TCP.

   @author Torsketrynet, MoldyDaniel
   */
  private void initiateRealCommunication() {
    for(SensorActuatorNode node : nodes.values()) {
      GreenhouseClient client = new GreenhouseClient(count, node);
      count++;
      client.run();
      clients.add(client);
    }
    System.out.println("Nodes size: " + nodes.size());
  }

  /**
   *  Create fake periodic switches for the greenhouse nodes.
   *
   * @author Girst
   */
  private void initiateFakePeriodicSwitches() {
    periodicSwitches.add(new PeriodicSwitch("Window DJ", nodes.get(1), 2, 20000));
    periodicSwitches.add(new PeriodicSwitch("Heater DJ", nodes.get(2), 7, 8000));
  }

  /**
   * Stop the simulation of the greenhouse - all the nodes in it.
   *
   * @author Girst
   */
  public void stop() {
    stopCommunication();
    for (SensorActuatorNode node : nodes.values()) {
      node.stop();
    }
    Logger.info("Simulator stopped");
  }

  /**
   * Stop the communication with the greenhouse nodes.
   *
   * @author Girst
   */
  private void stopCommunication() {
    if (fake) {
      for (PeriodicSwitch periodicSwitch : periodicSwitches) {
        periodicSwitch.stop();
      }
    } else {
      // TODO - here you stop the TCP/UDP communication
      server.stopServer();
    }
  }

  /**
   * Add a listener for notification of node staring and stopping.
   *
   * @param listener The listener which will receive notifications
   *
   * @author Girst
   */
  public void subscribeToLifecycleUpdates(NodeStateListener listener) {
    for (SensorActuatorNode node : nodes.values()) {
      node.addStateListener(listener);
    }
  }
}
