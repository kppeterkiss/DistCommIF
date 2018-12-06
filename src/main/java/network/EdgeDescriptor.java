package network;

public class EdgeDescriptor {
    long bandwidth;
    long delay;

    NodeDescriptor[] nodes = new NodeDescriptor[2];
    public NodeDescriptor getReacheableNodeFrom(NodeDescriptor nd){
        if(nodes[0].equals(nd))
            return nodes[1];
        return nodes[0];
    }

    public EdgeDescriptor(long bandwidth, long delay, NodeDescriptor[] nodes) {
        this.bandwidth = bandwidth;
        this.delay = delay;
        this.nodes = nodes;
    }
}
