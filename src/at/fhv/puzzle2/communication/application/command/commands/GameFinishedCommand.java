package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class GameFinishedCommand extends Command {
    private boolean _isWinning;

    public GameFinishedCommand(ClientID clientID) {
        super(clientID, CommandType.GameFinished);
    }

    public void setIsWinning(boolean isWinning) {
        _isWinning = isWinning;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.IS_WINNING, _isWinning);

        return super.toJSONString();
    }
}
