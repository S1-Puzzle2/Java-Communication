package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.configurator.SetPuzzleCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.math.BigDecimal;
import java.util.HashMap;

public class SetPuzzleCommandParser extends CommandParser {
    @Override
    public boolean canProcessMessage(String messageType) {
        return CommandType.SetPuzzle.isRightType(messageType);
    }

    @Override
    protected Command parse(ClientID clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        SetPuzzleCommand command = new SetPuzzleCommand(clientID);
        command.setPuzzleID(new BigDecimal((Long) messageData.get(CommandConstants.ID)).intValueExact());

        return command;
    }
}
