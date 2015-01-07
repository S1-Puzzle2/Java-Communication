package at.fhv.puzzle2.communication.connection;

public abstract class NetworkConnectionDecorator implements NetworkConnection {
    protected NetworkConnection _connection;

    public NetworkConnectionDecorator(NetworkConnection connection) {
        _connection = connection;
    }
}
