package at.fhv.puzzle2.communication.observable;

import at.fhv.puzzle2.communication.ThreadUtil;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.observer.MessageReceivedObserver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CommandReceivedObservable {
    private final List<MessageReceivedObserver> _observerList;

    public CommandReceivedObservable() {
        _observerList = Collections.synchronizedList(new LinkedList<>());
    }

    public void addObserver(MessageReceivedObserver observer) {
        _observerList.add(observer);
    }

    public boolean removeObserver(MessageReceivedObserver observer) {
        return _observerList.remove(observer);
    }

    public void appendMessage(Command command) {
        _observerList.forEach(listener -> ThreadUtil.runInThread(listener::messageReceived, command));
    }
}
