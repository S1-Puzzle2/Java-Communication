package at.fhv.puzzle2.communication.application.command.commands.mobile;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class AnswerCorrectCommand extends IsCorrectCommand {
    protected AnswerCorrectCommand(ClientID clientID) {
        super(clientID, CommandType.AnswerCorrect);
    }
}
