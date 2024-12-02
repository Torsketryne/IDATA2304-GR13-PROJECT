package idata2304.group13.greenhouse;

public class ActuatorInfo {

  private int id;
  private String type;
  private boolean state;

  public ActuatorInfo(int id, String type, boolean state) {
    this.id = id;
    this.type = type;
    this.state = state;
  }

  public int getId() {
    return this.id;
  }

  public String getType() {
    return this.type;
  }

  public boolean getState() {
    return this.state;
  }
}

