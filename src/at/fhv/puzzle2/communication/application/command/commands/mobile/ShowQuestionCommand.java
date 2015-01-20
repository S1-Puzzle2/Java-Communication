package at.fhv.puzzle2.communication.application.command.commands.mobile;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.HashMap;

public class ShowQuestionCommand extends Command {
    private int _questionID;
    private String _questionText;
    private HashMap<Integer, String> _answerMap;

    protected ShowQuestionCommand(ClientID clientID) {
        super(clientID, CommandType.ShowQuestion);
    }

    public void setQuestionID(int id) {
        _questionID = id;
    }

    public void setQuestionText(String text) {
        _questionText = text;
    }

    public void setAnswerMap(HashMap<Integer, String> answerMap) {
        _answerMap = answerMap;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.QUESTION_ID, _questionID);
        _messageData.put(CommandConstants.QUESTION_TEXT, _questionText);
        _messageData.put(CommandConstants.QUESTION_ANSWERS, _answerMap);

        return super.toJSONString();
    }
}
