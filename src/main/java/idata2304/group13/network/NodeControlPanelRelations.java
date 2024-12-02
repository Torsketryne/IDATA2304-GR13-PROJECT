package idata2304.group13.network;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manage the relationship between nodes and control panels in a network.
 *
 * this class allows to add, remove and retirve relationships nodes and control panels.
 */
public class NodeControlPanelRelations {

  private final HashMap<String, String> nodesToPanelRelations;

  /**
   * Create a new instance of the NodeControlPanelRelations class.
   *
   * This initializes an empty collection to store relationships between node and control panel
   */
  public NodeControlPanelRelations() {
    this.nodesToPanelRelations = new HashMap<>();
  }

  /**
   * Adds a relationships between a node and a control panel.
   *
   * @param nodeId The unique ID of the node.
   * @param panelId The unique ID of the control panel associated with the node.
   */
  public void addRelation(String nodeId, String panelId) {
    this.nodesToPanelRelations.put(nodeId, panelId);
  }

  /**
   * Remmoves the relationship for a specific node.
   *
   * @param nodeId The uniques ID of the node where relationship should be removed.
   */
  public void removeRelation(String nodeId) {
    this.nodesToPanelRelations.remove(nodeId);
  }

  /**
   * Retrives the control panel ID associated with a specific node.
   *
   * @param nodeId The unique ID of the code.
   * @return The ID of the control panel associated with node, if there is no realtionships exists.
   */
  public String getNodePartner(String nodeId) {
    return this.nodesToPanelRelations.get(nodeId);
  }

  /**
   * Find all nodes associated with a specific control panel.
   *
   * @param panelId The unique ID of the control panel.
   * @return A list of the nodes IDs associated with the control panel.
   */
  public List getPanelPartners(String panelId) {
    List<String> allNodeIds = new ArrayList<>(this.nodesToPanelRelations.keySet());
    List<String> associatedNodeIds = new ArrayList<>();
    for (String nodeId : allNodeIds) {
      if (nodeId.equals(getNodePartner(panelId))) {
        associatedNodeIds.add(nodeId);
      }
    }
    return associatedNodeIds;
  }
}
