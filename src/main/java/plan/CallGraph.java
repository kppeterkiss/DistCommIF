package plan;

import network.SparseGraph;

import java.util.List;

public class CallGraph extends SparseGraph<NodeDescriptor,ExecutionEdge> {

    //source analogou with root
    NodeDescriptor /*source,*/target;

    /*public NodeDescriptor getSource() {
        return source;
    }*/

    public NodeDescriptor getTarget() {
        return target;
    }

    public CallGraph(List<ExecutionEdge> localGraph, String sourceModuleName, String targetModuleName) {
        super(localGraph);
        root = localGraph.stream().filter(e->e.getFirstNode().getProcessId().equals(sourceModuleName)).findFirst().map(e->e.getFirstNode()).get();
        if(!targetModuleName.equals(sourceModuleName))
            target = localGraph.stream().filter(e->e.getEndNode().getProcessId().equals(targetModuleName)).findFirst().map(e->e.getEndNode()).get();
        else
            target = root;
    }
}
