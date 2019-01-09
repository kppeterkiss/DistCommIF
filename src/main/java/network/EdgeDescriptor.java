package network;

import java.util.Arrays;

public class EdgeDescriptor<D> {
    long bandwidth;
    long delay;

    public long getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(long bandwidth) {
        this.bandwidth = bandwidth;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public D[] getNodes() {
        return nodes;
    }

    public void setNodes(D[] nodes) {
        this.nodes = nodes;
    }

    D[] nodes;// = new D[2];
    public D getReacheableNodeFrom(PeerDescriptor nd){
        if(nodes[0].equals(nd))
            return nodes[1];
        return nodes[0];
    }

    public EdgeDescriptor(long bandwidth, long delay, D[] nodes) {
        this.bandwidth = bandwidth;
        this.delay = delay;
        this.nodes = nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeDescriptor<?> that = (EdgeDescriptor<?>) o;
        if(that.nodes[0].getClass()!=this.nodes[0].getClass())
            return false;
        return this.nodes[0].equals(that.nodes[0]) && this.nodes[1].equals(that.nodes[1]);
        //return Arrays.equals(nodes, that.nodes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(nodes);
    }

    public D getFirstNode(){
        return nodes[0];
    }
    public D getEndNode(){
        return nodes[1];
    }

    public boolean onNode(D node){
        return nodes[0].equals(node) || nodes[1].equals(node);
    }
}
