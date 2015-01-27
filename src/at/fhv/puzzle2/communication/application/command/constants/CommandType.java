package at.fhv.puzzle2.communication.application.command.constants;

import java.util.Objects;

public enum CommandType {
    Register("REGISTER"), Unknown("UNKNOWN"), Malformed("MALFORMED_COMMAND"),
    Registered("REGISTERED"), GameStart("GAME_START"), Ready("READY"),
    GetGameState("GET_GAME_STATE"), MobileGameStateResponse("MOBILE_GAME_STATE_RESPONSE"),
    UnityGameStateResponse("UNITY_GAME_STATE_RESPONSE"), GetPuzzlePart("GET_IMAGE"),
    Pause("PAUSE"), BarcodeScanned("BARCODE_SCANNED"), NotAllowed("NOT_ALLOWED"),
    ShowQuestion("SHOW_QUESTION"), SearchPuzzlePart("SHOW_IMAGE_SCREEN"),
    QuestionAnswered("ANSWERED_QUESTION"), BarcodeCorrect("BARCODE_CORRECT"),
    AnswerCorrect("ANSWER_CORRECT"), PartsUnlocked("PARTS_UNLOCKED"), PuzzleFinished("PUZZLE_FINISHED"),
    PuzzleList("PUZZLE_LIST"), ShowQR("SHOW_QR"), GameState("GAME_STATE"), PuzzlePart("IMAGE"),
    GameFinished("GAME_FINISHED"), CreatePuzzle("CREATE_PUZZLE"), CreatePuzzlePart("CREATE_PUZZLE_PART"),
    GetPuzzleList("GET_PUZZLE_LIST"), SetPuzzle("SET_PUZZLE"), ResetGame("RESET_GAME"), SolvePuzzle("SOLVE_PUZZLE");

    private final String _type;
    CommandType(String type) {
        _type = type;
    }

    public boolean isRightType(String commandType) {
        return Objects.equals(_type, commandType);
    }

    @Override
    public String toString() {
        return _type;
    }
}
