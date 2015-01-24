package at.fhv.puzzle2.communication.connection;

public class Broadcast {
    private final NetworkConnection _sender;
    private final String _message;

    public Broadcast(NetworkConnection sender, String message) {
        _sender = sender;
        _message = message;
    }

    public NetworkConnection getSender() {
        return _sender;
    }

    public String getMessage() {
        return _message;
    }
}
