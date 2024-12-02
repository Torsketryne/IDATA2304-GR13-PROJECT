package idata2304.group13.greenhouse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import idata2304.group13.tools.Logger;

/**
 * A collection of actuators of different types.
 */
public class ActuatorCollection implements Iterable<Actuator> {
  private final Map<Integer, Actuator> actuators = new HashMap<>();

  /**
   * Print a short info about all the actuators. Usable for debugging. Does NOT print a newline!
   */
  public void debugPrint() {
    for (Actuator actuator : actuators.values()) {
      Logger.infoNoNewline(" " + actuator.getType() + "[" + actuator.getId() + "]"
          + (actuator.isOn() ? " ON" : " off"));
    }
  }

  /**
   * Add an actuator to the collection.
   *
   * @param actuator The actuator to add.
   */
  public void add(Actuator actuator) {
    actuators.put(actuator.getId(), actuator);
  }

  /**
   * Get an actuator by its ID.
   *
   * @param id ID of the actuator to look up.
   * @return The actuator or null if none found
   */
  public Actuator get(int id) {
    return actuators.get(id);
  }

  /**
   *Return an iterator of objects managed by the instance.
   *
   * @return an iterator over the objects
   */
  @Override
  public Iterator<Actuator> iterator() {
    return actuators.values().iterator();
  }

  /**
   * Get the number of actuators stored in the collection.
   *
   * @return The number of actuators in this collection
   */
  public int size() {
    return actuators.size();
  }
}
