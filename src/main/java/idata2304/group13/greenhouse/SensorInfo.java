package idata2304.group13.greenhouse;

/**
 * @author Torsketryne
 */
public class SensorInfo {

  private String type;
  private int value;
  private String unit;

  public SensorInfo(String type, int value, String unit) {
    this.type = type;
    this.value = value;
    this.unit = unit;
  }

  public String getType() {
    return this.type;
  }

  public int getValue() {
    return this.value;
  }

  public String getUnit() {
    return this.unit;
  }
}
