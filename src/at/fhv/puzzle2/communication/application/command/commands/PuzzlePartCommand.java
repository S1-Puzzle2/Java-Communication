package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;
import at.fhv.puzzle2.communication.connection.networkPacket.NetworkPacketPriority;

import java.util.Base64;

public class PuzzlePartCommand extends Command {
    private String _base64Image;
    private int _imageID;

    public PuzzlePartCommand(ClientID clientID) {
        super(clientID, CommandType.PuzzlePart, NetworkPacketPriority.IMAGE_PRIORITY);
    }

    public void setImage(byte[] image) {
        _base64Image = Base64.getEncoder().encodeToString(image);
    }

    public void setImage(String base64Image) {
        _base64Image = base64Image;
    }

    public void setImageID(int id) {
        _imageID = id;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.ID, _imageID);
        _messageData.put(CommandConstants.BASE64IMAGE, _base64Image);

        return super.toJSONString();
    }

    @Override
    public String getLogString() {
        _messageData.put(CommandConstants.ID, _imageID);
        _messageData.put(CommandConstants.BASE64IMAGE, LOG_DATA_OMITTED);

        return super.toJSONString();
    }
}
