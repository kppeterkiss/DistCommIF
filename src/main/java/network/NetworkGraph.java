package network;

import java.util.List;

public class NetworkGraph extends SparseGraph<PeerDescriptor,NetworkEdge>{
    public NetworkGraph(List<NetworkEdge> localGraph) {
        super(localGraph);
    }
}
