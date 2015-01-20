package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class ReadyCommand extends Command {
    public ReadyCommand(ClientID clientID) {
        super(clientID, CommandType.Ready);
    }
}
