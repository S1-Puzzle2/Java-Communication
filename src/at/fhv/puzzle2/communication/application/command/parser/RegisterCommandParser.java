package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.application.command.AbstractCommand;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.RegisterCommand;
import at.fhv.puzzle2.communication.application.command.UnknownCommand;
import at.fhv.puzzle2.communication.application.connection.ApplicationConnectionDecorator;

import java.util.HashMap;
import java.util.Objects;

public class RegisterCommandParser implements CommandParser {
    @Override
    public boolean canProcessMessage(String messageType) {
        return Objects.equals(messageType, "REGISTER");
    }

    @Override
    public AbstractCommand parseCommand(HashMap<String, Object> message) throws MalformedCommandException {
        try {
            HashMap<String, Object> messageData = (HashMap<String, Object>) message.get("msgData");

            RegisterCommand registerCommand = new RegisterCommand((String) message.get("playerID"));
            registerCommand.setClientType((String) messageData.get("clientType"));

            return registerCommand;
        } catch(Exception e) {
            throw new MalformedCommandException();
        }
    }
}
