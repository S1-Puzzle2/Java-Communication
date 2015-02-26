package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.configurator.CreatePuzzlePartCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.math.BigDecimal;
import java.util.HashMap;

public class CreatePuzzlePartCommandParser extends CommandParser {
    public CreatePuzzlePartCommandParser() {
        super(false, true);
    }
    @Override
    public boolean canProcessMessage(String messageType) {
        return CommandType.CreatePuzzlePart.isRightType(messageType);
    }

    protected Command parse(ClientID clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        CreatePuzzlePartCommand command = new CreatePuzzlePartCommand(clientID);

        command.setOrder(new BigDecimal((Long) messageData.get(CommandConstants.PUZZLE_PART_ORDER)).intValueExact());
        command.setPuzzleID(new BigDecimal((Long) messageData.get(CommandConstants.PUZZLE_ID)).intValueExact());
        command.setUuid((String) messageData.get(CommandConstants.BAR_CODE));

        String base64Image = (String) messageData.get(CommandConstants.BASE64IMAGE);
        command.setImage(Base64.decode(base64Image));

        return command;
    }
}
