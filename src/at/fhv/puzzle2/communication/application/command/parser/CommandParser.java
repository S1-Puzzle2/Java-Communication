package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.application.command.AbstractCommand;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;

import java.util.HashMap;

public interface CommandParser {
    boolean canProcessMessage(String messageType);
    AbstractCommand parseCommand(HashMap<String, Object> messageData) throws MalformedCommandException;
}
