package at.fhv.puzzle2.communication.application.command.commands.mobile;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class NoQuestionsLeftCommand extends Command {
    public NoQuestionsLeftCommand(ClientID clientID) {
        super(clientID, CommandType.NoQuestionsLeft);
    }
}
