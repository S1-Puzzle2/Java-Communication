package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class GetGameStateCommand extends Command {
    public GetGameStateCommand(ClientID clientID) {
        super(clientID, CommandType.GetGameState);
    }

    public GetGameStateCommand(GetGameStateCommand command, ClientID clientID) {
        super(clientID, command.getCommandType());
    }
}
