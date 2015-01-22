package at.fhv.puzzle2.communication.application.command.constants;

public class CommandConstants {
    public static final String MESSAGE_TYPE = "msgType";
    public static final String MESSAGE_DATA = "msgData";
    public static final String CLIENT_ID = "clientID";

    //Use in UnknownCommand/MalformedCommand to include the application message
    public static final String APPLICATION_MESSAGE = "message";

    //Register-Command
    public static final String CLIENT_TYPE = "clientType";

    //Registered-Command
    public static final String SUCCESS = "success";

    //GetPuzzlePart-Command
    public static final String PUZZLE_PART_ID = "imageID";
    public static final String BAR_CODE = "barCode";
    public static final String NOT_ALLOWED_COMMAND_TYPE = "notAllowedCommandType";

    //ShowQuestion-Command
    public static final String QUESTION_ID = "questionID";
    public static final String QUESTION_TEXT = "questionText";
    public static final String QUESTION_ANSWERS = "questionAnswers";
    public static final String ANSWER_ID = "answerID";

    public static final String IS_CORRECT = "isCorrect";
    public static final String UNLOCKED_PARTS = "unlockedParts";
    public static final String PUZZLE_LIST = "puzzleList";
    public static final String PUZZLE_NAME = "gameName";
    public static final String TEAM_NAME = "teamName";
    public static final String PUZZLE_PARTS_LIST = "imageIDs";
    public static final String IMAGE = "base64Image";
    public static final String QR_CODE = "qrCode";
}
