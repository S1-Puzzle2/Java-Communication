package at.fhv.puzzle2.communication.application.command.parser;


import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.ReadyCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.HashMap;

public class ReadyCommandParser extends CommandParser {
    public ReadyCommandParser() {
        super(true);
    }

    @Override
    public boolean canProcessMessage(String messageType) {
        return CommandType.Ready.isRightType(messageType);
    }

    @Override
    public Command parse(String clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        return new ReadyCommand(new ClientID(clientID));
    }
}
