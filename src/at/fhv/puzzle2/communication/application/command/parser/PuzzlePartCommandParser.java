package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.PuzzlePartCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.math.BigDecimal;
import java.util.HashMap;

public class PuzzlePartCommandParser extends CommandParser {
    @Override
    public boolean canProcessMessage(String messageType) {
        return CommandType.PuzzlePart.isRightType(messageType);
    }

    @Override
    protected Command parse(ClientID clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        PuzzlePartCommand puzzlePartCommand = new PuzzlePartCommand(clientID);
        puzzlePartCommand.setImageID(new BigDecimal((Long) messageData.get(CommandConstants.PUZZLE_PART_ID)).intValueExact());
        puzzlePartCommand.setOrder(new BigDecimal((Long) messageData.get(CommandConstants.PUZZLE_PART_ORDER)).intValueExact());

        puzzlePartCommand.setImage((String) messageData.get(CommandConstants.IMAGE));

        return puzzlePartCommand;
    }
}
