package network;

public class NetworkEdge extends EdgeDescriptor<PeerDescriptor>{
    public NetworkEdge(long bandwidth, long delay, PeerDescriptor[] nodes) {
        super(bandwidth, delay, nodes);
    }
}
