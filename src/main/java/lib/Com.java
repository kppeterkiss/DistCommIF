package lib;

import java.util.List;

public abstract class Com extends Thread{
    public abstract boolean  send(String msg,String sender);
    public abstract List<String> receive(String id);
    public abstract boolean connect(String descriptor, String processId);
}
