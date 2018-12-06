package lib;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ModuleContainer {
    String classname;
    String categoryName;
    Class<?> category;
    Class<?> clazz;
    Map<String,Node> threadByName = new HashMap<>();

    static int counter;

    public ModuleContainer(String classname, String categoryName, Class<?> category, Class<?> clazz) {
        this.classname = classname;
        this.categoryName = categoryName;
        this.category = category;
        this.clazz = clazz;
    }


    public Node instantiate() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String nodeName = this.classname+"_"+counter++;
        Node n = (Node)this.clazz.getConstructor(String.class).newInstance(nodeName);
        this.threadByName.put(nodeName,n);
        return n;
    }
    @Deprecated
    public boolean killNode(String name) throws NoSuchElementException{
        Node n = this.threadByName.get(name);
        if(n == null)
            return false;
        n.interrupt();
        return true;
    }

}