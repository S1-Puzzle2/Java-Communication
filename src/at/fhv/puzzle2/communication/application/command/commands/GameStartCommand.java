package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class GameStartCommand extends Command {
    private long _time;

    public GameStartCommand(ClientID clientID) {
        super(clientID, CommandType.GameStart);
    }

    public void setTime(long time) {
        _time = time;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.TIME, _time);

        return super.toJSONString();
    }
}
