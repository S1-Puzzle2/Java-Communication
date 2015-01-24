package at.fhv.puzzle2.communication.connection;

abstract class NetworkConnectionDecorator implements NetworkConnection {
    final NetworkConnection _connection;

    NetworkConnectionDecorator(NetworkConnection connection) {
        _connection = connection;
    }
}
