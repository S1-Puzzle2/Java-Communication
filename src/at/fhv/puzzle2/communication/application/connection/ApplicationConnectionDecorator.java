package at.fhv.puzzle2.communication.application.connection;

import at.fhv.puzzle2.communication.connection.NetworkConnection;

public abstract  class ApplicationConnectionDecorator implements ApplicationConnection {
    protected final ApplicationConnection _connection;

    protected ApplicationConnectionDecorator(ApplicationConnection connection) {
        _connection = connection;
    }

    @Override
    public NetworkConnection getUnderlyingConnection() {
        return _connection.getUnderlyingConnection();
    }
}
