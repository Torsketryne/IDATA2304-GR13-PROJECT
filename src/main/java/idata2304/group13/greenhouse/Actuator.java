package idata2304.group13.greenhouse;

import java.util.HashMap;
import java.util.Map;
import idata2304.group13.listeners.common.ActuatorListener;

/**
 * Represents an actuator in the greenhouse network.
 * An actuator that can change the environment in a way. The actuator will make impact on the
 * sensors attached to this same node.
 */

// Note: javadoc documentation was enhanced with the assistance of ChatGPT and Github Copilot for clarity and completeness.

public class Actuator {
  private static int nextId = 1;
  private final String type;
  private final int nodeId;
  private final int id;
  private Map<String, Double> impacts = new HashMap<>();

  private ActuatorListener listener;

  private boolean on;

  /**
   * Create an actuator. An ID will be auto-generated.
   *
   * @param type   The type of the actuator ("heater", "fan").
   * @param nodeId ID of the node to which this actuator is connected.
   */
  public Actuator(String type, int nodeId) {
    this.type = type;
    this.nodeId = nodeId;
    this.on = false;
    this.id = generateUniqueId();
  }

  /**
   * Create an actuator with a specified ID.
   *
   * @param id     The desired ID of the node.
   * @param type   The type of the actuator.
   * @param nodeId ID of the node to which this actuator is connected.
   */
  public Actuator(int id, String type, int nodeId) {
    this.type = type;
    this.nodeId = nodeId;
    this.on = false;
    this.id = id;
  }

  /**
   * Method that generates a unique ID for each actuator.
   *
   * @return A unique ID for each actuator
   */
  private static int generateUniqueId() {
    return nextId++;
  }

  /**
   * Set the listener which will be notified when actuator state changes.
   *
   * @param listener The listener of state change events
   */
  public void setListener(ActuatorListener listener) {
    this.listener = listener;
  }

  /**
   * Register the impact of this actuator when active.
   *
   * @param sensorType     Which type of sensor readings will be impacted. Example: "temperature"
   * @param diffWhenActive What will be the introduced difference in the sensor reading when
   *                       the actuator is active. For example, if this value is 2.0 and the
   *                       sensorType is "temperature", this means that "activating this actuator
   *                       will increase the readings of temperature sensors attached to the
   *                       same node by +2 degrees".
   */
  public void setImpact(String sensorType, double diffWhenActive) {
    impacts.put(sensorType, diffWhenActive);
  }

  /**
   * Gets the type of the actuator.
   *
   * @return The type of the actuator.
   */
  public String getType() {
    return type;
  }

  /**
   * Create a clone of this actuator.
   *
   * @return A clone of this actuator, where all the fields are the same
   */
  public Actuator createClone() {
    Actuator a = new Actuator(type, nodeId);
    // Note - we pass a reference to the same map! This should not be problem, as long as we
    // don't modify the impacts AFTER creating the template
    a.impacts = impacts;
    return a;
  }

  /**
   * Toggle the actuator - if it was off, not it will be ON, and vice versa.
   */
  public void toggle() {
    this.on = !this.on;
    notifyChanges();
  }

  /**
   * The listener will be notified about the changes in the actuator state.
   */
  private void notifyChanges() {
    if (listener != null) {
      listener.actuatorUpdated(this.nodeId, this);
    }
  }

  /**
   * Check whether the actuator is active (ON), or inactive (OFF).
   *
   * @return True when the actuator is ON, false if it is OFF
   */
  public boolean isOn() {
    return on;
  }

  /**
   * Apply impact of this actuator to all sensors of one specific sensor node.
   *
   * @param node The sensor node to be affected by this actuator.
   */
  public void applyImpact(SensorActuatorNode node) {
    for (Map.Entry<String, Double> impactEntry : impacts.entrySet()) {
      String sensorType = impactEntry.getKey();
      double impact = impactEntry.getValue();
      if (!on) {
        impact = -impact;
      }
      node.applyActuatorImpact(sensorType, impact);
    }
  }

  /**
   * Returns a string representation of the actuators current state.
   *
   * @return A string will talk about the type of actuator and whether it is on or off.
   */
  @Override
  public String toString() {
    return "Actuator{"
        + "type='" + type + '\''
        + ", on=" + on
        + '}';
  }

  /**
   * Turn on the actuator.
   */
  public void turnOn() {
    if (!on) {
      on = true;
      notifyChanges();
    }
  }

  /**
   * Turn off the actuator.
   */
  public void turnOff() {
    if (on) {
      on = false;
      notifyChanges();
    }
  }

  /**
   * Get the ID of the actuator.
   *
   * @return An ID which is guaranteed to be unique at a node level, not necessarily unique at
   *     the whole greenhouse-network level.
   */
  public int getId() {
    return id;
    /**
     * Gets the ID of the of the node, when an actuator is connected to.
     *
     * @return The ID will be returned.
     */
  }
  public int getNodeId() {
    return nodeId;
  }

  /**
   * Set the actuator to the desired state.
   *
   * @param on Turn on when true, turn off when false
   */
  public void set(boolean on) {
    if (on) {
      turnOn();
    } else {
      turnOff();
    }
  }
}
