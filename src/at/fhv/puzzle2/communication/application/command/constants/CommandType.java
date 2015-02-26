package at.fhv.puzzle2.communication.application.command.constants;

import java.util.Objects;

public enum CommandType {
    Register("REGISTER"), Unknown("UNKNOWN"), Malformed("MALFORMED_COMMAND"),
    Registered("REGISTERED"), GameStart("GAME_START"), Ready("READY"),
    GetGameInfo("GET_GAME_INFO"), GameInfo("GAME_INFO"), GetPuzzlePart("GET_PUZZLE_PART"), GamePaused("GAME_PAUSED"),
    PartScanned("PART_SCANNED"), NotAllowed("NOT_ALLOWED"),
    AnswerQuestion("ANSWER_QUESTION"), SearchPart("SEARCH_PART"),
    QuestionAnswered("QUESTION_ANSWERED"), BarcodeCorrect("BARCODE_CORRECT"),
    AnswerCorrect("ANSWER_CORRECT"), PartUnlocked("PART_UNLOCKED"), AllPartsUnlocked("ALL_PARTS_UNLOCKED"),
    PuzzleList("PUZZLE_LIST"), ShowQR("SHOW_QR"), PuzzlePart("PUZZLE_PART"),
    GameFinished("GAME_FINISHED"), CreatePuzzle("CREATE_PUZZLE"), CreatePuzzlePart("CREATE_PUZZLE_PART"),
    GetPuzzleList("GET_PUZZLE_LIST"), SetPuzzle("SET_PUZZLE"), ResetGame("RESET_GAME"),
    SolvePuzzle("SOLVE_PUZZLE"), GetPuzzlePartList("GET_PUZZLE_PART_LIST"), PuzzlePartList("PUZZLE_PART_LIST"),
    RegisterGameStatusListener("REGISTER_GAME_STATUS_LISTENER"), GameStatusChanged("GAME_STATUS_CHANGED"),
    NoQuestionsLeft("NO_QUESTIONS_LEFT"), PuzzleCreated("PUZZLE_CREATED"), PuzzlePartCreated("PUZZLE_PART_CREATED"), CheckPuzzleFinished("CHECK_PUZZLE_FINISHED");

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
