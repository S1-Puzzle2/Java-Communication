package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.mobile.PartScannedCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.HashMap;

public class PartScannedCommandParser extends CommandParser {
    @Override
    public boolean canProcessMessage(String messageType) {
        return CommandType.PartScanned.isRightType(messageType);
    }

    @Override
    protected Command parse(ClientID clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        PartScannedCommand command = new PartScannedCommand(clientID);
        command.setBarcode((String) messageData.get(CommandConstants.BAR_CODE));

        return command;
    }
}
