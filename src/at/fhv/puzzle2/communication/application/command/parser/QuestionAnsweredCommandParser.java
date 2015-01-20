package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.mobile.QuestionAnsweredCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.HashMap;

public class QuestionAnsweredCommandParser extends CommandParser {
    public QuestionAnsweredCommandParser() {
        super(false);
    }

    @Override
    public boolean canProcessMessage(String messageType) {
        return CommandType.QuestionAnswered.isRightType(messageType);
    }

    @Override
    protected Command parse(ClientID clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        QuestionAnsweredCommand command = new QuestionAnsweredCommand(clientID);
        command.setQuestionID((Integer) messageData.get(CommandConstants.QUESTION_ID));
        command.setAnswerID((Integer) messageData.get(CommandConstants.ANSWER_ID));

        return command;
    }
}
