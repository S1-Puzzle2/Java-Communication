package at.fhv.puzzle2.communication.application.command.commands.unity;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class ShowQRCommand extends Command {
    private String _qrCode;
    public ShowQRCommand(ClientID clientID) {
        super(clientID, CommandType.ShowQR);
    }

    public void setQRCode(String code) {
        _qrCode = code;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.QR_CODE, _qrCode);

        return super.toJSONString();
    }
}
