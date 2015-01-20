package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class GameStartResponseCommand extends Command {
    protected GameStartResponseCommand(ClientID clientID) {
        super(clientID, CommandType.GameStart);
    }

    private GameStartResponseCommand(GameStartResponseCommand command, ClientID clientID) {
        super(clientID, command.getCommandType());
    }
}
