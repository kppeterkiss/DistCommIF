package lib;

public abstract class Connection<A extends Address> {
    public A getAddress() {
        return address;
    }

    A address;
}
