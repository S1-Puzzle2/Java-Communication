package at.fhv.puzzle2.communication.observable;

import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.observer.MessageReceivedObserver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class CommandReceivedObservable {
    private final List<MessageReceivedObserver> _observerList;
    private final BlockingQueue<Command> _messageQueue;

    public CommandReceivedObservable() {
        _observerList = Collections.synchronizedList(new LinkedList<>());
        _messageQueue = new LinkedBlockingQueue<>();
    }

    public void addObserver(MessageReceivedObserver observer) {
        _observerList.add(observer);
    }

    public boolean removeObserver(MessageReceivedObserver observer) {
        return _observerList.remove(observer);
    }

    public void appendMessage(Command message) {
        _messageQueue.add(message);

        _observerList.forEach(listener -> notifyInThread(listener::messageReceived));
    }

    /**
     *
     * @param consumer Consumer<ConnectionObservable>
     */
    private void notifyInThread(Consumer<CommandReceivedObservable> consumer) {
        //Run the notification in its own thread per listener, so the listeners wont block our network stack

        Runnable runnable = () -> consumer.accept(this);

        new Thread(runnable).start();
    }

    public synchronized List<Command> getMessageList() {
        List<Command> tmpList = _messageQueue.stream().collect(LinkedList::new, LinkedList::add, LinkedList::addAll);

        _messageQueue.clear();

        return tmpList;
    }
}
