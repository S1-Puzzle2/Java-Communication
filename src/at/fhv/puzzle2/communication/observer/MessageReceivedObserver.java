package at.fhv.puzzle2.communication.observer;

import at.fhv.puzzle2.communication.application.command.Command;

public interface MessageReceivedObserver {
    public void messageReceived(Command command);
}
