package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.configurator.GetPuzzlePartListCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.HashMap;

public class GetPuzzlePartListCommandParser extends CommandParser {
    @Override
    public boolean canProcessMessage(String messageType) {
        return CommandType.GetPuzzlePartList.isRightType(messageType);
    }

    @Override
    protected Command parse(ClientID clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        GetPuzzlePartListCommand getPuzzlePartListCommand = new GetPuzzlePartListCommand(clientID);
        getPuzzlePartListCommand.setPuzzleName((String) messageData.get(CommandConstants.PUZZLE_NAME));

        return getPuzzlePartListCommand;
    }
}
