package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class PuzzlePartCommand extends Command {
    private String _image64;
    private int _imageID;

    public PuzzlePartCommand(ClientID clientID) {
        super(clientID, CommandType.PuzzlePart);
    }

    public void setImage(String image64) {
        _image64 = image64;
    }

    public void setImageID(int id) {
        _imageID = id;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.IMAGE, _image64);
        _messageData.put(CommandConstants.PUZZLE_PART_ID, _imageID);

        return super.toJSONString();
    }
}
