package lib;

public abstract class Node extends Thread{
    private String coordinatorDescriptor;

    public String[] getArguments() {
        return arguments;
    }

    String[] arguments;
    public void setArguments(String[] args){this.arguments = args;}
    public void setProcessId(String id) {
        this.id = id;
    }
    public void setCoordinatorDescriptor(String s){
        this.coordinatorDescriptor = s;
    }
    public void connect(){
        this.com.connect(this.coordinatorDescriptor,this.id);
    }

    public String getProcessId() {
        return id;
    }

    String id="node";
    protected Com com;
    public void setCom(Com c){this.com = c;}

    //public abstract void receive(String s);
    //public abstract void send(String s);
    public abstract boolean start(String[] args);
    public abstract boolean stopNode();

}
