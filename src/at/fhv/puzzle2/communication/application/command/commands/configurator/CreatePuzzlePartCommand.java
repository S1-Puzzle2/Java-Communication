package at.fhv.puzzle2.communication.application.command.commands.configurator;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class CreatePuzzlePartCommand extends Command {
    private int _puzzleID;

    private String _uuid;
    private int _order;
    private byte[] _image;

    public CreatePuzzlePartCommand(ClientID clientID) {
        super(clientID, CommandType.CreatePuzzlePart);
    }

    public byte[] getImage() {
        return _image;
    }

    public void setImage(byte[] image) {
        this._image = image;
    }

    public int getOrder() {
        return _order;
    }

    public void setOrder(int order) {
        this._order = order;
    }

    public int getPuzzleID() {
        return _puzzleID;
    }

    public void setPuzzleID(int id) {
        this._puzzleID = id;
    }

    public String getUuid() {
        return _uuid;
    }

    public void setUuid(String uuid) {
        this._uuid = uuid;
    }
}
