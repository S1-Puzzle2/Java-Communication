package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.configurator.GetPuzzleListCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;
import at.fhv.puzzle2.communication.application.command.parser.CommandParser;

import java.util.HashMap;

public class GetPuzzleListCommandParser extends CommandParser {
    @Override
    public boolean canProcessMessage(String messageType) {
        return CommandType.GetPuzzleList.isRightType(messageType);
    }

    @Override
    protected Command parse(ClientID clientID, HashMap<String, Object> messageData) throws MalformedCommandException {

        return new GetPuzzleListCommand(clientID);
    }
}
