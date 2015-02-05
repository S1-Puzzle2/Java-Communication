package at.fhv.puzzle2.communication.observable;

import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.observer.MessageReceivedObserver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

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
        _observerList.forEach(listener -> notifyInThread(listener::messageReceived, command));
    }

    /**
     *
     * @param consumer Consumer<ConnectionObservable>
     */
    private void notifyInThread(Consumer<Command> consumer, final Command command) {
        //Run the notification in its own thread per listener, so the listeners wont block our network stack

        Runnable runnable = () -> consumer.accept(command);

        new Thread(runnable).start();
    }
}
