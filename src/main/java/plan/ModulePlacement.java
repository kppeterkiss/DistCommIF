package plan;

@FunctionalInterface
public interface ModulePlacement<CallGraphType, NetworkGraphType>  {
    public CallGraphType apply(CallGraphType callGraph, NetworkGraphType networkGraph);
}
