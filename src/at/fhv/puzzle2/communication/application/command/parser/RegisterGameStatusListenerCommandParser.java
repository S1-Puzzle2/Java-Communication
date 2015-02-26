package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.RegisterGameStatusListenerCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.HashMap;

public class RegisterGameStatusListenerCommandParser extends CommandParser {
    public RegisterGameStatusListenerCommandParser() {
        super(true);
    }
    @Override
    public boolean canProcessMessage(String messageType) {
        return CommandType.RegisterGameStatusListener.isRightType(messageType);
    }

    @Override
    protected Command parse(ClientID clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        RegisterGameStatusListenerCommand command = new RegisterGameStatusListenerCommand(clientID);

        return command;
    }
}
