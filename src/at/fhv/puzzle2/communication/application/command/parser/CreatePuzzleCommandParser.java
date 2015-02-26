package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.configurator.CreatePuzzleCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.HashMap;

public class CreatePuzzleCommandParser extends CommandParser {
    public CreatePuzzleCommandParser() {
        super(false, true);
    }
    @Override
    public boolean canProcessMessage(String messageType) {
        return CommandType.CreatePuzzle.isRightType(messageType);
    }

    @Override
    protected Command parse(ClientID clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        CreatePuzzleCommand command = new CreatePuzzleCommand(clientID);

        command.setPuzzleName((String) messageData.get(CommandConstants.NAME));

        return command;
    }
}
