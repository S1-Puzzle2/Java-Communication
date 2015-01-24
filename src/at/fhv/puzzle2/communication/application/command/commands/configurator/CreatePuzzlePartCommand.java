package at.fhv.puzzle2.communication.application.command.commands.configurator;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class CreatePuzzlePartCommand extends Command {
    private Integer _partID;

    private String _puzzleName;
    private String _uuid;
    private int _order;
    private byte[] _image;

    public CreatePuzzlePartCommand(ClientID clientID) {
        super(clientID, CommandType.CreatePuzzlePart);
    }

    public Integer getPartID() {
        return _partID;
    }

    public void setPartID(Integer partID) {
        this._partID = partID;
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

    public String getPuzzleName() {
        return _puzzleName;
    }

    public void setPuzzleName(String puzzleName) {
        this._puzzleName = puzzleName;
    }

    public String getUuid() {
        return _uuid;
    }

    public void setUuid(String uuid) {
        this._uuid = uuid;
    }
}
