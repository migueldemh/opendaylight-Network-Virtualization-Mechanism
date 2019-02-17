package org.opendaylight.dtu.impl.rev141210;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import com.google.common.base.Optional;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.TransactionStatus;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.controller.sal.binding.api.data.DataProviderService;
import org.opendaylight.openflowplugin.api.OFConstants;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Uri;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.pop.mpls.action._case.PopMplsActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.pop.vlan.action._case.PopVlanActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.push.mpls.action._case.PushMplsActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.push.vlan.action._case.PushVlanActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.field._case.SetFieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.vlan.id.action._case.SetMPLSIdActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.dtu.dtu.impl.rev141210.modules.module.configuration.dtu.impl.NotificationService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowCapableNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.Table;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.TableKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.service.rev130819.RemoveFlowInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.service.rev130819.SalFlowService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.OutputPortValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.InstructionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.ApplyActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.GoToTableCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.go.to.table._case.GoToTableBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.EtherType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.MPLSId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetDestinationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetTypeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.EthernetMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.ProtocolMatchFieldsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.VlanMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.ArpMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4Match;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.vlan.match.fields.MPLSIdBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.arp.rev140528.ArpPacketListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.arp.rev140528.ArpPacketReceived;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketReceived;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInputBuilder;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.NetworkTopology;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.TopologyId;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.Topology;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.TopologyKey;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.Link;
import org.opendaylight.yangtools.concepts.Registration;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.sdnhub.odl.tutorial.utils.GenericTransactionUtils;
import org.sdnhub.odl.tutorial.utils.PacketParsingUtils;
import org.sdnhub.odl.tutorial.utils.inventory.InventoryUtils;
import org.sdnhub.odl.tutorial.utils.openflow13.MatchUtils;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.PopMplsActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.PushMplsActionCaseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnector;
import org.opendaylight.l2switch.loopremover.util.InstanceIdentifierUtils;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.EtherType;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import static org.sdnhub.odl.tutorial.utils.openflow13.InstructionUtils.*;
import static org.sdnhub.odl.tutorial.utils.openflow13.MatchUtils.createArpDstIpv4Match;
import static org.sdnhub.odl.tutorial.utils.openflow13.MatchUtils.createInPortMatch;

public class DtuMain implements AutoCloseable, PacketProcessingListener   {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    //Members specific to this class
    private Map<String, String> hostAndVNetworks = new HashMap<String, String>();
    //Members related to MD-SAL operations
    private List<Registration> registrations;
    public static DataBroker dataBroker;
    private PacketProcessingService packetProcessingService;
    private static SalFlowService salFlowService;
    //List of switches/nodes
    public  static List<Switch> switches=new ArrayList<Switch>();
    //List of links
    public static List<Link> links;
    //List of path identifiers
    public static List<Integer> pathIdentifiers=new ArrayList<Integer>();
    //How the identifiers are assigned
    public static HashMap<String, Integer> hashMapTags=new HashMap<String, Integer>();
    Nodes nodes;
    boolean cont=true;
    boolean cont2=true;
    boolean cont3=false;


    public DtuMain(DataBroker dataBroker, NotificationProviderService notificationService, RpcProviderRegistry rpcProviderRegistry) {
        //Store the data broker for reading/writing from inventory store
        this.dataBroker = dataBroker;
        //Get access to the packet processing service for making RPC calls later
        this.packetProcessingService = rpcProviderRegistry.getRpcService(PacketProcessingService.class);
        //List used to track notification (both data change and YANG-defined) listener registrations
        this.registrations = Lists.newArrayList();
        //Object used for flow programming through RPC calls
        this.salFlowService = rpcProviderRegistry.getRpcService(SalFlowService.class);
        //known hosts and Virtual networks
        associationIpVirtualNetworks();
        registrations.add(notificationService.registerNotificationListener(this));
    }

    public void init(){
        DtuUtils.printhostAndVNetworks(hostAndVNetworks, LOG);
        //Switch discovery.
        getListNodes();
        //Store the switches
        storeNodes();
        printSwitches();
        //Install a flow rule in all the swithces to forward the packet to the controller.
        installFlowOutputControllerAllSwitches();
        //NetworkGraph npath=new NetworkGraph();
        //npath.addLinks(links);
        //List<Link> path=npath.getPath("openflow:9","openflow:1");
        //OG.info("-------///////////////-------");
        //LOG.info("-------///////////////-------");
        //LOG.info("-------///////////////-------");
        //for(int i=0;i<path.size();i++){
        //  LOG.info("Switch source: "+path.get(i).getSource().getSourceNode().toString());
        //  LOG.info("Switch Destination: " + path.get(i).getDestination().getDestNode().toString());
        //  LOG.info("Link: "+path.get(i).getDestination().getDestTp().getValue().toString());
        //   LOG.info("Link: "+path.get(i).getSource().getSourceTp().getValue().toString());
        //    }
    }

  /**
    *The controller knows how the hosts are associated.
    *hostAndVNetworks stores IP and Virtual Network ID
    */
    private void associationIpVirtualNetworks() {
        hostAndVNetworks.put("10.0.0.1", "1");
        hostAndVNetworks.put("10.0.0.2", "2");
        //hostAndVNetworks.put("10.0.0.3", "1");//This will be added via restconf
        hostAndVNetworks.put("10.0.0.4", "1");
        hostAndVNetworks.put("10.0.0.5", "2");
        initPathIdentifiers();
    }

  /**Define identifiers
    */
    public void initPathIdentifiers(){
        for(String ip: hostAndVNetworks.keySet()){
          //LOG.info(" pathIdentifiers: " + Integer.parseInt(hostAndVNetworks.get(ip)));
            if(!pathIdentifiers.contains(Integer.parseInt(hostAndVNetworks.get(ip))*100)){
                pathIdentifiers.add(Integer.parseInt(hostAndVNetworks.get(ip))*100);
                hashMapTags.put(hostAndVNetworks.get(ip),Integer.parseInt(hostAndVNetworks.get(ip))*100);
            }
        }
    }


 /**
  *Install a flow rule in all the switches
  *Forward packet to the controller
  */
   public void installFlowOutputControllerAllSwitches() {
        LOG.info("-------Installing Flow: Output controller-------");
        for (Node node : nodes.getNode()) {
            LOG.info("-------"+node.getId().getValue()+"-------");
            FlowDtuUtils.createFlowsFromSwitchToController(node.getId());
        }
        LOG.info("-------------------------");
    }

    @Override
    /**
      *This method treat every packet received from the switches.
      *It used during the host discovery. After receive a packet from each known host and identify where is locate
      *start the flow rule installation.
      *@param packetReceived source switch
      */
    public void onPacketReceived(PacketReceived packetReceived) {
        byte[] payload = packetReceived.getPayload();
        //Ignore LLDP packets, or you will be in big trouble
        byte[] etherTypeRaw = PacketParsingUtils.extractEtherType(packetReceived.getPayload());
        int etherType = (0x0000ffff & ByteBuffer.wrap(etherTypeRaw).getShort());
        if (etherType == 0x88cc) {
            return;
        }
        byte[] IPraw =DtuUtils.extracIpSender(payload);
        String ip= DtuUtils.asString(IPraw);
        NodeConnectorRef ingressNodeConnectorRef = packetReceived.getIngress();
        NodeRef ingressNodeRef = InventoryUtils.getNodeRef(ingressNodeConnectorRef);
        NodeConnectorId ingressNodeConnectorId = InventoryUtils.getNodeConnectorId(ingressNodeConnectorRef);
        NodeId ingressNodeId = InventoryUtils.getNodeId(ingressNodeConnectorRef);
        byte[] ArpRaw=DtuUtils.extracArpType(payload);
        String arpType=DtuUtils.asStringARP(ArpRaw);
        //LOG.info("*************Packet Received is a ************"+arpType+"*********************");
        if (etherType == 0x806&&arpType.equals("1")) {
            if (DtuUtils.belongToVirtualNetwork(hostAndVNetworks, ip, LOG)) {
                LOG.info("Received packet from known Host");
                LOG.info("IP: " + ip);
                LOG.info("Switch: " + ingressNodeId.getValue());
                LOG.info("Switch connector: " + ingressNodeConnectorId.getValue());
                if (cont) {
                    cont = false;
                    readLinks();
                    storeLinks();
                }
                //store the host in a switch
                addHostToSwitch(ingressNodeId, ingressNodeConnectorId, ip);
                //Remove from the list
                hostAndVNetworks.remove(ip);
            } else {
              //LOG.info("*************Received packet from unknown Host*********************************");
              //LOG.info("*************" + ip + "*********************************");
                if(!ipIsStore(ip)) {
                    LOG.info("Received packet from unknown Host");
                    LOG.info("IP: " + ip);
                    LOG.info("Switch: " + ingressNodeId.getValue());
                    LOG.info("Switch connector: " + ingressNodeConnectorId.getValue());
                    addHostToSwitch(ingressNodeId, ingressNodeConnectorId, ip);
                }
            }
        }
        if(hostAndVNetworks.isEmpty()){//List is empty. All the host were discovered!
            try {
                if(cont2) {
                    LOG.info("*************Controller found all hosts********************");
                    for (Node node : nodes.getNode()) {
                        //The controller does not need to receive more packets. Remove the flow rule in all the switches.
                        removeAllFlows(node.getId());
                    }
                }
                if(cont2){
                    printAllSwitchesHostsLinks();
                    NetworkGraph npath=new NetworkGraph();
                    npath.addLinks(links);
                    for(int i=0;i<switches.size();i++){
                        LOG.info("*************************************");
                        LOG.info("Switch: "+switches.get(i).getNodeId());
                        if(!switches.get(i).getHosts().isEmpty()){
                            for(int j=0;j<switches.get(i).getHosts().size();j++) {
                                LOG.info("hosts" + switches.get(i).getHosts().get(j).getNodeConnectorId().toString());
                                //If the switch contains a host assigned to a Virtual network
                                if(!switches.get(i).getHosts().get(j).getMPLSId().equals("FREE")){
                                  //Get all the host in the same Virtual network
                                  List<Host> h = DtuUtils.hostsSameVlanTopology(switches.get(i).getHosts().get(j).getMPLSId());
                                  h.remove(switches.get(i).getHosts().get(j));
                                  //Install the flow rules
                                  createMplsFlows(switches.get(i),switches.get(i).getHosts().get(j),h,npath);
                                }
                            }
                        }

                    }
                    LOG.info("*************************************");
                    LOG.info("Installed Flows");
                    printAllSwitchesHostsLinks();
                    cont2=false;
                    cont3=true;
                }
            }catch (Exception e){}
        }
        if (etherType == 0x806&&cont3==true) {
            String vlan=DtuUtils.getHost(ingressNodeConnectorId).getMPLSId();
            if(!vlan.equals("FREE")) {
              List<Host> h = DtuUtils.hostsSameVlanTopology(DtuUtils.getHost(ingressNodeConnectorId).getMPLSId());
              h.remove(DtuUtils.getHost(ingressNodeConnectorId));
              for (int i = 0; i < h.size(); i++) {
                NodeConnectorRef egressNodeConnectorRef = InventoryUtils.getNodeConnectorRef(h.get(i).getNodeConnectorId());//Puerto
                NodeRef egressNodeRef = InventoryUtils.getNodeRef(egressNodeConnectorRef);
                packetOut(egressNodeRef, egressNodeConnectorRef, payload);
              }
            }
        }
      }

  /**
    *This method create the label swich path from a source host
    *to the rest of the hosts in the same virtual network.
    *@param s source switch
    *@param hSrc source Host
    *@param hDst List of destination Hosts. Hosts belong to the same Virtual network.
    *@param npath contains the network topology
    */
    public void createMplsFlows(Switch s,Host hSrc,List<Host> hDst, NetworkGraph npath){
        for (int z = 0; z < hDst.size(); z++) {
            LOG.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            LOG.info("Installing Flows between " + hSrc.getSwitchName() + " " + hDst.get(z).getSwitchName());
            //Get the links bertween two host
            List<Link> path = npath.getPath(hSrc.getSwitchName(), hDst.get(z).getSwitchName());
            //Organize the links
            List<NodeConnectorId> order = putThemInOrder(hSrc.getSwitchName(), path);
            //Assing or get the label swich path identifier.
            String MPLSId = giveVlanMPLSIdTags(hSrc.getNodeConnectorId(), hDst.get(z).getNodeConnectorId());
            LOG.info("Tunnel Tag:   " + MPLSId);
            LOG.info("PushTag:" + s.getNodeId() + ",In: " + hSrc.getNodeConnectorId().getValue() + ",IPdst:" + hDst.get(z).getIp() + ",Tag: " + MPLSId + ",Out:" + order.get(0).getValue());
            //Create First flow rule in the source switch. It is responsible of push the identifier.
            FlowDtuUtils.pushtagMPLS(new NodeId(s.getNodeId()), hSrc.getNodeConnectorId(), hDst.get(z).getIp(), MPLSId, order.get(0));
            FlowDtuUtils.createFlowsARPSwitchToController(new NodeId(s.getNodeId()), hSrc.getNodeConnectorId(), hDst.get(z).getIp());
            int y = 1;
            int loop = order.size() - 1;
            for (y = 1; y < loop; y++) {
                LOG.info("HopFlow:" + InventoryUtils.getNodeId(order.get(y)).getValue() + ", In: " + order.get(y).getValue() + ", Tag: " + MPLSId + ", Out:" + order.get(y + 1).getValue());
                //Intermediate nodes, route the packet based in the identifer.
                FlowDtuUtils.HopFlowMPLS(InventoryUtils.getNodeId(order.get(y)), order.get(y), MPLSId, order.get(y + 1));
                y = y + 1;
            }
            LOG.info("StripTag:" + InventoryUtils.getNodeId(order.get(y)).getValue() + ", In:" + order.get(y).getValue() + ", Tag: " + MPLSId + ", Out:" + hDst.get(z).getNodeConnectorId().getValue());
            //Last siwtch contains the flow rule to remove the label.
            FlowDtuUtils.stripTagMPLS(InventoryUtils.getNodeId(order.get(y)), order.get(y), MPLSId, hDst.get(z).getNodeConnectorId());
        }
    }

    /**
    *Associate an unique identifier between two switches
    *@param source NodeConnectorId
    *@param destination NodeConnectorId
    *@return Label switch path identifier assigned
    */
    public static String giveVlanMPLSIdTags(NodeConnectorId source, NodeConnectorId destination){
        Host srcHost=DtuUtils.getHost(source);
        Host dstHost=DtuUtils.getHost(destination);
        Integer MPLSId=new Integer(0);
        for(int i=0;i<pathIdentifiers.size();i++){
        }
        if (!srcHost.getHashMap().containsKey(destination)) {
            srcHost.getHashMap().put(destination, hashMapTags.get(srcHost.getMPLSId()));
            dstHost.getHashMap().put(source, hashMapTags.get(srcHost.getMPLSId()));
            MPLSId=hashMapTags.get(srcHost.getMPLSId());
            hashMapTags.put(srcHost.getMPLSId(),hashMapTags.get(srcHost.getMPLSId())+1);
        }else {
            MPLSId = srcHost.getHashMap().get(destination);
        }
        return MPLSId.toString();
    }

    /**
    *This method check if an IP was already stored
    *@param IP address
    *@return true/false
    */
    public boolean ipIsStore(String ip){
        boolean r =false;
        for(int i=0;i<switches.size();i++) {
            for (int j = 0; j < switches.get(i).getHosts().size(); j++) {
                if (switches.get(i).getHosts().get(j).getIp().equals(ip)) {
                    r=true;
                }
            }
        }
        return r;
    }

    /**
    * This method store the switches
    */
    private void storeNodes() {
      //LOG.info("-------SWITCHES-------");
        for (Node node : nodes.getNode()) {
          //LOG.info("Node ID : {}", node.getId()+" "+node.getId().getValue()+" "+node.toString());
            switches.add(new Switch(node.getId().getValue()));
        }
        //LOG.info("----------------------");
    }

    /**
    * This method extract the links from the datastore
    */
    private void readLinks(){
        LOG.info("-------reading Links-------");
        InstanceIdentifier<Topology> topologyInstanceIdentifier = InstanceIdentifierUtils
                .generateTopologyInstanceIdentifier("flow:1");
        Topology topology = null;
        ReadOnlyTransaction readOnlyTransaction = dataBroker.newReadOnlyTransaction();
        try {
            Optional<Topology> topologyOptional = readOnlyTransaction
                    .read(LogicalDatastoreType.OPERATIONAL, topologyInstanceIdentifier).get();
            if (topologyOptional.isPresent()) {
                topology = topologyOptional.get();
            }else{LOG.info("No esta presente");}
            readOnlyTransaction.close();
            if (topology == null) {
                LOG.info("topology es null");
            }
            links = topology.getLink();
        } catch (Exception e) {
            LOG.error("Error reading topology {}", topologyInstanceIdentifier);
            readOnlyTransaction.close();
            throw new RuntimeException(
                    "Error reading from operational store, topology : " + topologyInstanceIdentifier, e);
        }
    }

    /**
    * This method store all the link information in the switches
    */
    private void storeLinks(){
        for(int i=0;i<links.size();i++){
            Link l=links.get(i);
            for(int j=0;j<switches.size();j++){
                Switch s=switches.get(j);
                String n= l.getSource().getSourceNode().getValue();
                if(s.getNodeId().toString().equals(n)){
                    s.setAlink(new NodeConnectorId(l.getSource().getSourceTp().getValue().toString()));
                }
            }
        }
    }

    /**
    * Read the Inventory data tree to find information about the Nodes and
    * NodeConnectors.
    */
    public void getListNodes() {
        try {
            InstanceIdentifier.InstanceIdentifierBuilder<Nodes> nodesInsIdBuilder = InstanceIdentifier
                    .<Nodes>builder(Nodes.class);
            ReadOnlyTransaction readOnlyTransaction = dataBroker.newReadOnlyTransaction();
            Optional<Nodes> dataObjectOptional = null;
            dataObjectOptional = readOnlyTransaction
                    .read(LogicalDatastoreType.OPERATIONAL, nodesInsIdBuilder.build()).get();
            if (dataObjectOptional.isPresent()) {
                nodes = (Nodes) dataObjectOptional.get();
            }
            readOnlyTransaction.close();
        } catch (InterruptedException e) {
            LOG.info("Failed to read nodes from Operation data store.");
            throw new RuntimeException("Failed to read nodes from Operation data store.", e);
        } catch (ExecutionException e) {
            LOG.info("Failed to read nodes from Operation data store.");
            throw new RuntimeException("Failed to read nodes from Operation data store.", e);
        }
        if (nodes == null) {
            // Reschedule thread when the data store read had errors
            LOG.info("Error to add switches");
        } else{LOG.info("Adding Switches");
        }
    }

    /**
    *The controller send a message to a specific switch to remove all the flow rules
    *@param NodeId of a switch
    */
    private void removeAllFlows(NodeId nodeId){
        NodeRef noderef=InventoryUtils.getNodeRef(nodeId);
        LOG.info("DELETE FLOWS IN SWITCH: "+nodeId.toString());
        //LOG.info("" + noderef);
        RemoveFlowInputBuilder flowBuilder = new RemoveFlowInputBuilder()
                .setBarrier(true)
                .setNode(noderef);
        salFlowService.removeFlow(flowBuilder.build());
    }

    /**
    *This method store host objects in a switch object
    *Add a host to a switch
    *@param NodeId of a switch
    *@param NodeConnectorId of a switch
    *@param String ip of a host
    */
    public void addHostToSwitch(NodeId nodeId,NodeConnectorId nodeConnectorId, String ip) {
        String MPLSId = hostAndVNetworks.get(ip);
        if(MPLSId==null){
            MPLSId="FREE";
        }
        Host h = new Host(MPLSId, ip, nodeConnectorId, nodeId.getValue());
        for (int j = 0; j < switches.size(); j++) {
            Switch s = switches.get(j);
            //LOG.info("++++++++Add Host To Switch++++++++++");
            //LOG.info("+++"+s.getNodeId().toString()+"++++"+nodeId.getValue());
            if (s.getNodeId().toString().equals(nodeId.getValue())) {
                s.setAHost(h);
                //LOG.info("++++++++Stored in:++++++++++");
                //LOG.info("Switch "+s.getNodeId().toString());
                //LOG.info("el host "+h.getNodeConnectorId().toString());
                //LOG.info("with tag vlan "+h.getMPLSId());
                //LOG.info("with IP "+h.getIp());
                //LOG.info("++++++++***********++++++++++");
            }
        }
    }

    /**
    *Recive a NodeConnectorId and return the IP of the host
    *@param NodeRef egressNodeRef
    *@param NodeConnectorRef egressNodeConnectorRef
    *@param byte[] payload
    *@return IP address
    */
    private void packetOut(NodeRef egressNodeRef, NodeConnectorRef egressNodeConnectorRef, byte[] payload) {
        Preconditions.checkNotNull(packetProcessingService);
        LOG.debug("Flooding packet of size {} out of port {}", payload.length, egressNodeConnectorRef);
        //Construct input for RPC call to packet processing service
        TransmitPacketInput input = new TransmitPacketInputBuilder()
                .setPayload(payload)
                .setNode(egressNodeRef)
                .setEgress(egressNodeConnectorRef)
                .build();
        packetProcessingService.transmitPacket(input);
    }

    /**
    *Recive a NodeConnectorId and return the IP of the host
    *@param NodeConnectorId a list of links
    *@return IP address
    */
    public String getIp(NodeConnectorId node){
        String n=new String();
        for(int i=0;i<DtuMain.switches.size();i++){
            for(int j=0;j<DtuMain.switches.get(i).getHosts().size();j++){
                if(DtuMain.switches.get(i).getHosts().get(j).getNodeConnectorId().getValue().equals(node.getValue())) {
                    n=DtuMain.switches.get(i).getHosts().get(j).getIp();
                }
            }
        }
        return n;
    }

    /**
    * Receive the source switch and a list of links(path)between switches to reach the destionation switch.
    *This method organize the links
    *@param source switch
    *@param path a list of links
    *@return a list of links
    */
    public static List<NodeConnectorId> putThemInOrder(String source,List<Link> path){
        List<NodeConnectorId> order= new ArrayList<NodeConnectorId>();
        String src=source;
        for(int i=0;i<path.size();i++){
            if(src.equals(path.get(i).getDestination().getDestNode().getValue())){
                order.add(new NodeConnectorId(path.get(i).getDestination().getDestTp().getValue()));
                order.add(new NodeConnectorId(path.get(i).getSource().getSourceTp().getValue()));
                src=path.get(i).getSource().getSourceNode().getValue();
            }else if(src.equals(path.get(i).getSource().getSourceNode().getValue())){
                order.add(new NodeConnectorId(path.get(i).getSource().getSourceTp().getValue()));
                order.add(new NodeConnectorId(path.get(i).getDestination().getDestTp().getValue()));
                src=path.get(i).getDestination().getDestNode().getValue();
            }
        }
        return order;
    }

    /**
    *This method is used for debug
    * Print Switches, hosts and links stored
    */
    public  void printAllSwitchesHostsLinks(){
        for(int i=0;i<DtuMain.switches.size();i++){
            LOG.info("+++++++++++++++++++++++++++++++++++++++++++");
            LOG.info("Switch "+ DtuMain.switches.get(i).getNodeId());
            for(int j=0;j<DtuMain.switches.get(i).getHosts().size();j++){
                LOG.info("Host: "+DtuMain.switches.get(i).getHosts().get(j).getNodeConnectorId().getValue()+" IP: "+DtuMain.switches.get(i).getHosts().get(j).getIp()+" Virtual Net.: "+DtuMain.switches.get(i).getHosts().get(j).getMPLSId());
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

    /**
    *This method is used for debug
    * Print all switches found in the topology
    */
    private void printSwitches(){
        for(int i=0;i<switches.size();i++){
            Switch l=switches.get(i);
            LOG.info(" Imprimo el link entero "+l.getNodeId());
        }
    }

    @Override
    public void close() throws Exception {
        for (Registration registration : registrations) {
            registration.close();
        }
        registrations.clear();
    }


}
