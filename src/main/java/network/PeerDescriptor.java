package network;

import lib.Address;
import lib.Com;
import lib.Connection;
import lib.ModuleContainer;

import java.util.List;
import java.util.Map;

/**
 * Class to describe the peer. It should contain the addresses of peer process, the availible modules, their instances and their connections.
 *
 *
 */
public class PeerDescriptor {
    // address of the {@link Com}
    Address address;
    Map<Class, List<ModuleContainer>> moduleDescriptionByCategory;
    Map<String,List<Connection>> connections;

    public PeerDescriptor(Map<Class, List<ModuleContainer>> moduleDescriptionByCategory, Address peerAddress, Com com) {
        this.address= peerAddress;
        this.moduleDescriptionByCategory = moduleDescriptionByCategory;
        this.connections = com.getConnections();
    }
}
