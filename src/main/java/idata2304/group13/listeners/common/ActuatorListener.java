package idata2304.group13.listeners.common;

import idata2304.group13.greenhouse.Actuator;

/**
 * Listener for actuator state changes.
 * This could be used both on the sensor/actuator (greenhouse) side, as wall as
 * on the control panel side.
 *
 * @author Girst
 */
public interface ActuatorListener {
  /**
   * An event that is fired every time an actuator changes state.
   *
   * @param nodeId   ID of the node on which this actuator is placed
   * @param actuator The actuator that has changed its state
   */
  void actuatorUpdated(int nodeId, Actuator actuator);
}
