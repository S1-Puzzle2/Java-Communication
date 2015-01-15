package at.fhv.puzzle2.communication.application.command.parser;


import at.fhv.puzzle2.communication.application.command.AbstractCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.ReadyCommand;

import java.util.HashMap;
import java.util.Objects;

public class ReadyCommandParser extends CommandParser {
    public ReadyCommandParser() {
        super(true);
    }

    @Override
    public boolean canProcessMessage(String messageType) {
        return Objects.equals(messageType, CommandTypeConstants.READY_MESSAGE);
    }

    @Override
    public AbstractCommand parse(String clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        return new ReadyCommand(clientID);
    }
}
