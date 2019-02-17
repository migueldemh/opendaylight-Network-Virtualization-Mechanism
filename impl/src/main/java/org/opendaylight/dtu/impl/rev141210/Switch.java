package org.opendaylight.dtu.impl.rev141210;

import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;

import java.util.ArrayList;
import java.util.List;

/**
 * This class define a switch
 */
public class Switch {
    //Attributes
    private String nodeId;
    private List<NodeConnectorId> links=new ArrayList<NodeConnectorId>();
    private List<Host> Hosts=new ArrayList<Host>();

    public Switch(){}

    public Switch(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public List<NodeConnectorId> getLinks() {
        return links;
    }

    public void setLinks(List<NodeConnectorId> links) {
        this.links = links;
    }

    public void setAlink(NodeConnectorId nodeConnectorId){
        links.add(nodeConnectorId);

    }
    public void setAHost(Host host){
        Hosts.add(host);

    }
    public List<Host> getHosts() {
        return Hosts;
    }

    public void setHosts(List<Host> hosts) {
        Hosts = hosts;
    }
}
