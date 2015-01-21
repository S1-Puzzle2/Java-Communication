package at.fhv.puzzle2.communication.application.command.commands.mobile;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class BarCodeCorrectCommand extends IsCorrectCommand {
    protected BarCodeCorrectCommand(ClientID clientID) {
        super(clientID, CommandType.BarcodeCorrect);
    }
}
