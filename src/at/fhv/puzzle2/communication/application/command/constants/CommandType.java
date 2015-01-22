package at.fhv.puzzle2.communication.application.command.constants;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public enum CommandType {
    Register("REGISTER"), Unknown("UNKNOWN"), Malformed("MALFORMED_COMMAND"),
    Registered("REGISTERED"), GameStart("GAME_START"), Ready("READY"),
    GetGameState("GET_GAME_STATE"), MobileGameStateResponse("MOBILE_GAME_STATE_RESPONSE"),
    UnityGameStateResponse("UNITY_GAME_STATE_RESPONSE"), GetPuzzlePart("GET_PUZZLE_PART"),
    Pause("PAUSE"), BarcodeScanned("BARCODE_SCANNED"), NotAllowed("NOT_ALLOWED"),
    ShowQuestion("SHOW_QUESTION"), SearchPuzzlePart("SEARCH_PUZZLE_PART"),
    QuestionAnswered("QUESTION_ANSWERED"), BarcodeCorrect("BARCODE_CORRECT"),
    AnswerCorrect("ANSWER_CORRECT"), PartsUnlocked("PARTS_UNLOCKED"), PuzzleFinished("PUZZLE_FINISHED"),
    PuzzleList("PUZZLE_LIST"), ShowQR("SHOW_QR"), GameState("GAME_STATE");

    private String _type;
    CommandType(String type) {
        _type = type;
    }

    public boolean isRightType(String commandType) {
        return Objects.equals(_type, commandType);
    }

    //Construct a static list with all enum values in it
    private static final HashMap<String, CommandType> _typeList = new LinkedHashMap<>();
    static {
        for(CommandType type : values()) {
            _typeList.put(type.toString(), type);
        }
    }

    public CommandType getTypeByString(String commandType) {
        if(_typeList.containsKey(commandType)) {
            return _typeList.get(commandType);
        }

        return Unknown;
    }

    @Override
    public String toString() {
        return _type;
    }
}
