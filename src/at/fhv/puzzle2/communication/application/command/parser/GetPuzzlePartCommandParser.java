package at.fhv.puzzle2.communication.application.command.parser;


import at.fhv.puzzle2.communication.application.command.AbstractCommand;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.GetPuzzlePartCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;

import java.util.HashMap;
import java.util.Objects;

public class GetPuzzlePartCommandParser extends CommandParser {
    public GetPuzzlePartCommandParser() {
        super(false);
    }

    @Override
    public boolean canProcessMessage(String messageType) {
        return Objects.equals(messageType, CommandTypeConstants.GET_PUZZLE_PART_MESSAGE);
    }

    @Override
    protected AbstractCommand parse(String clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        GetPuzzlePartCommand command = new GetPuzzlePartCommand(clientID);
        command.setPuzzlePartID((Integer) messageData.get("puzzlePartID"));

        return command;
    }
}
