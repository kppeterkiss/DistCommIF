package network;

import lib.Address;
import lib.Com;
import lib.Connection;
import lib.ModuleContainer;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class to describe the peer. It should contain the addresses of peer process, the availible modules, their instances and their connections.
 *
 *
 */
public class PeerDescriptor<A extends Address,C extends Connection> {
    // address of the {@link Com}
    A address;
    Map<String, List<ModuleContainer>> moduleDescriptionByCategory;
    Map<String,List<C>> connections;

    public PeerDescriptor(Map<String, List<ModuleContainer>> moduleDescriptionByCategory, A peerAddress, Com<C,A> com) {
        this.address= peerAddress;
        this.moduleDescriptionByCategory = moduleDescriptionByCategory;

        this.connections =com.getConnections();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeerDescriptor<A,C> that = (PeerDescriptor<A,C>) o;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(address);
    }
}
