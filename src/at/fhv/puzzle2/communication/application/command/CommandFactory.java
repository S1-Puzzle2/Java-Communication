package at.fhv.puzzle2.communication.application.command;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.application.command.commands.error.MalformedCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.parser.*;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CommandFactory {
    private static final List<CommandParser> _parserList = new LinkedList<>();

    static {
        _parserList.add(new RegisterCommandParser());
        _parserList.add(new ReadyCommandParser());
        _parserList.add(new GetGameStateCommandParser());
        _parserList.add(new GetPuzzlePartCommandParser());
        _parserList.add(new RegisteredCommandParser());
        _parserList.add(new BarcodeScannedCommandParser());
        _parserList.add(new QuestionAnsweredCommandParser());
        _parserList.add(new CreatePuzzleCommandParser());
        _parserList.add(new CreatePuzzlePartCommandParser());
    }

    public static Command parseCommand(ApplicationMessage message) throws MalformedCommandException, UnknownCommandException {
        try {
            @SuppressWarnings("unchecked") HashMap<String, Object> messageData = (HashMap<String, Object>) JSONValue.parse(message.getMessage());

            if(!messageData.containsKey(CommandConstants.MESSAGE_TYPE)) {
                return new MalformedCommand(message.getMessage());
            }

            Optional<CommandParser> optionalParser = _parserList.stream()
                    .filter(parser -> parser.canProcessMessage((String)messageData.get(CommandConstants.MESSAGE_TYPE)))
                    .findFirst();

            if(optionalParser.isPresent()) {
                return optionalParser.get().parseCommand(messageData);
            }
        } catch(Exception e) {
            throw new MalformedCommandException(message.getMessage());
        }

        throw new UnknownCommandException(message.getMessage());
    }
}
