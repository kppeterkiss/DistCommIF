package network;

public class EdgeDescriptor {
    long bandwidth;
    long delay;

    PeerDescriptor[] nodes = new PeerDescriptor[2];
    public PeerDescriptor getReacheableNodeFrom(PeerDescriptor nd){
        if(nodes[0].equals(nd))
            return nodes[1];
        return nodes[0];
    }

    public EdgeDescriptor(long bandwidth, long delay, PeerDescriptor[] nodes) {
        this.bandwidth = bandwidth;
        this.delay = delay;
        this.nodes = nodes;
    }
}
