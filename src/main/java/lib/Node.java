package lib;

public abstract class Node extends Thread{


    private Connection coordinatorDescriptor;

    public String[] getArguments() {
        return arguments;
    }



    String[] arguments;
    public void setArguments(String[] args){this.arguments = args;}
    //public void setApplicationNodeId(String id) {
    //    this.applicationNodeId = id;
    //}
    public void setCoordinatorDescriptor(Connection s){
        this.coordinatorDescriptor = s;
    }
    public void connect(){
        this.com.connectToNetwork(this.coordinatorDescriptor);
        this.com.addOutPutChannel(this.coordinatorDescriptor,this.getName());
    }
    public Node(String name){
        super(name);
    }

    public abstract String getResourceRoot();
    public abstract String getSourceHome();

    //public String getApplicationNodeId() {
    //    return applicationNodeId;
    //}

    //
    //String applicationNodeId = this.getClass().getName()+"node";
    protected Com com;
    // instantiated by the Com object thus it will be handled there. This method is also called byCom.
    public void setCom(Com c){this.com = c;/*c.addNode(getApplicationNodeId(),this);*/}

    //public abstract void receive(String s);
    //public abstract void send(String s);
    // TODO: 2018. 11. 09. remove from com list at exit 
    public abstract boolean start(String[] args);
    public abstract boolean stopNode();


}
