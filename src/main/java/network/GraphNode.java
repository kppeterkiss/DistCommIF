package network;

/**
 * General graph node
 * capacity describes the the provided or required computational capacity.
 */
public class GraphNode {
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    int cost;
    int capacity;
}
