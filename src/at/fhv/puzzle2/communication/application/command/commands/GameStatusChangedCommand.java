package at.fhv.puzzle2.communication.application.command.commands;

import at.fhv.puzzle2.communication.ClientID;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.constants.CommandConstants;
import at.fhv.puzzle2.communication.application.command.constants.CommandType;
import at.fhv.puzzle2.communication.application.command.dto.TeamStatusDTO;

public class GameStatusChangedCommand extends Command {
    private TeamStatusDTO _firstTeamStatus;
    private TeamStatusDTO _secondTeamStatus;

    public GameStatusChangedCommand(ClientID clientID) {
        super(clientID, CommandType.GameStatusChanged);
    }

    public void setFirstTeamStatus(TeamStatusDTO teamStatus) {
        _firstTeamStatus = teamStatus;
    }

    public void setSecondTeamStatus(TeamStatusDTO teamStatus) {
        _secondTeamStatus = teamStatus;
    }

    @Override
    public String toJSONString() {
        _messageData.put(CommandConstants.FIRST_TEAM, _firstTeamStatus);
        _messageData.put(CommandConstants.SECOND_TEAM, _secondTeamStatus);

        return super.toJSONString();
    }
}
