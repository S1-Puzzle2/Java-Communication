package at.fhv.puzzle2.communication.application.command;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.application.command.commands.MalformedCommand;
import at.fhv.puzzle2.communication.application.command.commands.UnknownCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.parser.*;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CommandFactory {
    private static List<CommandParser> _parserList = new LinkedList<>();

    static {
        _parserList.add(new RegisterCommandParser());
        _parserList.add(new ReadyCommandParser());
        _parserList.add(new GetGameStateCommandParser());
        _parserList.add(new GetPuzzlePartCommandParser());
    }

    public static Command parseCommand(ApplicationMessage message) {
        try {
            HashMap<String, Object> messageData = (HashMap<String, Object>) JSONValue.parse(message.getMessage());

            if(!messageData.containsKey(CommandConstants.MESSAGE_TYPE)) {
                return new MalformedCommand(message);
            }

            Optional<CommandParser> optionalParser = _parserList.stream()
                    .filter(parser -> parser.canProcessMessage((String)messageData.get(CommandConstants.MESSAGE_TYPE)))
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
