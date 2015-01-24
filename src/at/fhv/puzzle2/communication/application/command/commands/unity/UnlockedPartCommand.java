package at.fhv.puzzle2.communication.application.command.commands.unity;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;

public class UnlockedPartCommand extends Command {
    private Integer _unlockedPart;

    public UnlockedPartCommand(ClientID clientID) {
        super(clientID, CommandType.PartsUnlocked);
    }

    public void setUnlockedPartsList(Integer unlockedPart) {
        _unlockedPart = unlockedPart;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.UNLOCKED_PART, _unlockedPart);

        return super.toJSONString();
    }
}
