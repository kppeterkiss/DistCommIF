package network;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


// each nodes knows the  direct connections
// sends a message to direct connectikons to request a mapping-> answer OK -delay time -> build local graph
//

// stores all the path in the graph going out from the acual one
public class NetworkGraph {

    List<EdgeDescriptor> edgeList;
   // NodeDescriptor root;
    //List<NodeDescriptor> nodes = new LinkedList<>();
   // Map<NodeDescriptor,Map<NodeDescriptor,EdgeDescriptor>> edges = new HashMap<>();
    // we add connection to the given node since the parts come from the neighbouring ones
    //rather we connect the arrived subgraphs with the recent node
    public NetworkGraph(List<EdgeDescriptor> localGraph){
        this.edgeList = localGraph;
    }

   /* public NodeDescriptor getRoot(){
        //return this.nodes.get(0);
    }*/
   public void addEdge(EdgeDescriptor e){
       this.edgeList.add(e);
   }

    public void addSubGraph(NetworkGraph toAdd){
      this.edgeList.addAll(toAdd.edgeList);
            /* for(Map.Entry<NodeDescriptor, EdgeDescriptor> edges : neighbpours.getValue().entrySet()){
                 if(!this.edges.containsKey(edges.getKey()))
                     this.edges.
             }*/
         }







}