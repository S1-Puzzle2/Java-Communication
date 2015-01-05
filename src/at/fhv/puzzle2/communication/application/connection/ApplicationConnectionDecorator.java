package at.fhv.puzzle2.communication.application.connection;

public abstract  class ApplicationConnectionDecorator implements ApplicationConnection {
    protected ApplicationConnection _connection;

    public ApplicationConnectionDecorator(ApplicationConnection connection) {
        _connection = connection;
    }
}
