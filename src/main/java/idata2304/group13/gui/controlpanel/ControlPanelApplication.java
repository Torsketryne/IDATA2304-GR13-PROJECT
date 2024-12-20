package idata2304.group13.gui.controlpanel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import idata2304.group13.controlpanel.CommunicationChannel;
import idata2304.group13.controlpanel.ControlPanelLogic;
import idata2304.group13.controlpanel.SensorActuatorNodeInfo;
import idata2304.group13.greenhouse.Actuator;
import idata2304.group13.greenhouse.SensorReading;
import idata2304.group13.gui.common.ActuatorPane;
import idata2304.group13.gui.common.SensorPane;
import idata2304.group13.listeners.common.CommunicationChannelListener;
import idata2304.group13.listeners.controlpanel.GreenhouseEventListener;
import idata2304.group13.tools.Logger;

/**
 * Run a control panel with a graphical user interface (GUI), with JavaFX.
 *
 * @author Girst
 */
public class ControlPanelApplication extends Application implements GreenhouseEventListener,
    CommunicationChannelListener {
  private static ControlPanelLogic logic;
  private static final int WIDTH = 500;
  private static final int HEIGHT = 400;
  private static CommunicationChannel channel;

  private TabPane nodeTabPane;
  private Scene mainScene;
  private final Map<Integer, SensorPane> sensorPanes = new HashMap<>();
  private final Map<Integer, ActuatorPane> actuatorPanes = new HashMap<>();
  private final Map<Integer, SensorActuatorNodeInfo> nodeInfos = new HashMap<>();
  private final Map<Integer, Tab> nodeTabs = new HashMap<>();

  /**
   * Application entrypoint for the GUI of a control panel.
   * Note - this is a workaround to avoid problems with JavaFX not finding the modules!
   * We need to use another wrapper-class for the debugger to work.
   *
   * @param logic   The logic of the control panel node
   * @param channel Communication channel for sending control commands and receiving events
   */
  public static void startApp(ControlPanelLogic logic, CommunicationChannel channel) {
    if (logic == null) {
      throw new IllegalArgumentException("Control panel logic can't be null");
    }
    ControlPanelApplication.logic = logic;
    ControlPanelApplication.channel = channel;
    Logger.info("Running control panel GUI...");
    launch();
  }

  /**
   * Sets ups the main application window and initializes listeners.
   *
   * @param stage The primary stage window of the application.
   */
  @Override
  public void start(Stage stage) {
    if (channel == null) {
      throw new IllegalStateException(
          "No communication channel. See the README on how to use fake event spawner!");
    }

    stage.setMinWidth(WIDTH);
    stage.setMinHeight(HEIGHT);
    stage.setTitle("Control panel");
    mainScene = new Scene(createEmptyContent(), WIDTH, HEIGHT);
    stage.setScene(mainScene);
    stage.show();
    logic.addListener(this);
    logic.setCommunicationChannelListener(this);
    if (!channel.open()) {
      logic.onCommunicationChannelClosed();
    }
  }

  /**
   * Create a label to display when there are no nodes to show.
   *
   * @return A message for the user.
   */
  private static Label createEmptyContent() {
    Label l = new Label("Waiting for node data...");
    l.setAlignment(Pos.CENTER);
    return l;
  }

  /**
   * Will be a call when there is new node added to the system.
   *
   * @param nodeInfo Information about the added node.
   */
  @Override
  public void onNodeAdded(SensorActuatorNodeInfo nodeInfo) {
    Platform.runLater(() -> addNodeTab(nodeInfo));
  }

  /**
   * Will be a call when the node is removed from the system.
   *
   * @param nodeId The node ID to be removed.
   */
  @Override
  public void onNodeRemoved(int nodeId) {
    Tab nodeTab = nodeTabs.get(nodeId);
    if (nodeTab != null) {
      Platform.runLater(() -> {
        removeNodeTab(nodeId, nodeTab);
        forgetNodeInfo(nodeId);
        if (nodeInfos.isEmpty()) {
          removeNodeTabPane();
        }
      });
      Logger.info("Node " + nodeId + " removed");
    } else {
      Logger.error("Can't remove node " + nodeId + ", there is no Tab for it");
    }
  }

  /**
   * Remove the tab pane from the main scene.
   */
  private void removeNodeTabPane() {
    mainScene.setRoot(createEmptyContent());
    nodeTabPane = null;
  }

  /**
   * Updates the GUI when sensor data is received.
   *
   * @param nodeId  Node ID that sent the data.
   * @param sensors List of sensor readings.
   */
  @Override
  public void onSensorData(int nodeId, List<SensorReading> sensors) {
    Logger.info("Sensor data from node " + nodeId);
    SensorPane sensorPane = sensorPanes.get(nodeId);
    if (sensorPane != null) {
      sensorPane.update(sensors);
    } else {
      Logger.error("No sensor section for node " + nodeId);
    }
  }

  /**
   * Updates the GUI when an actuator's state changes.
   *
   * @param nodeId Node ID with the updated actuator.
   * @param actuatorId ID of the updated actuator.
   * @param isOn  The new state of the actuator(true is ON, false is OFF).
   */
  @Override
  public void onActuatorStateChanged(int nodeId, int actuatorId, boolean isOn) {
    String state = isOn ? "ON" : "off";
    Logger.info("actuator[" + actuatorId + "] on node " + nodeId + " is " + state);
    ActuatorPane actuatorPane = actuatorPanes.get(nodeId);
    if (actuatorPane != null) {
      Actuator actuator = getStoredActuator(nodeId, actuatorId);
      if (actuator != null) {
        if (isOn) {
          actuator.turnOn();
        } else {
          actuator.turnOff();
        }
        actuatorPane.update(actuator);
      } else {
        Logger.error(" actuator not found");
      }
    } else {
      Logger.error("No actuator section for node " + nodeId);
    }
  }

  /**
   * Retrives an actuator for a specific node by its ID.
   *
   * @param nodeId Node ID that contains the actuator.
   * @param actuatorId Actuator ID to retrieve.
   * @return if actuator found, or if it doesn't exists.
   */
  private Actuator getStoredActuator(int nodeId, int actuatorId) {
    Actuator actuator = null;
    SensorActuatorNodeInfo nodeInfo = nodeInfos.get(nodeId);
    if (nodeInfo != null) {
      actuator = nodeInfo.getActuator(actuatorId);
    }
    return actuator;
  }

  /**
   * Removes all stored info related to specific node.
   *
   * @param nodeId Node ID to forget.
   */
  private void forgetNodeInfo(int nodeId) {
    sensorPanes.remove(nodeId);
    actuatorPanes.remove(nodeId);
    nodeInfos.remove(nodeId);
  }

  /**
   *Removes a specific nodes tab from the tab pane.
   *
   * @param nodeId Node ID to remove.
   * @param nodeTab the tab with the related node.
   */
  private void removeNodeTab(int nodeId, Tab nodeTab) {
    nodeTab.getTabPane().getTabs().remove(nodeTab);
    nodeTabs.remove(nodeId);
  }

  /**
   * Add a new tab for a node and initializes its panes.
   *
   * @param nodeInfo Info about the node to add.
   */
  private void addNodeTab(SensorActuatorNodeInfo nodeInfo) {
    if (nodeTabPane == null) {
      nodeTabPane = new TabPane();
      mainScene.setRoot(nodeTabPane);
    }
    Tab nodeTab = nodeTabs.get(nodeInfo.getId());
    if (nodeTab == null) {
      nodeInfos.put(nodeInfo.getId(), nodeInfo);
      nodeTabPane.getTabs().add(createNodeTab(nodeInfo));
    } else {
      Logger.info("Duplicate node spawned, ignore it");
    }
  }

  /**
   * Create a new tab for a node with sensor and actuator panes.
   *
   * @param nodeInfo The information of the node to display in the tab.
   * @return The created tab.
   */
  private Tab createNodeTab(SensorActuatorNodeInfo nodeInfo) {
    Tab tab = new Tab("Node " + nodeInfo.getId());
    SensorPane sensorPane = createEmptySensorPane();
    sensorPanes.put(nodeInfo.getId(), sensorPane);
    ActuatorPane actuatorPane = new ActuatorPane(nodeInfo.getActuators());
    actuatorPanes.put(nodeInfo.getId(), actuatorPane);
    tab.setContent(new VBox(sensorPane, actuatorPane));
    nodeTabs.put(nodeInfo.getId(), tab);
    return tab;
  }

  /**
   * Creates an empty sensor pane.
   *
   * @return A pane with no data.
   */
  private static SensorPane createEmptySensorPane() {
    return new SensorPane();
  }

  /**
   * Handles the closure of the communication channel.
   */
  @Override
  public void onCommunicationChannelClosed() {
    Logger.info("Communication closed, closing the GUI");
    Platform.runLater(Platform::exit);
  }
}
