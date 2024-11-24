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
   * @param node The node which will be handled in this window
   */
  public NodeGuiWindow(SensorActuatorNode node) {
    this.node = node;
    Scene scene = new Scene(createContent(), WINDOW_WIDTH, WINDOW_HEIGHT);
    setScene(scene);
    setTitle("Node " + node.getId());
    initializeListeners(node);
    setPositionAndSize();
  }

  private void setPositionAndSize() {
    setX((node.getId() - 1) * HORIZONTAL_OFFSET);
    setY(node.getId() * VERTICAL_OFFSET);
    setMinWidth(WINDOW_HEIGHT);
    setMinHeight(WINDOW_WIDTH);
  }


  private void initializeListeners(SensorActuatorNode node) {
    setOnCloseRequest(windowEvent -> shutDownNode());
    node.addSensorListener(this);
    node.addActuatorListener(this);
  }

  private void shutDownNode() {
    node.stop();
  }

  private Parent createContent() {
    actuatorPane = new ActuatorPane(node.getActuators());
    sensorPane = new SensorPane(node.getSensors());
    return new VBox(sensorPane, actuatorPane);
  }


  @Override
  public void sensorsUpdated(List<Sensor> sensors) {
    if (sensorPane != null) {
      sensorPane.update(sensors);
    }
  }

  @Override
  public void actuatorUpdated(int nodeId, Actuator actuator) {
    if (actuatorPane != null) {
      actuatorPane.update(actuator);
    }
  }
}