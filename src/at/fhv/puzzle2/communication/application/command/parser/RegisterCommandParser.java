package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.application.command.*;
import at.fhv.puzzle2.communication.application.command.commands.RegisterCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandTypeConstants;

import java.util.HashMap;
import java.util.Objects;

public class RegisterCommandParser extends CommandParser {

    public RegisterCommandParser() {
        super(false);
    }

    @Override
    public boolean canProcessMessage(String messageType) {
        return Objects.equals(messageType, CommandTypeConstants.REGISTER_MESSAGE);
    }

    @Override
    public AbstractCommand parse(String clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        try {
            RegisterCommand registerCommand = new RegisterCommand(clientID);
            registerCommand.setClientType((String) messageData.get(CommandConstants.CLIENT_TYPE));

            return registerCommand;
        } catch(Exception e) {
            throw new MalformedCommandException();
        }
    }
}
