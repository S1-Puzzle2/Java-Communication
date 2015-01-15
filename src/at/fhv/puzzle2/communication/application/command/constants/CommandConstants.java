package at.fhv.puzzle2.communication.application.command.constants;

/**
 * Created by sinz on 15.01.2015.
 */
public class CommandConstants {
    public static final String MESSAGE_TYPE = "msgType";
    public static final String MESSAGE_DATA = "msgData";
    public static final String CLIENT_ID = "clientID";

    //Use in UnknownCommand/MalformedCommand to include the application message
    public static final String APPLICATION_MESSAGE = "message";

    //Register-Command
    public static final String CLIENT_TYPE = "clientType";

    //Registered-Command
    public static final String REGISTERED = "registered";
}
