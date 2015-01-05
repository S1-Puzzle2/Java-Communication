package at.fhv.puzzle2.communication.connection;

public class Broadcast {
    public NetworkConnection _sender;
    public String _message;

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
