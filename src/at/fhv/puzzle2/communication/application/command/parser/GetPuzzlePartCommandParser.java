package at.fhv.puzzle2.communication.application.command.parser;


import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.GetPuzzlePartCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.HashMap;

public class GetPuzzlePartCommandParser extends CommandParser {
    public GetPuzzlePartCommandParser() {
        super(false);
    }

    @Override
    public boolean canProcessMessage(String messageType) {
        return CommandType.GetPuzzlePart.isRightType(messageType);
    }

    @Override
    protected Command parse(ClientID clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        GetPuzzlePartCommand command = new GetPuzzlePartCommand(clientID);
        command.setPuzzlePartID((Integer) messageData.get("puzzlePartID"));

        return command;
    }
}
