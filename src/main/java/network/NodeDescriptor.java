package network;

import lib.Address;
import lib.ModuleContainer;

import java.util.List;
import java.util.Map;

public class NodeDescriptor {
    Address address;
    Map<Class, List<ModuleContainer>> moduleDescriptionByCategory;

    public NodeDescriptor(Map<Class, List<ModuleContainer>> moduleDescriptionByCategory, Address peerAddress) {
        this.address= peerAddress;
        this.moduleDescriptionByCategory = moduleDescriptionByCategory;
    }
}
