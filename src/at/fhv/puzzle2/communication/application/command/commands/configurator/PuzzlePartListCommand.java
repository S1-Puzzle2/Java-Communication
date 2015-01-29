package at.fhv.puzzle2.communication.application.command.commands.configurator;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class PuzzlePartListCommand extends Command {
    private String _puzzleName;
    private List<DummyPuzzlePart> _partList = new LinkedList<>();

    public PuzzlePartListCommand(ClientID clientID) {
        super(clientID, CommandType.PuzzlePartList);
    }

    public void setPartList(List<DummyPuzzlePart> partList) {
        _partList = partList;
    }

    public void setPuzzleName(String name) {
        _puzzleName = name;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.PUZZLE_NAME, _puzzleName);
        _messageData.put(CommandConstants.PUZZLE_PART_LIST, _partList);

        return super.toJSONString();
    }

    public class DummyPuzzlePart implements JSONAware {
        final int puzzlePartID;
        final String barCode;

        public DummyPuzzlePart(Integer puzzlePartID, String barCode) {
            this.puzzlePartID = puzzlePartID;
            this.barCode = barCode;
        }

        @Override
        public String toJSONString() {
            JSONObject json = new JSONObject();
            json.put(CommandConstants.PUZZLE_PART_ID, puzzlePartID);
            json.put(CommandConstants.BAR_CODE, barCode);

            return json.toJSONString();
        }
    }
}
