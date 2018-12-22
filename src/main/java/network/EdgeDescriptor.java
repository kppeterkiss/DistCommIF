package network;

import java.util.Arrays;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeDescriptor that = (EdgeDescriptor) o;
        return this.nodes[0].equals(that.nodes[0]) && this.nodes[1].equals(that.nodes[1]);
        //return Arrays.equals(nodes, that.nodes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(nodes);
    }
}
