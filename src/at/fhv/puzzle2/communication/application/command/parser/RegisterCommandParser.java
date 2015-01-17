package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.RegisterCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.HashMap;

public class RegisterCommandParser extends CommandParser {

    public RegisterCommandParser() {
        super(false);
    }

    @Override
    public boolean canProcessMessage(String messageType) {
        return CommandType.Register.isRightType(messageType);
    }

    @Override
    public Command parse(String clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        try {
            RegisterCommand registerCommand = new RegisterCommand(new ClientID(clientID));
            registerCommand.setClientType((String) messageData.get(CommandConstants.CLIENT_TYPE));

            return registerCommand;
        } catch(Exception e) {
            throw new MalformedCommandException();
        }
    }
}
