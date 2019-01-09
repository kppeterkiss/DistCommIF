package plan.algorithms;

import network.NetworkEdge;
import network.NetworkGraph;
import network.PeerDescriptor;
import plan.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class StarLayout implements ModulePlacement<CallGraph, NetworkGraph>{

    // TODO: 2019. 01. 03. adjust capacities 
    @Override
    public CallGraph apply(CallGraph cg, NetworkGraph ng) {
        CallGraph routedcg = new CallGraph(cg.getEdgeList(),cg.getRoot().getProcessId(),cg.getTarget().getProcessId());
        routedcg.setEdgeList(new LinkedList<>());
       // cg.getRoot().setLocation(ng.getRoot().getAddress());
        ng.getRoot().setCapacity(ng.getRoot().getCapacity()-cg.getRoot().getCapacity());
        //the output will be processed at the same node as where the task has been initiated.
        cg.getTarget().setLocation(ng.getRoot().getAddress());
        //iterate over

        //active nofr 
        NodeDescriptor activeNode = cg.getRoot();
        PeerDescriptor activePeer = ng.getRoot();
        routedcg.getRoot().setHostingPeerLocation(activePeer.getAddress());
        routedcg.getRoot().setLocation(activePeer.resolveProcessAddress(activeNode.getProcessId()));
        Stack<NodeDescriptor> processingStack = new Stack<>();
        List<ExecutionEdge> done = new LinkedList<>();

        while(activeNode!=null) {

            // we want to place the nodes connected to the active one
            List<ExecutionEdge> edgesFromActiveNode = cg.getOutboundEdges(activeNode);
            //edgesFromActiveNode.forEach(e->processingStack.push(e.getEndNode()));
            // nodes e.g. task to be executed after the active
            for (ExecutionEdge e : edgesFromActiveNode) {
                if(done.contains(e))
                    continue;

                //get the node to place that is next to the active
                NodeDescriptor nodeToPlace = e.getEndNode();
                processingStack.push(nodeToPlace);
                List<NetworkEdge> networkEdgesFromActivePeer = ng.getOutboundEdges(activePeer);
                //if the node to place can be deployed paralelly
                if (e.getRelation().equals(RelationMultiplicity.ONE_TO_MANY)){
                    //iterating over peers one hop from the active, and initiate a node on each neighbouring peer
                    for(NetworkEdge ne: networkEdgesFromActivePeer ){
                        //deep copy of the node to deploy, there can we assign  a location
                        NodeDescriptor newNodeDEsc = new NodeDescriptor(nodeToPlace);
                       // SparkHTTPServlet.HttpAddress peerAddress = (SparkHTTPServlet.HttpAddress)ne.getEndNode().getAddress();
                       // SparkHTTPServlet.HttpAddress addressOfNode = new SparkHTTPServlet.HttpAddress(peerAddress.getPeerId(),newNodeDEsc.getProcessId(),peerAddress.getUrl(),peerAddress.getPort());
                        newNodeDEsc.setHostingPeerLocation(ne.getEndNode().getAddress());
                        newNodeDEsc.setLocation(ne.getEndNode().resolveProcessAddress(newNodeDEsc.getProcessId()));
                        // 2 times for bidirectional
                        routedcg.addEdge(new ExecutionEdge((e.getBandwidth() / (long) networkEdgesFromActivePeer.size()),ne.getDelay(),new NodeDescriptor[]{activeNode,newNodeDEsc},RelationMultiplicity.ONE_TO_ONE));
                        routedcg.addEdge(new ExecutionEdge((e.getBandwidth() / (long) networkEdgesFromActivePeer.size()),ne.getDelay(),new NodeDescriptor[]{newNodeDEsc,activeNode},RelationMultiplicity.ONE_TO_ONE));
                    }
                    if(networkEdgesFromActivePeer.size()==0) {
                        NodeDescriptor newNodeDEsc = new NodeDescriptor(nodeToPlace);
                        newNodeDEsc.setHostingPeerLocation(activePeer.getAddress());
                        newNodeDEsc.setLocation(activePeer.resolveProcessAddress(newNodeDEsc.getProcessId()));

                        routedcg.addEdge(new ExecutionEdge(1000,0l,new NodeDescriptor[]{activeNode,newNodeDEsc},RelationMultiplicity.ONE_TO_ONE));
                        routedcg.addEdge(new ExecutionEdge(1000,0l,new NodeDescriptor[]{newNodeDEsc,activeNode},RelationMultiplicity.ONE_TO_ONE));
                    }
                    done.add(e);

                }
                else
                    throw new NotImplementedException();
                
            }
            if(!processingStack.empty())
                activeNode = processingStack.pop();
            else
                activeNode = null;
        }
        return routedcg;
    }
}
