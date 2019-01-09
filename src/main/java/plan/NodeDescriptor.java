package plan;

import lib.Address;
import lib.Connection;
import network.GraphNode;

import java.util.Objects;


public class NodeDescriptor<A extends Address> extends GraphNode{
    boolean multiThread;
    String processId;
    String resourcepath;

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public void setResourcepath(String resourcepath) {
        this.resourcepath = resourcepath;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public void setModuleClassName(String moduleClassName) {
        this.moduleClassName = moduleClassName;
    }

    //public void setPeerid(String peerid) {
    //    this.peerid = peerid;
    //}

    public void setLocation(A location) {
        this.location = location;
    }

    public String[] getArgs() {
        return args;
    }

    String[] args;
    String moduleClassName;
    //String peerid;

    public String getProcessId() {
        return processId;
    }

    public String getResourcepath() {
        return resourcepath;
    }

    public String getModuleClassName() {
        return moduleClassName;
    }

    //public String getPeerid() {
    //    return peerid;
   // }

    public A getLocation() {
        return location;
    }

    A location;

    public A getHostingPeerLocation() {
        return hostingPeerLocation;
    }

    public void setHostingPeerLocation(A hostingPeerLocation) {
        this.hostingPeerLocation = hostingPeerLocation;
    }

    A hostingPeerLocation;

    public NodeDescriptor() {
    }

    public NodeDescriptor(boolean multiThread, String processId, String resourcepath, String[] args, String moduleClassName, A location) {
        this.multiThread = multiThread;
        this.processId = processId;
        this.resourcepath = resourcepath;
        this.args = args;
        this.moduleClassName = moduleClassName;
        this.location = location;
    }

    public NodeDescriptor(NodeDescriptor<A> old) {
       this(old.multiThread,old.processId,old.resourcepath,old.args,old.moduleClassName,old.location);


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeDescriptor<?> that = (NodeDescriptor<?>) o;
        return Objects.equals(processId, that.processId) &&
                Objects.equals(moduleClassName, that.moduleClassName) &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {

        return Objects.hash(processId, moduleClassName, location);
    }
}
