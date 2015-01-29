package at.fhv.puzzle2.communication.application.command.commands.configurator;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class GetPuzzleListCommand extends Command {
    public GetPuzzleListCommand(ClientID clientID) {
        super(clientID, CommandType.GetPuzzleList);
    }
}
