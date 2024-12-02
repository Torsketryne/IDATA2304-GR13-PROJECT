package idata2304.group13.gui.greenhouse;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import idata2304.group13.greenhouse.GreenhouseSimulator;
import idata2304.group13.greenhouse.SensorActuatorNode;
import idata2304.group13.listeners.greenhouse.NodeStateListener;
import idata2304.group13.tools.Logger;

/**
 * Run a greenhouse simulation with a graphical user interface (GUI), with JavaFX.
 *
 * Create a user interface for monitoring and controlling a greenhouse simulation.
 */
public class GreenhouseApplication extends Application implements NodeStateListener {
  private static GreenhouseSimulator simulator;
  private final Map<SensorActuatorNode, NodeGuiWindow> nodeWindows = new HashMap<>();
  private Stage mainStage;

  /**
   *Starts the JavaFX application.
   *
   * Sets up the mai, window for the app, initializes the simulator, and listens for events form the nodes.
   *
   * @param mainStage The primary stage window of the JavaFX application.
   */
  @Override
  public void start(Stage mainStage) {
    this.mainStage = mainStage;
    mainStage.setScene(new MainGreenhouseGuiWindow());
    mainStage.setMinWidth(MainGreenhouseGuiWindow.WIDTH);
    mainStage.setMinHeight(MainGreenhouseGuiWindow.HEIGHT);
    mainStage.setTitle("Greenhouse simulator");
    mainStage.show();
    Logger.info("GUI subscribes to lifecycle events");
    simulator.initialize();
    simulator.subscribeToLifecycleUpdates(this);
    mainStage.setOnCloseRequest(event -> closeApplication());
    simulator.start();
  }

  /**
   * Closes the application when the user exits.
   *
   * Stops the simulator and tries tO shut down the app.
   */
  private void closeApplication() {
    Logger.info("Closing Greenhouse application...");
    simulator.stop();
    try {
      stop();
    } catch (Exception e) {
      Logger.error("Could not stop the application: " + e.getMessage());
    }
  }

  /**
   * Start the GUI Application.
   *
   * @param fake When true, emulate fake events instead of opening real sockets
   */
  public static void startApp(boolean fake) {
    Logger.info("Running greenhouse simulator with JavaFX GUI...");
    simulator = new GreenhouseSimulator(fake);
    launch();
  }

  /**
   *Node is ready to be displayed.
   *
   * @param node the node which is ready now.
   */
  @Override
  public void onNodeReady(SensorActuatorNode node) {
    Logger.info("Starting window for node " + node.getId());
    NodeGuiWindow window = new NodeGuiWindow(node);
    nodeWindows.put(node, window);
    window.show();
  }

  /**
   * Node is stopped and no longer active.
   *
   * @param node The node which is stopped.
   */
  @Override
  public void onNodeStopped(SensorActuatorNode node) {
    NodeGuiWindow window = nodeWindows.remove(node);
    if (window != null) {
      Platform.runLater(window::close);
      if (nodeWindows.isEmpty()) {
        Platform.runLater(mainStage::close);
      }
    }
  }
}
