package at.fhv.puzzle2.communication.application.command.commands.mobile;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;
import at.fhv.puzzle2.communication.application.command.dto.AnswerDTO;

import java.util.List;

public class AnswerQuestionCommand extends Command {
    private int _questionID;
    private String _questionText;
    private List<AnswerDTO> _answerList;

    public AnswerQuestionCommand(ClientID clientID) {
        super(clientID, CommandType.AnswerQuestion);
    }

    public void setQuestionID(int id) {
        _questionID = id;
    }

    public void setQuestionText(String text) {
        _questionText = text;
    }

    public void setAnswerList(List<AnswerDTO> answerList) {
        _answerList = answerList;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.ID, _questionID);
        _messageData.put(CommandConstants.TEXT, _questionText);
        _messageData.put(CommandConstants.ANSWERS, _answerList);

        return super.toJSONString();
    }
}
