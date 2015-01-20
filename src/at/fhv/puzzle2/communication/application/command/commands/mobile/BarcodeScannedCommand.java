package at.fhv.puzzle2.communication.application.command.commands.mobile;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class BarcodeScannedCommand extends Command {
    private String _barCode;

    public BarcodeScannedCommand(ClientID clientID) {
        super(clientID, CommandType.BarcodeScanned);
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
