package at.fhv.puzzle2.communication.application.command;

public class UnknownCommandException extends Exception {
    public UnknownCommandException(String message) {
        super(message);
    }
}
