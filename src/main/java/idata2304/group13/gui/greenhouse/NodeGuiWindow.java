package idata2304.group13.gui.greenhouse;

import java.util.List;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import idata2304.group13.greenhouse.Actuator;
import idata2304.group13.greenhouse.Sensor;
import idata2304.group13.greenhouse.SensorActuatorNode;
import idata2304.group13.gui.common.ActuatorPane;
import idata2304.group13.gui.common.SensorPane;
import idata2304.group13.listeners.common.ActuatorListener;
import idata2304.group13.listeners.greenhouse.SensorListener;

/**
 * Window with GUI for overview and control of one specific sensor/actuator node.
 *
 * This class display the information about sensors and actuator and allows interaction
 * with them through graphical interface.
 */
public class NodeGuiWindow extends Stage implements SensorListener, ActuatorListener {
  private static final double VERTICAL_OFFSET = 50;
  private static final double HORIZONTAL_OFFSET = 150;
  private static final double WINDOW_WIDTH = 300;
  private static final double WINDOW_HEIGHT = 300;
  private final SensorActuatorNode node;

  private ActuatorPane actuatorPane;
  private SensorPane sensorPane;

  /**
   * Create a GUI window for a specific node.
   *
   * @param node The actuator and sensor node to display in the window.
   */
  public NodeGuiWindow(SensorActuatorNode node) {
    this.node = node;
    Scene scene = new Scene(createContent(), WINDOW_WIDTH, WINDOW_HEIGHT);
    setScene(scene);
    setTitle("Node " + node.getId());
    initializeListeners(node);
    setPositionAndSize();
  }

  /**
   * Sets the position and size of the window.
   */
  private void setPositionAndSize() {
    setX((node.getId() - 1) * HORIZONTAL_OFFSET);
    setY(node.getId() * VERTICAL_OFFSET);
    setMinWidth(WINDOW_HEIGHT);
    setMinHeight(WINDOW_WIDTH);
  }


  /**
   * Initializes listeners for the node.
   *
   * @param node The node to add listeners for.
   */
  private void initializeListeners(SensorActuatorNode node) {
    setOnCloseRequest(windowEvent -> shutDownNode());
    node.addSensorListener(this);
    node.addActuatorListener(this);
  }

  /**
   * Shuts down the node when the window is closed.
   */
  private void shutDownNode() {
    node.stop();
  }

  /**
   * Create the content for the window.
   *
   * @return the graphical elements for the window.
   */
  private Parent createContent() {
    actuatorPane = new ActuatorPane(node.getActuators());
    sensorPane = new SensorPane(node.getSensors());
    return new VBox(sensorPane, actuatorPane);
  }


  /**
   * Updates the sensor panel, when the sensor node are updated.
   *
   * @param sensors The updated sensors.
   */
  @Override
  public void sensorsUpdated(List<Sensor> sensors) {
    if (sensorPane != null) {
      sensorPane.update(sensors);
    }
  }

  /**
   * Updates the actuator panel, when an actuator is updated.
   *
   * @param nodeId   Node ID that has updated actuator.
   * @param actuator The updated actuator.
   */
  @Override
  public void actuatorUpdated(int nodeId, Actuator actuator) {
    if (actuatorPane != null) {
      actuatorPane.update(actuator);
    }
  }
}
