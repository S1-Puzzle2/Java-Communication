package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public abstract class CommandParser {
    boolean _omitMessageDataAllowed = false;
    boolean _clientIDNullAllowed = false;

    protected CommandParser(boolean omitMessageDataAllowed, boolean clientIDNullAllowed) {
        _omitMessageDataAllowed = omitMessageDataAllowed;
        _clientIDNullAllowed = clientIDNullAllowed;
    }

    protected CommandParser(boolean _omitMessageDataAllowed) {
        this(_omitMessageDataAllowed, false);
    }
    public abstract boolean canProcessMessage(String messageType);
    protected abstract Command parse(ClientID clientID, HashMap<String, Object> messageData) throws MalformedCommandException;

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

            String clientIDString =(String) message.get(CommandConstants.CLIENT_ID);
            ClientID clientID;
            if(clientIDString == null || clientIDString.isEmpty()) {
                clientID = null;
            } else {
                clientID = new ClientID(UUID.fromString(clientIDString));
            }

            if(!_clientIDNullAllowed && clientID == null) {
                throw new MalformedCommandException();
            }

            return parse(clientID, messageData);
        } catch(Exception e) {
            throw new MalformedCommandException();
        }
    }
}
