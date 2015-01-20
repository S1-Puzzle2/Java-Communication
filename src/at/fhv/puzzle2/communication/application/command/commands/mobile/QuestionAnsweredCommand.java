package at.fhv.puzzle2.communication.application.command.commands.mobile;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class QuestionAnsweredCommand extends Command {
    private int _questionID;
    private int _answerID;

    public QuestionAnsweredCommand(ClientID clientID) {
        super(clientID, CommandType.QuestionAnswered);
    }

    public void setQuestionID(int id) {
        _questionID = id;
    }

    public void setAnswerID(int id) {
        _answerID = id;
    }

    public int getQuestionID() {
        return _questionID;
    }

    public int getAnswerID() {
        return _answerID;
    }
}
