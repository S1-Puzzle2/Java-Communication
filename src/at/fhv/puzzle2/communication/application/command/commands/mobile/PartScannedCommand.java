package at.fhv.puzzle2.communication.application.command.commands.mobile;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class PartScannedCommand extends Command {
    private String _barCode;

    public PartScannedCommand(ClientID clientID) {
        super(clientID, CommandType.PartScanned);
    }

    public void setBarcode(String barcode) {
        _barCode = barcode;
    }

    public String getBarCode() {
        return _barCode;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.BAR_CODE, _barCode);

        return super.toJSONString();
    }
}
