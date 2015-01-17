package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.GetGameStateCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.HashMap;

public class GetGameStateCommandParser extends CommandParser {
    public GetGameStateCommandParser() {
        super(true);
    }

    @Override
    public boolean canProcessMessage(String messageType) {
        return CommandType.GetGameState.isRightType(messageType);
    }

    @Override
    protected Command parse(String clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        return new GetGameStateCommand(new ClientID(clientID));
    }
}
