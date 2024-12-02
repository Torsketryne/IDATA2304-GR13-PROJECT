package idata2304.group13.greenhouse;

/**
 * A sensor which can sense the environment in a specific way.
 */
public class Sensor {
  private final SensorReading reading;
  private final double min;
  private final double max;
  private static int nextId = 1;
  private final int id;

  /**
   * Create a sensor.
   *
   * @param type    The type of the sensor. Examples: "temperature", "humidity"
   * @param min     Minimum allowed value
   * @param max     Maximum allowed value
   * @param current The current (starting) value of the sensor
   * @param unit    The measurement unit. Examples: "%", "C", "lux"
   */
  public Sensor(String type, double min, double max, double current, String unit) {
    this.reading = new SensorReading(type, current, unit);
    this.min = min;
    this.max = max;
    ensureValueBoundsAndPrecision(current);
    this.id = generateUniqueId();
  }

  /**
   * Get the ID of the actuator.
   *
   * @return An ID which is guaranteed to be unique at a node level, not necessarily unique at
   *     the whole greenhouse-network level.
   */
  public int getId() {
    return id;
  }

  public String getType() {
    return reading.getType();
  }

  /**
   * Get the current sensor reading.
   *
   * @return The current sensor reading (value)
   */
  public SensorReading getReading() {
    return reading;
  }

  /**
   * Create a clone of this sensor.
   *
   * @return A clone of this sensor, where all the fields are the same
   */
  public Sensor createClone() {
    return new Sensor(this.reading.getType(), this.min, this.max,
        this.reading.getValue(), this.reading.getUnit());
  }

  /**
   * Add a random noise to the sensors to simulate realistic values.
   */
  public void addRandomNoise() {
    double newValue = this.reading.getValue() + generateRealisticNoise();
    ensureValueBoundsAndPrecision(newValue);
  }

  /**
   * Ensure the new value is within the allowed bounds and has the correct precision.
   *
   * @param newValue The new value to be checked and adjusted if necessary
   */
  private void ensureValueBoundsAndPrecision(double newValue) {
    newValue = roundToTwoDecimals(newValue);
    if (newValue < min) {
      newValue = min;
    } else if (newValue > max) {
      newValue = max;
    }
    reading.setValue(newValue);
  }

  /**
   * Round a value to two decimal places.
   *
   * @param value The value to be rounded
   * @return The rounded value
   */
  private double roundToTwoDecimals(double value) {
    return Math.round(value * 100.0) / 100.0;
  }

  /**
   * Generate a realistic noise value to simulate sensor reading fluctuations.
   *
   * @return A noice value in the range [-1%..+1%] of the sensor's range
   */
  private double generateRealisticNoise() {
    final double wholeRange = max - min;
    final double onePercentOfRange = wholeRange / 100.0;
    final double zeroToTwoPercent = Math.random() * onePercentOfRange * 2;
    return zeroToTwoPercent - onePercentOfRange; // In the range [-1%..+1%]
  }

  /**
   * Apply an external impact (from an actuator) to the current value of the sensor.
   *
   * @param impact The impact to apply - the delta for the value
   */
  public void applyImpact(double impact) {
    double newValue = this.reading.getValue() + impact;
    ensureValueBoundsAndPrecision(newValue);
  }

  /**
   * Return a string representation of the sensor.
   *
   * @return A string representation of the sensor reading
   */
  @Override
  public String toString() {
    return reading.toString();
  }

  /**
   * Method that generates a unique ID for each actuator.
   *
   * @return A unique ID for each actuator
   */
  private static int generateUniqueId() {
    return nextId++;
  }
}