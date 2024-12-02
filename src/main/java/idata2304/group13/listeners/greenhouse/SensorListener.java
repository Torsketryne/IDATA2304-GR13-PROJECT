package idata2304.group13.listeners.greenhouse;

import java.util.List;
import idata2304.group13.greenhouse.Sensor;

/**
 * Listener for sensor update events.
 * This will (probably) be usable only on the sensor/actuator node (greenhouse) side, where the
 * real sensor objects are available. The control panel side has only sensor reading values
 * available, not the sensors themselves.
 *
 * @author Girst
 */
public interface SensorListener {
  /**
   * An event that is fired every time sensor values are updated.
   *
   * @param sensors A list of sensors having new values (readings)
   */
  void sensorsUpdated(List<Sensor> sensors);
}
