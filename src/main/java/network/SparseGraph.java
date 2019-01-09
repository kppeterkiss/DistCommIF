package network;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


// each nodes knows the  direct connections
// sends a message to direct connectikons to request a mapping-> answer OK -delay time -> build local graph
//

// stores all the path in the graph going out from the acual one
public class SparseGraph<N extends GraphNode,E extends EdgeDescriptor<N>> {

    public N getRoot() {
        return root;
    }

    public void setRoot(N root) {
        this.root = root;
    }

    //
    protected N root;

    public void setEdgeList(List<E> edgeList) {
        this.edgeList = edgeList;
    }



    List<E> edgeList;
   // PeerDescriptor root;
    //List<PeerDescriptor> nodes = new LinkedList<>();
   // Map<PeerDescriptor,Map<PeerDescriptor,EdgeDescriptor>> edges = new HashMap<>();
    // we add connection to the given node since the parts come from the neighbouring ones
    //rather we connect the arrived subgraphs with the recent node
    public SparseGraph(List<E> localGraph){
        this.edgeList = localGraph;
    }

   /* public PeerDescriptor getRoot(){
        //return this.nodes.get(0);
    }*/
   public void addEdge(E e){
       if (this.edgeList.contains(e))
           //to update the volatile values like delay etc
           this.edgeList.remove(e);
       this.edgeList.add(e);
   }

   public List<E> getEdgeList(){
       return this.edgeList;
   }

    public void addSubGraph(SparseGraph<N,E> toAdd){
      for(E ed : toAdd.edgeList) {
          addEdge(ed);
      }


      //this.edgeList.addAll(toAdd.edgeList);
            /* for(Map.Entry<PeerDescriptor, EdgeDescriptor> edges : neighbpours.getValue().entrySet()){
                 if(!this.edges.containsKey(edges.getKey()))
                     this.edges.
             }*/
         }

    public List<E> getOutboundEdges(N node){
       return this.edgeList.stream().filter(e -> e.onNode(node)).collect(Collectors.toList());
    }

    public List<N> getNeighbouringNodes(N node){
       return getOutboundEdges(node).stream().map(e->e.getEndNode()).collect(Collectors.toList());
    }







}
