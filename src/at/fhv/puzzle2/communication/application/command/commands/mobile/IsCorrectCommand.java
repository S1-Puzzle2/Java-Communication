package at.fhv.puzzle2.communication.application.command.commands.mobile;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public abstract class IsCorrectCommand extends Command {
    private boolean _isCorrect;

    IsCorrectCommand(ClientID clientID, CommandType commandType) {
        super(clientID, commandType);
    }

    public void setIsCorrect(boolean isCorrect) {
        _isCorrect = isCorrect;
    }

    public boolean getIsCorrect() {
        return _isCorrect;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.IS_CORRECT, _isCorrect);

        return super.toJSONString();
    }
}
