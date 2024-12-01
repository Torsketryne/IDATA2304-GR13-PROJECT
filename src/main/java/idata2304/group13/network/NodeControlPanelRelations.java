package idata2304.group13.network;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

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

  public List getPanelPartners(String panelId) {
    List<String> allNodeIds = new ArrayList<>(this.nodesToPanelRelations.keySet());
    List<String> assosiatedNodeIds = new ArrayList();
    for (String nodeId : allNodeIds) {
      if (nodeId.equals(getNodePartner(panelId))) {
        assosiatedNodeIds.add(nodeId);
      }
    }
    return assosiatedNodeIds;
  }
}
