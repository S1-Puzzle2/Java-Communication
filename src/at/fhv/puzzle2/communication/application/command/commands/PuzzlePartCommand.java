package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

import java.util.Base64;

public class PuzzlePartCommand extends Command {
    private byte[] _image;
    private int _imageID;
    private int _order;

    public PuzzlePartCommand(ClientID clientID) {
        super(clientID, CommandType.PuzzlePart);
    }

    public void setImage(byte[] image) {
        _image = image;
    }

    public void setImageID(int id) {
        _imageID = id;
    }

    public void setOrder(int order) {
        _order = order;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.IMAGE, Base64.getEncoder().encodeToString(_image));
        _messageData.put(CommandConstants.PUZZLE_PART_ID, _imageID);
        _messageData.put(CommandConstants.PUZZLE_PART_ORDER, _order);

        return super.toJSONString();
    }

    @Override
    public String getLogString() {
        _messageData.put(CommandConstants.IMAGE, LOG_DATA_OMITTED);
        _messageData.put(CommandConstants.PUZZLE_PART_ID, _imageID);
        _messageData.put(CommandConstants.PUZZLE_PART_ORDER, _order);

        return super.toJSONString();
    }
}
