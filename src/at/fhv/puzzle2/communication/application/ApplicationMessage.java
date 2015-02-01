package at.fhv.puzzle2.communication.application;

import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.connection.ApplicationConnection;

import java.nio.charset.Charset;

public class ApplicationMessage {
    private String _message;
    private ApplicationConnection _sender = null;
    private int _priority;

    public ApplicationMessage(ApplicationConnection sender, String message) {
        this(sender, message, 0);
    }

    public ApplicationMessage(ApplicationConnection sender, String message, int priority) {
        _sender = sender;
        _message = message;
        _priority = priority;
    }

    public ApplicationMessage(String message, int priority) {
        this(null, message, priority);
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

    public int getPriority() {
        return _priority;
    }

    public static ApplicationMessage createApplicationMessage(Command command) {
        return new ApplicationMessage(command.toJSONString(), command.getPriority());
    }
}
