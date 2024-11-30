package idata2304.group13.gui.network;

import idata2304.group13.controlpanel.SensorActuatorNodeInfo;
import idata2304.group13.greenhouse.SensorActuatorNode;
import idata2304.group13.greenhouse.SensorReading;
import idata2304.group13.gui.greenhouse.MainGreenhouseGuiWindow;
import idata2304.group13.listeners.common.CommunicationChannelListener;
import idata2304.group13.listeners.controlpanel.GreenhouseEventListener;
import idata2304.group13.listeners.greenhouse.NodeStateListener;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;

public class NetworkApplication extends Application implements GreenhouseEventListener,
    CommunicationChannelListener, NodeStateListener {

  private Stage mainStage;

  @Override
  public void start(Stage mainStage) {
    this.mainStage = mainStage;
    mainStage.setScene(new MainGreenhouseGuiWindow());
    mainStage.setMinWidth(MainGreenhouseGuiWindow.WIDTH);
    mainStage.setMinHeight(MainGreenhouseGuiWindow.HEIGHT);
    mainStage.setTitle("Greenhouse simulator");
    mainStage.show();
  }
  public void onNodeReady(SensorActuatorNode node) {

  }

  public void onNodeStopped(SensorActuatorNode node) {

  }

  public void onNodeAdded(SensorActuatorNodeInfo nodeInfo) {

  }

  public void onNodeRemoved(int nodeId) {

  }

  public void onSensorData(int nodeId, List<SensorReading> sensors) {

  }

  public void onActuatorStateChanged(int nodeId, int actuatorId, boolean isOn) {

  }

  public void onCommunicationChannelClosed() {

  }
}
