package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.mobile.AllPartsUnlockedCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.HashMap;

public class PuzzleFinishedCommandParser extends CommandParser {
    public PuzzleFinishedCommandParser() {
        super(true);
    }

    @Override
    public boolean canProcessMessage(String messageType) {
        return CommandType.AllPartsUnlocked.isRightType(messageType);
    }

    @Override
    protected Command parse(ClientID clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        return new AllPartsUnlockedCommand(clientID);
    }
}
