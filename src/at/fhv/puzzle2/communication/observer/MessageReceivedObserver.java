package at.fhv.puzzle2.communication.observer;

import at.fhv.puzzle2.communication.observable.CommandReceivedObservable;

public interface MessageReceivedObserver {
    public void messageReceived(CommandReceivedObservable commandReceivedObservable);
}
