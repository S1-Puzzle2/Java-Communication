package at.fhv.puzzle2.communication.connection;

public abstract class NetworkConnectionDecorator implements NetworkConnection {
    protected NetworkConnection _connection;

    protected NetworkConnectionDecorator(NetworkConnection connection) {
        _connection = connection;
    }
}
