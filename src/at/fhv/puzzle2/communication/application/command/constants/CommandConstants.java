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

    public static final String ID = "id";

    public static final String NAME = "name";

    //GetPuzzlePart-Command
    public static final String BAR_CODE = "barCode";
    public static final String NOT_ALLOWED_COMMAND_TYPE = "notAllowedCommandType";

    public static final String IS_CORRECT = "isCorrect";
    public static final String PUZZLE_LIST = "puzzleList";
    public static final String TEAM_NAME = "teamName";
    public static final String BASE64IMAGE = "base64Image";
    public static final String QR_CODE = "qrCode";
    public static final String TIME = "time";
    public static final String CORRECT_ANSWER_ID = "correctAnswerID";
    public static final String IS_WINNING = "isWinning";
    public static final String PUZZLE_PART_ORDER = "order";
    public static final String UNLOCKED_PARTS_LIST = "unlockedImageIDs";
    public static final String PUZZLE = "puzzle";

    //Team constnats
    public static final String FIRST_TEAM_NAME = "firstTeamName";
    public static final String SECOND_TEAM_NAME = "secondTeamName";
    public static final String FIRST_TEAM = "firstTeam";
    public static final String SECOND_TEAM = "secondTeam";

    //Clients constants
    public static final String CLIENTS = "clients";
    public static final String UNITY_CIENT = "unity";
    public static final String MOBILE_CLIENT = "mobile";

    //Status constants
    public static final String COUNT_REMAINING_PARTS = "countRemainingParts";
    public static final String COUNT_REMAINING_QUESTIONS = "countRemainingQuestions";
    public static final String PART_LIST = "partList";
    public static final String PUZZLE_ID = "puzzleID";
    public static final String TEXT = "text";
    public static final String ANSWERS = "answers";
}
