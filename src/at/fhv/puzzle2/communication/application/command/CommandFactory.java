package at.fhv.puzzle2.communication.application.command;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.application.command.parser.CommandParser;
import at.fhv.puzzle2.communication.application.command.parser.RegisterCommandParser;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CommandFactory {
    private static List<CommandParser> _parserList = new LinkedList<>();

    static {
        _parserList.add(new RegisterCommandParser());
    }

    public static Command parseCommand(ApplicationMessage message) {
        try {
            HashMap<String, Object> messageData = (HashMap<String, Object>) JSONValue.parse(message.getMessage());

            if(!messageData.containsKey("msgType")) {
                return new MalformedCommand(message);
            }

            Optional<CommandParser> optionalParser = _parserList.stream()
                    .filter(parser -> parser.canProcessMessage((String)messageData.get("msgType")))
                    .findFirst();

            if(optionalParser.isPresent()) {
                return optionalParser.get().parseCommand(messageData);
            }

            return new UnknownCommand(message);
        } catch(Exception e) {
            return new MalformedCommand(message);
        }
    }
}
