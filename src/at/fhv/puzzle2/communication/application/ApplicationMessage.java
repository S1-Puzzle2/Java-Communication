package at.fhv.puzzle2.communication.application;

import at.fhv.puzzle2.communication.application.connection.ApplicationConnection;

import java.nio.charset.Charset;

/**
 * Created by sinz on 14.01.2015.
 */
public class ApplicationMessage {
    private String _message;
    private ApplicationConnection _sender = null;

    public ApplicationMessage(ApplicationConnection sender, String message) {
        _sender = sender;
        _message = message;
    }

    public ApplicationMessage(String message) {
        _message = message;
    }

    public String getMessage() {
        return _message;
    }

    public ApplicationConnection getSender() {
        return _sender;
    }

    public void setMessage(byte[] message) {
        this._message = new String(message, Charset.forName("UTF-8"));
    }
}
