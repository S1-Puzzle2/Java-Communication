package at.fhv.puzzle2.communication.application.command.commands.mobile;


import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class AllPartsUnlockedCommand extends Command {
    public AllPartsUnlockedCommand(ClientID clientID) {
        super(clientID, CommandType.AllPartsUnlocked);
    }
}
