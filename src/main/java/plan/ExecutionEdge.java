package plan;

import network.EdgeDescriptor;

public class ExecutionEdge extends EdgeDescriptor<NodeDescriptor>{

    public void setRelation(RelationMultiplicity relation) {
        this.relation = relation;
    }

    public RelationMultiplicity getRelation() {
        return relation;
    }

    RelationMultiplicity relation;

    public ExecutionEdge(long bandwidth, long delay, NodeDescriptor[] nodes, RelationMultiplicity m) {
        super(bandwidth, delay, nodes);
        this.relation = m;
    }
   // public Enum Direction {}

}
