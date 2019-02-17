package org.opendaylight.dtu.impl.rev141210;

import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//This class contains methods for debuggind. Also util funtions.
public class DtuUtils {

    public DtuUtils() {
    }

    /**
    * Recieve a NodeConnectorId(port) and return the host connected
    *@param node NodeConnectorId
    *@return n Host
    */
    public static Host getHost(NodeConnectorId node){
        Host n=new Host();
        for(int i=0;i<DtuMain.switches.size();i++){
            for(int j=0;j<DtuMain.switches.get(i).getHosts().size();j++){
                if(DtuMain.switches.get(i).getHosts().get(j).getNodeConnectorId().equals(node)){//busco el nodo origen
                    n=DtuMain.switches.get(i).getHosts().get(j);
                }
            }
        }
        return n;
    }

    /**
    * Recieve a identifier and return a list of hosts belong same virtual networks
    *@param node NodeConnectorId
    *@return h list of hosts
    */
    public static List<Host> hostsSameVlanTopology(String identifier) {
        List<Host> h= new ArrayList<Host>();
        for (int i = 0; i < DtuMain.switches.size(); i++) {
            if(!DtuMain.switches.get(i).getHosts().isEmpty()) {
                for (int j = 0; j < DtuMain.switches.get(i).getHosts().size(); j++) {
                    if (DtuMain.switches.get(i).getHosts().get(j).getId().equals(identifier)) {
                        h.add(DtuMain.switches.get(i).getHosts().get(j));
                    }
                }
            }
        }
        return h;
    }

    /**
    * From a payload this method extract the sender IP address
    *@param payload
    *@return byte[] IP address
    */
    public static byte[] extracIpSender(byte[] payload) {
        return Arrays.copyOfRange(payload, 28, 32);
    }

    /**
    * From a payload this method extract the ARP type
    *@param payload
    *@return byte[] Arp type
    */
    public static byte[] extracArpType(byte[] payload) {
        return Arrays.copyOfRange(payload, 20, 22);
    }

    /**
    * Convert the ARP type to string
    *@param arpType
    *@return string
    */
    public static String asStringARP(byte[] arpType) {
        //  Check if the address is valid
        if ( arpType == null || arpType.length != 2)
            return null;
          //  Convert the raw IP address to a string
        StringBuffer str = new StringBuffer();
        str.append((int) ( arpType[1] & 0xFF));
           //  Return the address string
        return str.toString();
    }

    /**
    * Convert the IP addres to string
    *@param arpType
    *@return string
    */
    public static String asString(byte[] ipaddr) {
        //  Check if the address is valid
        if ( ipaddr == null || ipaddr.length != 4)
          return null;
        //  Convert the raw IP address to a string
        StringBuffer str = new StringBuffer();
          str.append((int) ( ipaddr[0] & 0xFF));
          str.append(".");
          str.append((int) ( ipaddr[1] & 0xFF));
          str.append(".");
          str.append((int) (ipaddr[2] & 0xFF));
          str.append(".");
          str.append((int) (ipaddr[3] & 0xFF));
          //  Return the address string
        return str.toString();
    }

    /**
    * Print info: host and Virtual networks(used for debug)
    */
    public static void printhostAndVNetworks(Map<String, String> hosAndVlans,Logger LOG ) {
        LOG.info("Table");

        for (Map.Entry<String, String> entry : hosAndVlans.entrySet()) {
            LOG.info("IP: " + entry.getKey() + " | VLAN: " + entry.getValue());
        }
    }

    /**
    * Print info: switches(used for debug)
    * @param nodes
    */
    public static void printNodes(Nodes nodes,Logger LOG) {
        LOG.info("-------SWITCHES-------");
        for (Node node : nodes.getNode()) {
            LOG.info("Node ID : {}", node.getId());
        }
        LOG.info("----------------------");

    }

    /**
    * Print info: Links(used for debug)
    * @param links
    */
    public static void printLinksData(List<Link> links,Logger LOG){
        for(int i=0;i<links.size();i++){
            Link l=links.get(i);
            LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info(" Print full link "+l.toString());
            LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info(" Print key"+l.getKey());
            LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info(" Print  link ID "+l.getLinkId());
            LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info(" Print destination " +l.getDestination());
            LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info(" Print source "+l.getSource());
            LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info(" Print supportinglink "+l.getSupportingLink());
            LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info(" Print interface  "+l.getImplementedInterface());
            LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info(" Source node  "+l.getSource().getSourceNode().getValue());
            LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
            LOG.info(" The link  "+l.getSource().getSourceTp().getValue());
            LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

    }

    /**
    * Verify if an IP belongs to a specific virtual netork
    * @param hosAndVlans
    * @param ip
    * @param LOG
    * @return true/false
    */
    public static boolean belongToVirtualNetwork(Map<String, String> hosAndVlans,String ip,Logger LOG){
        boolean b=false;
        for (Map.Entry<String, String> entry : hosAndVlans.entrySet()) {
            if(entry.getKey().equals(ip)){
                b=true;
            }
        }
        return b;
    }

    /**
    * Verify if an IP belongs to a specific virtual netork
    * @param s
    * @param identifier
    * @return true/false
    */
    public List<Host> hostsSameVlanSameSwitch(Switch s, String identifier) {
        List<Host> h= new ArrayList<Host>();
        for (int i = 0; i < s.getHosts().size(); i++) {
            if(s.getHosts().get(i).getId().equals(identifier)){
                h.add(s.getHosts().get(i));
            }

        }
        return h;
    }

}
