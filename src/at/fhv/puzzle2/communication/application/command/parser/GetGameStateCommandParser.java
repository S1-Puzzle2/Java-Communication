package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.application.command.AbstractCommand;
import at.fhv.puzzle2.communication.application.command.commands.GetGameStateCommand;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;

import java.util.HashMap;
import java.util.Objects;

public class GetGameStateCommandParser extends CommandParser {
    public GetGameStateCommandParser() {
        super(true);
    }

    @Override
    public boolean canProcessMessage(String messageType) {
        return Objects.equals(messageType, CommandTypeConstants.GET_GAME_STATE_MESSAGE);
    }

    @Override
    protected AbstractCommand parse(String clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        return new GetGameStateCommand(clientID);
    }
}
