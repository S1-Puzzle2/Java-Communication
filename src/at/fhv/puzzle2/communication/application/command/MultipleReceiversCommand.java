package at.fhv.puzzle2.communication.application.command;

import at.fhv.puzzle2.communication.ClientID;

public interface MultipleReceiversCommand {
    public Command createCopyWithDifferentClientID(ClientID clientID);
}
