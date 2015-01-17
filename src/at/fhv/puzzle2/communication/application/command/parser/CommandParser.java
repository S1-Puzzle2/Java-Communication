package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;

import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class CommandParser {
    private boolean _omitMessageDataAllowed = false;

    protected CommandParser(boolean omitMessageDataAllowed) {
        _omitMessageDataAllowed = omitMessageDataAllowed;
    }
    public abstract boolean canProcessMessage(String messageType);
    protected abstract Command parse(String clientID, HashMap<String, Object> messageData) throws MalformedCommandException;

    public Command parseCommand(HashMap<String, Object> message) throws  MalformedCommandException {
        try {
            boolean containsMessageData = message.containsKey(CommandConstants.MESSAGE_DATA);
            if(!_omitMessageDataAllowed && !containsMessageData) {
                throw new MalformedCommandException();
            }

            HashMap<String, Object> messageData;
            if(containsMessageData) {
                messageData = (HashMap<String, Object>) message.get(CommandConstants.MESSAGE_DATA);
            } else {
                messageData = new LinkedHashMap<>();
            }

            return parse((String) message.get(CommandConstants.CLIENT_ID), messageData);
        } catch(Exception e) {
            throw new MalformedCommandException();
        }
    }
}
