package at.fhv.puzzle2.communication.application.command;

import org.json.simple.JSONValue;

import java.util.HashMap;

public class MalformedCommandException extends Exception {
    public MalformedCommandException(String message) {
        super(message);
    }

    public MalformedCommandException(HashMap<String, Object> message) {
        this(JSONValue.toJSONString(message));
    }
}
