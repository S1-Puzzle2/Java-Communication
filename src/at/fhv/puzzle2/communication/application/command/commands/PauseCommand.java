package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class PauseCommand extends Command {
    public PauseCommand(ClientID clientID) {
        super(clientID, CommandType.Pause);
    }

    public PauseCommand(PauseCommand pauseCommand, ClientID clientID) {
        super(clientID, pauseCommand.getCommandType());
    }
}
