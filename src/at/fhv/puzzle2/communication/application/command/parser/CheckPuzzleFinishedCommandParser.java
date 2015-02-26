package at.fhv.puzzle2.communication.application.command.parser;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.commands.unity.CheckPuzzleFinishedCommand;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import org.json.simple.JSONArray;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CheckPuzzleFinishedCommandParser extends CommandParser {
    @Override
    public boolean canProcessMessage(String messageType) {
        return false;
    }

    @Override
    protected Command parse(ClientID clientID, HashMap<String, Object> messageData) throws MalformedCommandException {
        CheckPuzzleFinishedCommand command = new CheckPuzzleFinishedCommand(clientID);
        JSONArray jsonPartList = (JSONArray) messageData.get(CommandConstants.PART_LIST);

        List<Integer> partList = new LinkedList<>();
        for (int i = 0; i < jsonPartList.size(); i++) {
            partList.add(new BigDecimal((Long) jsonPartList.get(i)).intValueExact());
        }

        command.setPartList(partList);

        return command;
    }
}
