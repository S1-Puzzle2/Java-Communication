package at.fhv.puzzle2.communication.observer;

import at.fhv.puzzle2.communication.observable.ConnectionObservable;

public interface ConnectionObserver {
    public void notify(ConnectionObservable observable);
}
