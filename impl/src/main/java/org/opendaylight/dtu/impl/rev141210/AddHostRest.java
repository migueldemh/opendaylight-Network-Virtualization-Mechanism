package org.opendaylight.dtu.impl.rev141210;

import java.util.List;
import java.util.concurrent.Future;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.AddHostVirtualNetworkOutputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.dtu.rev150105.DtuService;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.Link;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.sdnhub.odl.tutorial.utils.inventory.InventoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.Node;
//This class install the label switch paths for host.
public class AddHostRest implements DtuService {
  private static final Logger LOG = LoggerFactory.getLogger(AddHostRest.class);

    @Override
    /**
    * This method treat the data received via restconf. Add a new host to a Virtual network.
    *@param input contains the IPaddress of the host and the virtual Network ID
    *@return AddHostVirtualNetworkOutput
    */
    public Future<RpcResult<AddHostVirtualNetworkOutput>> addHostVirtualNetwork(AddHostVirtualNetworkInput input) {
        LOG.info("///////////////////////////////////////////");
        LOG.info("/////////////RESTCONF//////////////////////");
        LOG.info("///////////////////////////////////////////");
        LOG.info("ADD Host: "+input.getIP()+", to  virtual Net.: "+input.getVirtualNetwork());
        AddHostVirtualNetworkOutputBuilder addHostOutputBuilder=new AddHostVirtualNetworkOutputBuilder();
        addHostOutputBuilder.setTunnels("Done");
        NetworkGraph npath=new NetworkGraph();
        npath.addLinks(DtuMain.links);
        Host hostRest=getHost(input.getIP().toString());
        hostRest.setVlanId(input.getVirtualNetwork());
        Switch switchHostRest=getSwitch(input.getIP());
        List<Host> h = DtuUtils.hostsSameVlanTopology(input.getVirtualNetwork());
        //Install the flows in one direcction. From the new host to the rest of the hosts in the Virtual network.
        h.remove(hostRest);
        for (int z = 0; z < h.size(); z++) {
            LOG.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            LOG.info("Installing Flows between " + hostRest.getSwitchName() + " " + h.get(z).getSwitchName());
            List<Link> path = npath.getPath(hostRest.getSwitchName(), h.get(z).getSwitchName());
            List<NodeConnectorId> order = DtuMain.putThemInOrder(hostRest.getSwitchName(), path);
            String vlanId = DtuMain.giveVlanIdTags(hostRest.getNodeConnectorId(), h.get(z).getNodeConnectorId());
            LOG.info("Tunnel Tag:   " + vlanId);
            LOG.info("PushTag:" + switchHostRest.getNodeId() + ",In: " + hostRest.getNodeConnectorId().getValue() + ",IPdst:" + h.get(z).getIp() + ",Tag: " + vlanId + ",Out:" + order.get(0).getValue());
            FlowDtuUtils.pushtagMPLS(new NodeId(switchHostRest.getNodeId()), hostRest.getNodeConnectorId(), h.get(z).getIp(), vlanId, order.get(0));
            FlowDtuUtils.createFlowsARPSwitchToController(new NodeId(switchHostRest.getNodeId()), hostRest.getNodeConnectorId(), h.get(z).getIp());
            int y = 1;
            int loop = order.size() - 1;
            for (y = 1; y < loop; y++) {
                LOG.info("HopFlow:" + InventoryUtils.getNodeId(order.get(y)).getValue() + ", In: " + order.get(y).getValue() + ", Tag: " + vlanId + ", Out:" + order.get(y + 1).getValue());
                FlowDtuUtils.HopFlowMPLS(InventoryUtils.getNodeId(order.get(y)), order.get(y), vlanId, order.get(y + 1));
                y = y + 1;
            }
            LOG.info("StripTag:" + InventoryUtils.getNodeId(order.get(y)).getValue() + ", In:" + order.get(y).getValue() + ", Tag: " + vlanId + ", Out:" + h.get(z).getNodeConnectorId().getValue());
            FlowDtuUtils.stripTagMPLS(InventoryUtils.getNodeId(order.get(y)), order.get(y), vlanId, h.get(z).getNodeConnectorId());
        }

        List<Host> hostlopp = DtuUtils.hostsSameVlanTopology(input.getVirtualNetwork());
        hostlopp.remove(hostRest);
        //Install the flows in one direcction. From the hosts in the Virtual network to the new host.
        for (int z = 0; z < h.size(); z++) {
            LOG.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            LOG.info("Installing Flows between " + hostlopp.get(z).getSwitchName() + " " + hostRest.getSwitchName());
            List<Link> path = npath.getPath(hostlopp.get(z).getSwitchName(), hostRest.getSwitchName());
            List<NodeConnectorId> order = DtuMain.putThemInOrder(hostlopp.get(z).getSwitchName(), path);
            String vlanId = DtuMain.giveVlanIdTags(hostlopp.get(z).getNodeConnectorId(), hostRest.getNodeConnectorId());
            LOG.info("Tunnel Tag:   " + vlanId);
            LOG.info("PushTag:" + hostlopp.get(z).getSwitchName() + ",In: " + hostlopp.get(z).getNodeConnectorId().getValue() + ",IPdst:" + hostRest.getIp() + ",Tag: " + vlanId + ",Out:" + order.get(0).getValue());
            FlowDtuUtils.pushtagMPLS(new NodeId(hostlopp.get(z).getSwitchName()), hostlopp.get(z).getNodeConnectorId(),hostRest.getIp(), vlanId, order.get(0));
            FlowDtuUtils.createFlowsARPSwitchToController(new NodeId(hostlopp.get(z).getSwitchName()), hostlopp.get(z).getNodeConnectorId(), hostRest.getIp());
            int y = 1;
            int loop = order.size() - 1;
            for (y = 1; y < loop; y++) {
                LOG.info("HopFlow:" + InventoryUtils.getNodeId(order.get(y)).getValue() + ", In: " + order.get(y).getValue() + ", Tag: " + vlanId + ", Out:" + order.get(y + 1).getValue());
                FlowDtuUtils.HopFlowMPLS(InventoryUtils.getNodeId(order.get(y)), order.get(y), vlanId, order.get(y + 1));
                y = y + 1;
            }
            LOG.info("StripTag:" + InventoryUtils.getNodeId(order.get(y)).getValue() + ", In:" + order.get(y).getValue() + ", Tag: " + vlanId + ", Out:" + h.get(z).getNodeConnectorId().getValue());
            FlowDtuUtils.stripTagMPLS(InventoryUtils.getNodeId(order.get(y)), order.get(y), vlanId, h.get(z).getNodeConnectorId());
        }
        LOG.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        printAllSwitchesHostsLinks();
        return RpcResultBuilder.success(addHostOutputBuilder.build()).buildFuture();
    }

    /**
    * Return the Host object
    *@param ip
    *@return Host
    */
    public Host getHost(String ip){
        Host n=new Host();
        for(int i=0;i<DtuMain.switches.size();i++){
            for(int j=0;j<DtuMain.switches.get(i).getHosts().size();j++){
                if(DtuMain.switches.get(i).getHosts().get(j).getIp().equals(ip)) {//busco el nodo origen
                    n=DtuMain.switches.get(i).getHosts().get(j);
                }
            }
        }
        return n;

    }

    /**
    * Return the Switch object
    *@param ip
    *@return Host
    */
    public Switch getSwitch(String ip){
        Switch n=new Switch();
        for(int i=0;i<DtuMain.switches.size();i++){
            for(int j=0;j<DtuMain.switches.get(i).getHosts().size();j++){
                if(DtuMain.switches.get(i).getHosts().get(j).getIp().equals(ip)) {//busco el nodo origen
                    n=DtuMain.switches.get(i);
                }
            }
        }
        return n;

    }

    /**
    * Return the IP address object
    *@param node switch port
    *@return Host
    */
    public String getIp(NodeConnectorId node){
        String n=new String();
        for(int i=0;i<DtuMain.switches.size();i++){
            for(int j=0;j<DtuMain.switches.get(i).getHosts().size();j++){
                if(DtuMain.switches.get(i).getHosts().get(j).getNodeConnectorId().getValue().equals(node.getValue())) {//busco el nodo origen
                    n=DtuMain.switches.get(i).getHosts().get(j).getIp();
                }
            }
        }
        return n;

    }

    /**
    * Method used for debugging
    */
    public  void printAllSwitchesHostsLinks(){
        for(int i=0;i<DtuMain.switches.size();i++){
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Switch "+ DtuMain.switches.get(i).getNodeId());
            for(int j=0;j<DtuMain.switches.get(i).getHosts().size();j++){
                LOG.info("Host: "+DtuMain.switches.get(i).getHosts().get(j).getNodeConnectorId().getValue()+" IP: "+DtuMain.switches.get(i).getHosts().get(j).getIp()+" Virtual Net.: "+DtuMain.switches.get(i).getHosts().get(j).getVlanId());
                for(NodeConnectorId host: DtuMain.switches.get(i).getHosts().get(j).getHashMap().keySet()) {
                    if (!DtuMain.switches.get(i).getHosts().get(j).getHashMap().isEmpty()) {
                        LOG.info("   Tunnel: Dst: " + getIp(new NodeConnectorId(host.getValue())) + ", Tag: " + DtuMain.switches.get(i).getHosts().get(j).getHashMap().get(host).toString());

                    }
                }
            }
            for(int j=0;j<DtuMain.switches.get(i).getLinks().size();j++){
                LOG.info("Link: "+DtuMain.switches.get(i).getLinks().get(j).getValue());
            }
        }
        LOG.info("+++++++++++++++++++++++++++++++++++++++++++");

    }
}
