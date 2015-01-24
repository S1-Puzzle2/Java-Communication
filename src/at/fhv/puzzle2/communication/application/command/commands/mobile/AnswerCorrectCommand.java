package at.fhv.puzzle2.communication.application.command.commands.mobile;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class AnswerCorrectCommand extends IsCorrectCommand {
    private int _correctAnswerID;

    public AnswerCorrectCommand(ClientID clientID) {
        super(clientID, CommandType.AnswerCorrect);
    }

    public void setCorrectAnswerID(int id) {
        _correctAnswerID = id;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.CORRECT_ANSWER_ID, _correctAnswerID);

        return super.toJSONString();
    }
}
