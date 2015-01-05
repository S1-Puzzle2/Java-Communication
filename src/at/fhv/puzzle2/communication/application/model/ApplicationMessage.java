package at.fhv.puzzle2.communication.application.model;

import at.fhv.puzzle2.communication.application.connection.ApplicationConnection;

import java.nio.charset.Charset;

public class ApplicationMessage {
    private String _message;
    private ApplicationConnection _sender = null;

    public ApplicationMessage(String message) {
        this._message = message;
    }

    public ApplicationMessage(String message, ApplicationConnection sender) {
        this(message);

        this._sender = sender;
    }

    public ApplicationMessage(byte[] message) {
        this(new String(message, Charset.forName("UTF-8")));
    }

    public ApplicationMessage(byte[] message, ApplicationConnection sender) {
        this(new String(message, Charset.forName("UTF-8")), sender);
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        _message = message;
    }

    public void setMessage(byte[] message) {
        _message = new String(message, Charset.forName("UTF-8"));
    }

    public ApplicationConnection getSender() {
        return _sender;
    }

    public void setSender(ApplicationConnection sender) {
        _sender = sender;
    }
}
