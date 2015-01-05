package at.fhv.puzzle2.communication.observer;

import at.fhv.puzzle2.communication.observable.MessageReceivedObservable;

public interface MessageReceivedObserver {
    public void messageReceived(MessageReceivedObservable messageReceivedObservable);
}
