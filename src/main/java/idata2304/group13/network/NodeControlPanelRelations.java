package idata2304.group13.network;

import java.util.HashMap;

public class NodeControlPanelRelations {

  private HashMap<String, String> nodesToPanelRelations;

  public void addRelation(String nodeId, String panelId) {
    this.nodesToPanelRelations.put(nodeId, panelId);
  }

  public void removeRelation(String nodeId) {
    this.nodesToPanelRelations.remove(nodeId);
  }

  public String getNodePartner(String nodeId) {
    return this.nodesToPanelRelations.get(nodeId);
  }
}
