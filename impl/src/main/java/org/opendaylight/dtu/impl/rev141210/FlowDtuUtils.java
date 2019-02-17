package org.opendaylight.dtu.impl.rev141210;

import com.google.common.collect.Lists;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Uri;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.pop.mpls.action._case.PopMplsActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.pop.vlan.action._case.PopVlanActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.push.mpls.action._case.PushMplsActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.push.vlan.action._case.PushVlanActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.field._case.SetFieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.vlan.id.action._case.SetVlanIdActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowCapableNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.Table;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.TableKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.OutputPortValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.InstructionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.ApplyActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.EtherType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.VlanId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetDestinationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetTypeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.EthernetMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.ProtocolMatchFieldsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.VlanMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.ArpMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.vlan.match.fields.VlanIdBuilder;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.sdnhub.odl.tutorial.utils.GenericTransactionUtils;
import java.util.List;
import static org.sdnhub.odl.tutorial.utils.openflow13.MatchUtils.createArpDstIpv4Match;

/**
 * This class contains the methods to install different types of flow rules.
 */
public class FlowDtuUtils {

    ///////////////////////////////////////////////
    ////////////////MPLS//////////////////////////////
    ///////////////////////////////////////////////

    /**
    *This method install a flow rule to route the packet between switches.
    *Match fields are port and path identifier.
    *Action is output port.
    *@param nodeId Switch
    *@param nodeconceID ingress port
    *@param ipDst destination ip
    *@param mplsID path identifier
    *@param nodeconceout egrees port
    */
    public static void HopFlowMPLS(NodeId nodeId,NodeConnectorId nodeconceID,String mplsID, NodeConnectorId nodeout ){
        MatchBuilder matchBuilder = new MatchBuilder();
        FlowDtuUtils.createInPortMatch(matchBuilder, nodeconceID);
        createMplsLabelBosMatch(matchBuilder, (long) (Integer.valueOf(mplsID)), true);
        InstructionsBuilder isb = new InstructionsBuilder();
        List<Instruction> instructions = Lists.newArrayList();
        InstructionBuilder ib = new InstructionBuilder();
        ApplyActionsBuilder aab = new ApplyActionsBuilder();
        ActionBuilder ab = new ActionBuilder();
        List<Action> actionList = Lists.newArrayList();
        OutputActionBuilder output = new OutputActionBuilder();
        output.setOutputNodeConnector(nodeout);
        output.setMaxLength(65535); //Send full packet and No buffer
        ab.setAction(new OutputActionCaseBuilder().setOutputAction(output.build()).build());
        ab.setOrder(0);
        ab.setKey(new ActionKey(0));
        actionList.add(ab.build());
        // Create Apply Actions Instruction
        aab.setAction(actionList);
        ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
        ib.setOrder(1);
        ib.setKey(new InstructionKey(1));
        instructions.add(ib.build());
        // Create Flow
        FlowBuilder flowBuilder = new FlowBuilder();
        flowBuilder.setMatch(matchBuilder.build());
        String flowId = "Rule"+nodeId+nodeconceID+nodeconceID+mplsID;
        flowBuilder.setId(new FlowId(flowId));
        FlowKey key = new FlowKey(new FlowId(flowId));
        flowBuilder.setBarrier(true);
        flowBuilder.setTableId((short) 0);  //Table 0
        flowBuilder.setKey(key);
        flowBuilder.setPriority(32768);
        flowBuilder.setFlowName(flowId);
        flowBuilder.setHardTimeout(0);
        flowBuilder.setIdleTimeout(0);
        flowBuilder.setInstructions(isb.setInstruction(instructions).build());
        InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
                .child(Node.class, new NodeKey(nodeId))
                .augmentation(FlowCapableNode.class)
                .child(Table.class, new TableKey(flowBuilder.getTableId()))
                .child(Flow.class, flowBuilder.getKey())
                .build();
        GenericTransactionUtils.writeData(DtuMain.dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
    }

    /**
    * This method install a flow rule in a destination switch. The tag is removed from the packet before forward to destination host.
    *Match fields are port and path identifier.
    *Action is pop the tag and output port.
    *@param nodeId Switch
    *@param nodeconceID ingress port
    *@param ipDst destination ip
    *@param mplsID path identifier
    *@param nodeconceout egrees port
    */
    public static void stripTagMPLS(NodeId nodeId,NodeConnectorId nodeconceID,String mplsID, NodeConnectorId nodeout ){
        //Creating match object
        MatchBuilder matchBuilder = new MatchBuilder();
        FlowDtuUtils.createInPortMatch(matchBuilder, nodeconceID);
        createMplsLabelBosMatch(matchBuilder, (long) (Integer.valueOf(mplsID)), true);
        // Instructions List Stores Individual Instructions
        InstructionsBuilder isb = new InstructionsBuilder();
        List<Instruction> instructions = Lists.newArrayList();
        InstructionBuilder ib = new InstructionBuilder();
        ApplyActionsBuilder aab = new ApplyActionsBuilder();
        ActionBuilder ab = new ActionBuilder();
        List<Action> actionList = Lists.newArrayList();
        PopMplsActionBuilder popMplsActionBuilder = new PopMplsActionBuilder();
        popMplsActionBuilder.setEthernetType(0x800);
        ab.setAction(new PopMplsActionCaseBuilder().setPopMplsAction(popMplsActionBuilder.build()).build());
        ab.setKey(new ActionKey(0));
        actionList.add(ab.build());
        OutputActionBuilder output = new OutputActionBuilder();
        output.setOutputNodeConnector(nodeout);
        output.setMaxLength(65535); //Send full packet and No buffer
        ab.setAction(new OutputActionCaseBuilder().setOutputAction(output.build()).build());
        ab.setOrder(1);
        ab.setKey(new ActionKey(1));
        actionList.add(ab.build());
        aab.setAction(actionList);
        ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
        ib.setOrder(1);
        ib.setKey(new InstructionKey(1));
        instructions.add(ib.build());
        // Create Flow
        FlowBuilder flowBuilder = new FlowBuilder();
        flowBuilder.setMatch(matchBuilder.build());
        String flowId = "Rule"+nodeId+nodeconceID+mplsID;
        flowBuilder.setId(new FlowId(flowId));
        FlowKey key = new FlowKey(new FlowId(flowId));
        flowBuilder.setBarrier(true);
        flowBuilder.setTableId((short) 0);  //Table 0
        flowBuilder.setKey(key);
        flowBuilder.setPriority(32768);
        flowBuilder.setFlowName(flowId);
        flowBuilder.setHardTimeout(0);
        flowBuilder.setIdleTimeout(0);
        flowBuilder.setInstructions(isb.setInstruction(instructions).build());
        InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
                .child(Node.class, new NodeKey(nodeId))
                .augmentation(FlowCapableNode.class)
                .child(Table.class, new TableKey(flowBuilder.getTableId()))
                .child(Flow.class, flowBuilder.getKey())
                .build();
        GenericTransactionUtils.writeData(DtuMain.dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);

    }

    /**
    * This method install a flow in a source switch. The tag is push in a packet before forward to next switch.
    *Match fields are port, IP address and path identifier.
    *Action is pop the tag and output port.
    *@param nodeId Switch
    *@param nodeconceID ingress port
    *@param ipDst destination ip
    *@param mplsID path identifier
    *@param nodeconceout egrees port
    */
    public static void pushtagMPLS(NodeId nodeId, NodeConnectorId nodeconceID,String ipDst,String mplsID, NodeConnectorId nodeconceout) {
        //Creating match object
        MatchBuilder matchBuilder = new MatchBuilder();
        FlowDtuUtils.createInPortMatch(matchBuilder, nodeconceID);
        createEtherTypeMatch(matchBuilder, new EtherType(0x0806L));
        createDstL3IPv4Match(matchBuilder,iPv4PrefixFromIPv4Address(ipDst));
        // Instructions List Stores Individual Instructions
        InstructionsBuilder isb = new InstructionsBuilder();
        List<Instruction> instructions = Lists.newArrayList();
        InstructionBuilder ib = new InstructionBuilder();
        ApplyActionsBuilder aab = new ApplyActionsBuilder();
        ActionBuilder ab = new ActionBuilder();
        List<Action> actionList = Lists.newArrayList();
        /* First we push vlan header */
        PushMplsActionBuilder push = new PushMplsActionBuilder();
        push.setEthernetType(Integer.valueOf(0x8847));
        ab.setAction(new PushMplsActionCaseBuilder().setPushMplsAction(push.build()).build());
        ab.setKey(new ActionKey(0));
        actionList.add(ab.build());
        ProtocolMatchFieldsBuilder matchFieldsBuilder = new ProtocolMatchFieldsBuilder().setMplsLabel((long)(Integer.parseInt(mplsID))).setMplsBos((short)1);
        ab.setOrder(1).setKey(new ActionKey(1)).setAction(new SetFieldCaseBuilder().
                setSetField(new SetFieldBuilder().setProtocolMatchFields(matchFieldsBuilder.build()).build()).build());
        actionList.add(ab.build());
        OutputActionBuilder output = new OutputActionBuilder();
        output.setOutputNodeConnector(nodeconceout);
        output.setMaxLength(65535); //Send full packet and No buffer
        ab.setAction(new OutputActionCaseBuilder().setOutputAction(output.build()).build());
        ab.setOrder(2);
        ab.setKey(new ActionKey(2));
        actionList.add(ab.build());
        // Create Apply Actions Instruction
        aab.setAction(actionList);
        ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
        ib.setOrder(1);
        ib.setKey(new InstructionKey(1));
        instructions.add(ib.build());
        // Create Flow
        FlowBuilder flowBuilder = new FlowBuilder();
        flowBuilder.setMatch(matchBuilder.build());
        String flowId = "Rule"+nodeId+nodeconceID+mplsID;
        flowBuilder.setId(new FlowId(flowId));
        FlowKey key = new FlowKey(new FlowId(flowId));
        flowBuilder.setBarrier(true);
        flowBuilder.setTableId((short) 0);  //Table 0
        flowBuilder.setKey(key);
        flowBuilder.setPriority(32768);
        flowBuilder.setFlowName(flowId);
        flowBuilder.setHardTimeout(0);
        flowBuilder.setIdleTimeout(0);
        flowBuilder.setInstructions(isb.setInstruction(instructions).build());
        InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
                .child(Node.class, new NodeKey(nodeId))
                .augmentation(FlowCapableNode.class)
                .child(Table.class, new TableKey(flowBuilder.getTableId()))
                .child(Flow.class, flowBuilder.getKey())
                .build();
        GenericTransactionUtils.writeData(DtuMain.dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);

    }

    /**
    * This method help to create the match field for MPLS flow rules.
    *@param matchBuilder
    *@param label
    *@param bos
    *@return MatchBuilder
    */
    public static  MatchBuilder createMplsLabelBosMatch(MatchBuilder matchBuilder, Long label, boolean bos) {
        EthernetMatchBuilder eth = new EthernetMatchBuilder();
        EthernetTypeBuilder ethTypeBuilder = new EthernetTypeBuilder();
        ethTypeBuilder.setType(new EtherType(Long.valueOf(34887L)));
        eth.setEthernetType(ethTypeBuilder.build());
        matchBuilder.setEthernetMatch(eth.build());
        ProtocolMatchFieldsBuilder matchFieldsBuilder = (new ProtocolMatchFieldsBuilder()).setMplsLabel(label).setMplsBos(Short.valueOf((short)1));
        matchBuilder.setProtocolMatchFields(matchFieldsBuilder.build());
        return matchBuilder;
    }

    /**
    * This method help to resolve the ARP request between hosts in different switches.
    *@param nodeId Switch
    *@param nodeconceID ingress port
    *@param ipDst destination ip
    *@param mplsID path identifier
    *@param nodeconceout egrees port
    */
    public void pushARPMPLS(NodeId nodeId, NodeConnectorId nodeconceID,String ipDst,String mplsID, NodeConnectorId nodeconceout) {
        MatchBuilder matchBuilder = new MatchBuilder();
        FlowDtuUtils.createInPortMatch(matchBuilder, nodeconceID);
        createEtherTypeMatch(matchBuilder, new EtherType(0x0806L));
        createArpDstIpv4Match(matchBuilder, iPv4PrefixFromIPv4Address(ipDst));
        InstructionsBuilder isb = new InstructionsBuilder();
        List<Instruction> instructions = Lists.newArrayList();
        InstructionBuilder ib = new InstructionBuilder();
        ApplyActionsBuilder aab = new ApplyActionsBuilder();
        ActionBuilder ab = new ActionBuilder();
        List<Action> actionList = Lists.newArrayList();
        PushMplsActionBuilder push = new PushMplsActionBuilder();
        push.setEthernetType(Integer.valueOf(0x8847));
        ab.setAction(new PushMplsActionCaseBuilder().setPushMplsAction(push.build()).build());
        ab.setKey(new ActionKey(0));
        actionList.add(ab.build());
        ProtocolMatchFieldsBuilder matchFieldsBuilder = new ProtocolMatchFieldsBuilder().setMplsLabel((long)(Integer.parseInt(mplsID))).setMplsBos((short)0);
        ab.setOrder(1).setKey(new ActionKey(1)).setAction(new SetFieldCaseBuilder().
                setSetField(new SetFieldBuilder().setProtocolMatchFields(matchFieldsBuilder.build()).build()).build());
        actionList.add(ab.build());
        OutputActionBuilder output = new OutputActionBuilder();
        output.setOutputNodeConnector(nodeconceout);
        output.setMaxLength(65535); //Send full packet and No buffer
        ab.setAction(new OutputActionCaseBuilder().setOutputAction(output.build()).build());
        ab.setOrder(2);
        ab.setKey(new ActionKey(2));
        actionList.add(ab.build());
        // Create Apply Actions Instruction
        aab.setAction(actionList);
        ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
        ib.setOrder(1);
        ib.setKey(new InstructionKey(1));
        instructions.add(ib.build());
        // Create Flow
        FlowBuilder flowBuilder = new FlowBuilder();
        flowBuilder.setMatch(matchBuilder.build());
        String flowId = "ARP"+nodeId+nodeconceID+mplsID;
        flowBuilder.setId(new FlowId(flowId));
        FlowKey key = new FlowKey(new FlowId(flowId));
        flowBuilder.setBarrier(true);
        flowBuilder.setTableId((short) 0);  //Table 0
        flowBuilder.setKey(key);
        flowBuilder.setPriority(32768);
        flowBuilder.setFlowName(flowId);
        flowBuilder.setHardTimeout(0);
        flowBuilder.setIdleTimeout(0);
        flowBuilder.setInstructions(isb.setInstruction(instructions).build());
        InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
                .child(Node.class, new NodeKey(nodeId))
                .augmentation(FlowCapableNode.class)
                .child(Table.class, new TableKey(flowBuilder.getTableId()))
                .child(Flow.class, flowBuilder.getKey())
                .build();
        GenericTransactionUtils.writeData(DtuMain.dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);

    }

    /////////////////////////////////////////////////////////
    /////////////////////END MPLS//////////////////////////////////////
    //////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////
    /////////////////////INIT VLAN//////////////////////////////////////
    //////////////////////////////////////////////////////////////


    /**
    * This method install a flow rule in a destination switch. The tag is removed from the packet before forward to destination host.
    *Match fields are port and path identifier.
    *Action is pop the tag and output port.
    *@param nodeId Switch
    *@param nodeconceID ingress port
    *@param ipDst destination ip
    *@param vlanId path identifier
    *@param nodeconceout egrees port
    */
    public static void stripTag(NodeId nodeId,NodeConnectorId nodeconceID,String vlanId, NodeConnectorId nodeout ){
        //Creating match object
        MatchBuilder matchBuilder = new MatchBuilder();
        FlowDtuUtils.createInPortMatch(matchBuilder, nodeconceID);
        createVlanIdMatch(matchBuilder, new VlanId(Integer.valueOf(vlanId)), true);
        // Instructions List Stores Individual Instructions
        InstructionsBuilder isb = new InstructionsBuilder();
        List<Instruction> instructions = Lists.newArrayList();
        InstructionBuilder ib = new InstructionBuilder();
        ApplyActionsBuilder aab = new ApplyActionsBuilder();
        ActionBuilder ab = new ActionBuilder();
        List<Action> actionList = Lists.newArrayList();
        PopVlanActionBuilder popVlanActionBuilder = new PopVlanActionBuilder();
        ab.setAction(new PopVlanActionCaseBuilder().setPopVlanAction(popVlanActionBuilder.build()).build());
        ab.setKey(new ActionKey(0));
        actionList.add(ab.build());
        OutputActionBuilder output = new OutputActionBuilder();
        output.setOutputNodeConnector(nodeout);
        output.setMaxLength(65535); //Send full packet and No buffer
        ab.setAction(new OutputActionCaseBuilder().setOutputAction(output.build()).build());
        ab.setOrder(1);
        ab.setKey(new ActionKey(1));
        actionList.add(ab.build());
        aab.setAction(actionList);
        ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
        ib.setOrder(1);
        ib.setKey(new InstructionKey(1));
        instructions.add(ib.build());
        // Create Flow
        FlowBuilder flowBuilder = new FlowBuilder();
        flowBuilder.setMatch(matchBuilder.build());
        String flowId = "Rule"+nodeId+nodeconceID+vlanId;
        flowBuilder.setId(new FlowId(flowId));
        FlowKey key = new FlowKey(new FlowId(flowId));
        flowBuilder.setBarrier(true);
        flowBuilder.setTableId((short) 0);  //Table 0
        flowBuilder.setKey(key);
        flowBuilder.setPriority(32768);
        flowBuilder.setFlowName(flowId);
        flowBuilder.setHardTimeout(0);
        flowBuilder.setIdleTimeout(0);
        flowBuilder.setInstructions(isb.setInstruction(instructions).build());
        InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
                .child(Node.class, new NodeKey(nodeId))
                .augmentation(FlowCapableNode.class)
                .child(Table.class, new TableKey(flowBuilder.getTableId()))
                .child(Flow.class, flowBuilder.getKey())
                .build();
        GenericTransactionUtils.writeData(DtuMain.dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);

    }

    /**
    *This method install a flow rule to route the packet between switches.
    *Match fields are port and path identifier.
    *Action is output port.
    *@param nodeId Switch
    *@param nodeconceID ingress port
    *@param ipDst destination ip
    *@param vlanId path identifier
    *@param nodeconceout egrees port
    */
    public static void HopFlow(NodeId nodeId,NodeConnectorId nodeconceID,String vlanId, NodeConnectorId nodeout ){
        MatchBuilder matchBuilder = new MatchBuilder();
        FlowDtuUtils.createInPortMatch(matchBuilder, nodeconceID);
        createVlanIdMatch(matchBuilder, new VlanId(Integer.valueOf(vlanId)), true);
        // Instructions List Stores Individual Instructions
        InstructionsBuilder isb = new InstructionsBuilder();
        List<Instruction> instructions = Lists.newArrayList();
        InstructionBuilder ib = new InstructionBuilder();
        ApplyActionsBuilder aab = new ApplyActionsBuilder();
        ActionBuilder ab = new ActionBuilder();
        List<Action> actionList = Lists.newArrayList();
        OutputActionBuilder output = new OutputActionBuilder();
        output.setOutputNodeConnector(nodeout);
        output.setMaxLength(65535); //Send full packet and No buffer
        ab.setAction(new OutputActionCaseBuilder().setOutputAction(output.build()).build());
        ab.setOrder(0);
        ab.setKey(new ActionKey(0));
        actionList.add(ab.build());
        // Create Apply Actions Instruction
        aab.setAction(actionList);
        ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
        ib.setOrder(1);
        ib.setKey(new InstructionKey(1));
        instructions.add(ib.build());
        // Create Flow
        FlowBuilder flowBuilder = new FlowBuilder();
        flowBuilder.setMatch(matchBuilder.build());
        String flowId = "Rule"+nodeId+nodeconceID+nodeconceID+vlanId;
        flowBuilder.setId(new FlowId(flowId));
        FlowKey key = new FlowKey(new FlowId(flowId));
        flowBuilder.setBarrier(true);
        flowBuilder.setTableId((short) 0);  //Table 0
        flowBuilder.setKey(key);
        flowBuilder.setPriority(32768);
        flowBuilder.setFlowName(flowId);
        flowBuilder.setHardTimeout(0);
        flowBuilder.setIdleTimeout(0);
        flowBuilder.setInstructions(isb.setInstruction(instructions).build());
        InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
                .child(Node.class, new NodeKey(nodeId))
                .augmentation(FlowCapableNode.class)
                .child(Table.class, new TableKey(flowBuilder.getTableId()))
                .child(Flow.class, flowBuilder.getKey())
                .build();
        GenericTransactionUtils.writeData(DtuMain.dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);

    }

    /**
    *
    *@param nodeId Switch
    *@param nodeconceID ingress port
    *@param ipDst destination ip
    *@param vlanId path identifier
    *@param nodeconceout egrees port
    */
    public static void pushARP(NodeId nodeId, NodeConnectorId nodeconceID,String ipDst,String vlanId, NodeConnectorId nodeconceout) {
        //Creating match object
        MatchBuilder matchBuilder = new MatchBuilder();
        FlowDtuUtils.createInPortMatch(matchBuilder, nodeconceID);
        createEtherTypeMatch(matchBuilder, new EtherType(0x0806L));
        createArpDstIpv4Match(matchBuilder, iPv4PrefixFromIPv4Address(ipDst));
        // Instructions List Stores Individual Instructions
        InstructionsBuilder isb = new InstructionsBuilder();
        List<Instruction> instructions = Lists.newArrayList();
        InstructionBuilder ib = new InstructionBuilder();
        ApplyActionsBuilder aab = new ApplyActionsBuilder();
        ActionBuilder ab = new ActionBuilder();
        List<Action> actionList = Lists.newArrayList();
        /* First we push vlan header */
        PushVlanActionBuilder vlan = new PushVlanActionBuilder();
        vlan.setEthernetType(0x8100);
        ab.setAction(new PushVlanActionCaseBuilder().setPushVlanAction(vlan.build()).build());
        ab.setOrder(0);
        actionList.add(ab.build());
        /* Then we set vlan id value as vlanId */
        SetVlanIdActionBuilder vl = new SetVlanIdActionBuilder();
        vl.setVlanId(new VlanId(Integer.parseInt(vlanId)));
        ab = new ActionBuilder();
        ab.setAction(new SetVlanIdActionCaseBuilder().setSetVlanIdAction(vl.build()).build());
        ab.setOrder(1);
        actionList.add(ab.build());
        OutputActionBuilder output = new OutputActionBuilder();
        output.setOutputNodeConnector(nodeconceout);
        output.setMaxLength(65535); //Send full packet and No buffer
        ab.setAction(new OutputActionCaseBuilder().setOutputAction(output.build()).build());
        ab.setOrder(2);
        ab.setKey(new ActionKey(2));
        actionList.add(ab.build());
        // Create Apply Actions Instruction
        aab.setAction(actionList);
        ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
        ib.setOrder(1);
        ib.setKey(new InstructionKey(1));
        instructions.add(ib.build());
        // Create Flow
        FlowBuilder flowBuilder = new FlowBuilder();
        flowBuilder.setMatch(matchBuilder.build());
        String flowId = "ARP"+nodeId+nodeconceID+vlanId;
        flowBuilder.setId(new FlowId(flowId));
        FlowKey key = new FlowKey(new FlowId(flowId));
        flowBuilder.setBarrier(true);
        flowBuilder.setTableId((short) 0);  //Table 0
        flowBuilder.setKey(key);
        flowBuilder.setPriority(32768);
        flowBuilder.setFlowName(flowId);
        flowBuilder.setHardTimeout(0);
        flowBuilder.setIdleTimeout(0);
        flowBuilder.setInstructions(isb.setInstruction(instructions).build());

        InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
                .child(Node.class, new NodeKey(nodeId))
                .augmentation(FlowCapableNode.class)
                .child(Table.class, new TableKey(flowBuilder.getTableId()))
                .child(Flow.class, flowBuilder.getKey())
                .build();
        GenericTransactionUtils.writeData(DtuMain.dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);

    }

    /**
    * This method install a flow rule in a source switch. The tag is push in a packet before forward to next switch.
    *Match fields are port and path identifier.
    *Action is pop the tag and output port.
    *@param nodeId Switch
    *@param nodeconceID ingress port
    *@param ipDst destination ip
    *@param vlanId path identifier
    *@param nodeconceout egrees port
    */
    public static void pushtag(NodeId nodeId, NodeConnectorId nodeconceID,String ipDst,String vlanId, NodeConnectorId nodeconceout) {
        //Creating match object
        MatchBuilder matchBuilder = new MatchBuilder();
        // Instructions List Stores Individual Instructions
        InstructionsBuilder isb = new InstructionsBuilder();
        List<Instruction> instructions = Lists.newArrayList();
        InstructionBuilder ib = new InstructionBuilder();
        ApplyActionsBuilder aab = new ApplyActionsBuilder();
        ActionBuilder ab = new ActionBuilder();
        List<Action> actionList = Lists.newArrayList();
        /* First we push vlan header */
        PushVlanActionBuilder vlan = new PushVlanActionBuilder();
        vlan.setEthernetType(0x8100);
        ab.setAction(new PushVlanActionCaseBuilder().setPushVlanAction(vlan.build()).build());
        ab.setOrder(0);
        actionList.add(ab.build());
        /* Then we set vlan id value as vlanId */
        SetVlanIdActionBuilder vl = new SetVlanIdActionBuilder();
        vl.setVlanId(new VlanId(Integer.parseInt(vlanId)));
        ab = new ActionBuilder();
        ab.setAction(new SetVlanIdActionCaseBuilder().setSetVlanIdAction(vl.build()).build());
        ab.setOrder(1);
        actionList.add(ab.build());
        OutputActionBuilder output = new OutputActionBuilder();
        output.setOutputNodeConnector(nodeconceout);
        output.setMaxLength(65535); //Send full packet and No buffer
        ab.setAction(new OutputActionCaseBuilder().setOutputAction(output.build()).build());
        ab.setOrder(2);
        ab.setKey(new ActionKey(2));
        actionList.add(ab.build());
        // Create Apply Actions Instruction
        aab.setAction(actionList);
        ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
        ib.setOrder(1);
        ib.setKey(new InstructionKey(1));
        instructions.add(ib.build());
        // Create Flow
        FlowBuilder flowBuilder = new FlowBuilder();
        flowBuilder.setMatch(matchBuilder.build());
        String flowId = "Push"+nodeId+nodeconceID+vlanId;
        flowBuilder.setId(new FlowId(flowId));
        FlowKey key = new FlowKey(new FlowId(flowId));
        flowBuilder.setBarrier(true);
        flowBuilder.setTableId((short) 0);  //Table 0
        flowBuilder.setKey(key);
        flowBuilder.setPriority(32768);
        flowBuilder.setFlowName(flowId);
        flowBuilder.setHardTimeout(0);
        flowBuilder.setIdleTimeout(0);
        flowBuilder.setInstructions(isb.setInstruction(instructions).build());
        InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
                .child(Node.class, new NodeKey(nodeId))
                .augmentation(FlowCapableNode.class)
                .child(Table.class, new TableKey(flowBuilder.getTableId()))
                .child(Flow.class, flowBuilder.getKey())
                .build();
        GenericTransactionUtils.writeData(DtuMain.dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
    }

    ////////////////////////////////////////////////
    /////////////////////END VLAN///////////////
    ///////////////////////////////////////////////////



    /////////////////////////////////////////////////////////
    /////////////////////INIT CONTROLLER//////////////////////////////////////
    //////////////////////////////////////////////////////////////


    /**
    * This method install a flow in a switch. The flow rule forward all the arp packet to the controller.
    *Match fields are port, IP address and type of packet.
    *Action forward packet to the controller.
    *@param nodeId Switch
    *@param nodeconceID ingress port
    *@param ipDst destination ip
    */
    public static void createFlowsARPSwitchToController(NodeId nodeid,NodeConnectorId nodeconceID,String ipDst){
        //Creating match object
        MatchBuilder matchBuilder = new MatchBuilder();
        createInPortMatch(matchBuilder, nodeconceID);
        createEtherTypeMatch(matchBuilder, new EtherType(0x0806L));
        createArpDstIpv4Match(matchBuilder, iPv4PrefixFromIPv4Address(ipDst));
        // Instructions List Stores Individual Instructions
        InstructionsBuilder isb = new InstructionsBuilder();
        List<Instruction> instructions = Lists.newArrayList();
        InstructionBuilder ib = new InstructionBuilder();
        ApplyActionsBuilder aab = new ApplyActionsBuilder();
        ActionBuilder ab = new ActionBuilder();
        List<Action> actionList = Lists.newArrayList();
        // Set output action
        OutputActionBuilder output = new OutputActionBuilder();
        output.setMaxLength(65535); //Send full packet and No buffer
        output.setOutputNodeConnector(new Uri(OutputPortValues.CONTROLLER.toString()));
        ab.setAction(new OutputActionCaseBuilder().setOutputAction(output.build()).build());
        ab.setOrder(0);
        ab.setKey(new ActionKey(0));
        actionList.add(ab.build());
        // Create Apply Actions Instruction
        aab.setAction(actionList);
        ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
        ib.setOrder(0);
        ib.setKey(new InstructionKey(0));
        instructions.add(ib.build());
        // Create Flow
        FlowBuilder flowBuilder = new FlowBuilder();
        flowBuilder.setMatch(matchBuilder.build());
        String flowId = "Output CONTROLLER"+nodeid.toString()+ipDst;
        flowBuilder.setId(new FlowId(flowId));
        FlowKey key = new FlowKey(new FlowId(flowId));
        flowBuilder.setBarrier(true);
        flowBuilder.setTableId((short) 0);
        flowBuilder.setKey(key);
        flowBuilder.setPriority(32768);
        flowBuilder.setFlowName(flowId);
        flowBuilder.setHardTimeout(0);
        flowBuilder.setIdleTimeout(0);
        flowBuilder.setInstructions(isb.setInstruction(instructions).build());
        InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
                .child(Node.class, new NodeKey(nodeid))
                .augmentation(FlowCapableNode.class)
                .child(Table.class, new TableKey(flowBuilder.getTableId()))
                .child(Flow.class, flowBuilder.getKey())
                .build();
        GenericTransactionUtils.writeData(DtuMain.dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
    }

    /**
    * This method install a flow rule in a switch. The flow rule forward all packets to the controller.
    *Match all.
    *Action forward packet to the controller.
    *@param nodeId Switch
    */
    public static void createFlowsFromSwitchToController(NodeId nodeid){
        //Creating match object
        MatchBuilder matchBuilder = new MatchBuilder();
        // Instructions List Stores Individual Instructions
        InstructionsBuilder isb = new InstructionsBuilder();
        List<Instruction> instructions = Lists.newArrayList();
        InstructionBuilder ib = new InstructionBuilder();
        ApplyActionsBuilder aab = new ApplyActionsBuilder();
        ActionBuilder ab = new ActionBuilder();
        List<Action> actionList = Lists.newArrayList();
        // Set output action
        OutputActionBuilder output = new OutputActionBuilder();
        output.setMaxLength(65535); //Send full packet and No buffer
        output.setOutputNodeConnector(new Uri(OutputPortValues.CONTROLLER.toString()));
        ab.setAction(new OutputActionCaseBuilder().setOutputAction(output.build()).build());
        ab.setOrder(0);
        ab.setKey(new ActionKey(0));
        actionList.add(ab.build());
        // Create Apply Actions Instruction
        aab.setAction(actionList);
        ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
        ib.setOrder(0);
        ib.setKey(new InstructionKey(0));
        instructions.add(ib.build());
        // Create Flow
        FlowBuilder flowBuilder = new FlowBuilder();
        flowBuilder.setMatch(matchBuilder.build());
        String flowId = "Output CONTROLLER"+nodeid.toString();
        flowBuilder.setId(new FlowId(flowId));
        FlowKey key = new FlowKey(new FlowId(flowId));
        flowBuilder.setBarrier(true);
        flowBuilder.setTableId((short) 0);
        flowBuilder.setKey(key);
        flowBuilder.setPriority(32768);
        flowBuilder.setFlowName(flowId);
        flowBuilder.setHardTimeout(0);
        flowBuilder.setIdleTimeout(0);
        flowBuilder.setInstructions(isb.setInstruction(instructions).build());
        InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
                .child(Node.class, new NodeKey(nodeid))
                .augmentation(FlowCapableNode.class)
                .child(Table.class, new TableKey(flowBuilder.getTableId()))
                .child(Flow.class, flowBuilder.getKey())
                .build();
        GenericTransactionUtils.writeData(DtuMain.dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
    }



    ////////////////////////////////////////////////
    /////////////////////END CONTROLLER///////////////
    ///////////////////////////////////////////////////

    /**
    * This method help to create the match field for input ports.
    *@param matchBuilder
    *@param ncId
    *@return MatchBuilder
    */
    public static  MatchBuilder createInPortMatch(MatchBuilder matchBuilder, NodeConnectorId ncId) {
        matchBuilder.setInPort(ncId);
        return matchBuilder;
    }

    /**
    * This method help to create the match field for MPLS identifiers.
    *@param matchBuilder
    *@param dstip
    *@return MatchBuilder
    */
    public static MatchBuilder createMplsLabelBosMatch(Long label, boolean bos) {
        MatchBuilder matchBuilder = new MatchBuilder();
        EthernetMatchBuilder eth = new EthernetMatchBuilder();
        EthernetTypeBuilder ethTypeBuilder = new EthernetTypeBuilder();
        ethTypeBuilder.setType(new EtherType(0x8847L));
        eth.setEthernetType(ethTypeBuilder.build());
        matchBuilder.setEthernetMatch(eth.build());

        ProtocolMatchFieldsBuilder matchFieldsBuilder = new ProtocolMatchFieldsBuilder()
                .setMplsLabel(label)
                .setMplsBos((short) (1));
        matchBuilder.setProtocolMatchFields(matchFieldsBuilder.build());
        return matchBuilder;
    }

    /**
    * This method help to create the match field for IPv4.
    *@param matchBuilder
    *@param dstip
    *@return MatchBuilder
    */
    public static MatchBuilder createDstL3IPv4Match(MatchBuilder matchBuilder, Ipv4Prefix dstip) {
        EthernetMatchBuilder eth = new EthernetMatchBuilder();
        EthernetTypeBuilder ethTypeBuilder = new EthernetTypeBuilder();
        ethTypeBuilder.setType(new EtherType(0x0800L));
        eth.setEthernetType(ethTypeBuilder.build());
        matchBuilder.setEthernetMatch(eth.build());
        Ipv4MatchBuilder ipv4match = new Ipv4MatchBuilder();
        ipv4match.setIpv4Destination(dstip);
        matchBuilder.setLayer3Match(ipv4match.build());
        return matchBuilder;

    }

    /**
    * This method help to create the match field for IPv4.
    *@param matchBuilder
    *@param dstip
    *@return MatchBuilder
    */
    public static MatchBuilder createArpDstIpv4Match(MatchBuilder matchBuilder, Ipv4Prefix dstip) {
        ArpMatchBuilder arpDstMatch = new ArpMatchBuilder();
        arpDstMatch.setArpTargetTransportAddress(dstip);
        matchBuilder.setLayer3Match(arpDstMatch.build());
        return matchBuilder;
    }

    /**
    * This method return an Ipv4Prefix
    *@param ipv4AddressString
    *@return Ipv4Prefix
    */
    public static Ipv4Prefix iPv4PrefixFromIPv4Address(String ipv4AddressString) {
        return new Ipv4Prefix(ipv4AddressString + "/32");
    }

    /**
    * This method help to create the match field for ethernet.
    *@param matchBuilder
    *@param etherType
    *@return MatchBuilder
    */
    public static MatchBuilder createEtherTypeMatch(MatchBuilder matchBuilder, EtherType etherType) {
        EthernetMatchBuilder ethernetMatch = new EthernetMatchBuilder();
        EthernetTypeBuilder ethTypeBuilder = new EthernetTypeBuilder();
        ethTypeBuilder.setType(new EtherType(etherType));
        ethernetMatch.setEthernetType(ethTypeBuilder.build());
        matchBuilder.setEthernetMatch(ethernetMatch.build());
        return matchBuilder;
    }

    /**
    * This method help to create the match field for ethernet.
    *@param matchBuilder
    *@param etherType
    *@return MatchBuilder
    */
    public static MatchBuilder createEtherTypeMatc(MatchBuilder matchBuilder, EtherType etherType) {
        EthernetMatchBuilder ethernetMatch = new EthernetMatchBuilder();
        EthernetTypeBuilder ethTypeBuilder = new EthernetTypeBuilder();
        ethTypeBuilder.setType(new EtherType(etherType));
        ethernetMatch.setEthernetType(ethTypeBuilder.build());
        matchBuilder.setEthernetMatch(ethernetMatch.build());
        return matchBuilder;
    }

    /**
    * This method help to create the match field for destination mac address.
    *@param matchBuilder
    *@param dMacAddr
    *@param mask
    *@return MatchBuilder
    */
    public static MatchBuilder createDestEthMatch(MatchBuilder matchBuilder, MacAddress dMacAddr, MacAddress mask) {
        EthernetMatchBuilder ethernetMatch = new EthernetMatchBuilder();
        EthernetDestinationBuilder ethDestinationBuilder = new EthernetDestinationBuilder();
        ethDestinationBuilder.setAddress(dMacAddr);
        if (mask != null) {
            ethDestinationBuilder.setMask(mask);
        }
        ethernetMatch.setEthernetDestination(ethDestinationBuilder.build());
        matchBuilder.setEthernetMatch(ethernetMatch.build());
        return matchBuilder;
    }

    /**
    * This method help to create the match field for VLANids.
    *@param matchBuilder
    *@param label
    *@param bos
    *@return MatchBuilder
    */
    public static MatchBuilder createVlanIdMatch(MatchBuilder matchBuilder, VlanId vlanId, boolean present) {
        VlanMatchBuilder vlanMatchBuilder = new VlanMatchBuilder();
        VlanIdBuilder vlanIdBuilder = new VlanIdBuilder();
        vlanIdBuilder.setVlanId(new VlanId(vlanId));
        vlanIdBuilder.setVlanIdPresent(present);
        vlanMatchBuilder.setVlanId(vlanIdBuilder.build());
        matchBuilder.setVlanMatch(vlanMatchBuilder.build());

        return matchBuilder;
    }

}
