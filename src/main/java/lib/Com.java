package lib;

import network.NetworkGraph;
import network.NodeDescriptor;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Com extends Thread{

    Address peerAddress;

    public int getDefaultPort() {
        return defaultPort;
    }

    public void setDefaultPort(int defaultPort) {
        this.defaultPort = defaultPort;
    }

    public int getDefaultSecondaryPort() {
        return defaultSecondaryPort;
    }

    public void setDefaultSecondaryPort(int defaultSecondaryPort) {
        this.defaultSecondaryPort = defaultSecondaryPort;
    }

    // must be not blocked by firewall
    protected int defaultPort = 8901;
    protected int defaultSecondaryPort = 8902;

    public String getPeerId() {
        return peerId;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

    protected String peerId;

    Map<Class,String> categorynames = new HashMap<>();

    Map<Class,List<ModuleContainer>> moduleDescriptionByCategory = new HashMap<>();


    {
        categorynames.put(Coordinator.class,"coordinator");
        categorynames.put(Slave.class,"slave");
        categorynames.put(Node.class,"slave");
    }

    /**
     * Method for instantiating a module of a distributed software.
     * @param moduleName Name of the main class of the module, that should be an implementation of the abstract classes that extends the {@link Node} suberclass.
     * @param arguments
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public String  launchModule(String moduleName, String[]  arguments) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        for(Map.Entry<Class,List<ModuleContainer>> e : this.moduleDescriptionByCategory.entrySet()){
            for(ModuleContainer md : e.getValue())
                if(md.classname.equals(moduleName)) {
                    Node n = md.instantiate();
                    n.setCom(this);
                    n.setArguments(arguments);
                    n.start();
                    return n.getName();
                }

        }
        return null;
    }

    public abstract String launchRemoteModule(Connection c, String moduleName, String[] arguments);

    public abstract String killRemoteModule(Connection c);


    public Node getModuleReferenceByName(String name){
        for(Map.Entry<Class,List<ModuleContainer>> e : this.moduleDescriptionByCategory.entrySet()) {
            for (ModuleContainer md : e.getValue()) {
                Node n = md.threadByName.get(name);
                if (n != null)
                    return n;
            }
        }
        return null;
    }

    /**
     * convenience method to get the connection information of a given process.
     * @param id
     * @return
     */
    public abstract Connection getProcessConnectionDescriptor(String id);

    public abstract Connection calculateRemoteProcessConnectionDescriptor(String id,Connection c) ;

    /**
     * Ask a remote process to establish a connection to another remote process.
     * @param descriptor the descriptor of the connection to the remote process.
     * @param name ???
     * @param descriptor2 The descriptor of the remote process to which the connection is required to build
     * @param connDEscriptor The type of the connection to be established.
     * @return success
     */
    public abstract boolean addConnectionToRemote(Connection descriptor, String name, Connection descriptor2, ConnectionType connDEscriptor);

    /**
     * Ask a peer top instantiate a remote process.
     * @param descriptor The connectrion information of the remote peer
     * @param name The name of the module to start.
     * @param args The args array to be passed to the process at startup,
     * @return success
     */
    public abstract  boolean startRemoteProcess(Connection descriptor, String name, String[] args);

    /*
    public int getFreePort() throws IOException {
        ServerSocket s = new ServerSocket(0);
        System.out.println("listening on port: " + s.getLocalPort());
        return s.getLocalPort();
    }*/
    public void killModule(String moduleName){
        Node n = getModuleReferenceByName(moduleName);
        if(n!=null)
            n.interrupt();


    }



    //List<Node> services = new LinkedList<>();

    /**
     *
     * @param moduleStoragePath
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void discoverServices(String moduleStoragePath) throws IOException, ClassNotFoundException {
        for(Map.Entry<Class,String> e: categorynames.entrySet()) {
            String categoryname = e.getValue();
            Class categoryClass = e.getKey();
           if(  !new File(moduleStoragePath + File.separator + e.getValue()).exists())
               continue;

            Map<Class, String> res = Utils.findAllMatchingTypes(e.getKey(), moduleStoragePath + File.separator + e.getValue());
            for(Map.Entry<Class,String> e1 : res.entrySet()) {
                if (!moduleDescriptionByCategory.containsKey(categoryClass))
                    moduleDescriptionByCategory.put(categoryClass, new LinkedList<ModuleContainer>());
                moduleDescriptionByCategory.get(categoryClass).add(new ModuleContainer(e1.getKey().getName(),categoryname,categoryClass,e1.getKey()));
            }
        }
    }

    public Com(String peerId) throws IOException, ClassNotFoundException {
        super(peerId);
        this.peerAddress = getPeerAddress();
        this.peerId = peerId;
        discoverServices("modules");
    }

    @Deprecated
    public void startService(String name){

    }

    // TODO: 2018. 11. 09. public this i
    @Deprecated
    public Map<String,Node> nodes = new HashMap();
   
    public String getSourceHome(){
        //todo überhack
        String p = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        p =  p.substring(0,p.lastIndexOf("target/classes"));
        return p;
    }

    @Deprecated
    public void addNode(String processid,Node process){
        this.nodes.put(processid,process);
    }
    @Deprecated
    public void detachNode(String processid){
        this.nodes.remove(processid);
    }

    /**
     * abstract method for sending data to the registered target/consumer nodes.
     * @param msg The message string to send.
     * @param sender The identifier of the sender process, that is necessary since the porcesses running within a {@link Com} object will share the channels.
     * @return The potential response from the addressee.
     */
    public abstract String publish(String msg,String sender);

    /**
     * abstract method for sending data to the one target process.
     * @param to
     * @param msg
     * @param sender
     * @return
     */
    public abstract String send(Connection to, String msg, String sender) throws IOException;

    /**
     * Abstract method to acccess the processes mailbox. 
     * @param id The peerId of the receiver process.
     * @return The list of String messages.
     */
    public abstract List<String> receive(String id);

    // TODO: 2018. 11. 08. rename

    /**
     * Method for searching for resources. It first looks up inn the local repository then ask the peers to find it and send it.
     * Resources should be stored in a common "repo" folder under the name of the module that they belong to.
     * @param fileName The name of the folder or file to find
     * @param location The teoretical location where it supposed to be found.
     * @param fileDestinationNode The connection information of the node where it should be sent.
     * @return
     */
    public abstract String getFile(String fileName, String location, String fileDestinationNode);

    /**
     *     here we have to connect to the network itself
     * @param descriptor connection information of one node, the format should be given depending on the implementation of
     *                   the {@link Com} object.
     * @return connection successful
     */
    public abstract boolean connectToNetwork(Connection descriptor);

    /**
     * Method for specifying the processes where the produced output should be sent. The target process will be added to consumer process list,
     * and a message should be sent to require the target to add the current process to the source lists.
     * @param descriptor The descriptor of the process to connect to and to which we want to send the outputs of this process.
     * @param processId The identifier of the target process
     * @return connection successful
     */
    public abstract boolean addOutPutChannel(Connection descriptor, String processId);

    /**
     * Method for subscribing for uptates to another process. The process in parameter will be added to source node list,
     * and a message will be sent to the target process for adding the current process to the consumer process list.
     * @param descriptor
     * @param processId
     * @return connection successful
     */
    public abstract boolean addInputchannel(Connection descriptor, String processId);

    public boolean addBidirectionalChannel(Connection descriptor,String processId){
        return addOutPutChannel(descriptor,processId) && addInputchannel(descriptor,processId);
    }

    public abstract Address getPeerAddress();

    public NodeDescriptor getInfo(){
        return new NodeDescriptor(this.moduleDescriptionByCategory, this.peerAddress );
    }

    //public abstract List<NetworkGraph> requestNeighboursMap();

    public abstract NetworkGraph mapNetwork();


}
