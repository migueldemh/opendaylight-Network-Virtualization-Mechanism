virtualNetworkpackage org.opendaylight.dtu.impl.rev141210;

import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;

import java.util.HashMap;

/**
 * This class define a host
 */
public class Host {
    //Attributes
    private String virtualNetwork;
    private String ip;
    private NodeConnectorId nodeConnectorId;
    private String switchName;
    public HashMap<NodeConnectorId, Integer> hashMap;

    public HashMap<NodeConnectorId, Integer> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<NodeConnectorId, Integer> hashMap) {
        this.hashMap = hashMap;
    }

    public Host(String virtualNetwork, String ip, NodeConnectorId nodeConnectorId,String switchName) {
        this.virtualNetwork = virtualNetwork;
        this.ip = ip;
        this.nodeConnectorId = nodeConnectorId;
        this.switchName=switchName;
        this.hashMap = new HashMap<NodeConnectorId, Integer>();
    }
    public Host() {

    }
    public String getSwitchName() {
        return switchName;
    }

    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    public String getId() {
        return virtualNetwork;

    }

    public void setId(String virtualNetwork) {
        this.virtualNetwork = virtualNetwork;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public NodeConnectorId getNodeConnectorId() {
        return nodeConnectorId;
    }

    public void setNodeConnectorId(NodeConnectorId nodeConnectorId) {
        this.nodeConnectorId = nodeConnectorId;
    }
}
