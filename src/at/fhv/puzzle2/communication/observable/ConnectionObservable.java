package at.fhv.puzzle2.communication.observable;

import at.fhv.puzzle2.communication.application.connection.ApplicationConnection;
import at.fhv.puzzle2.communication.application.connection.CommandConnection;
import at.fhv.puzzle2.communication.observer.ConnectionObserver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toCollection;

public class ConnectionObservable<T extends ConnectionObserver> {
    private List<T> _observableList;
    private BlockingQueue<CommandConnection> _connectionQueue;

    public ConnectionObservable() {
        _observableList = Collections.synchronizedList(new LinkedList<>());

        _connectionQueue = new LinkedBlockingQueue<>();
    }

    public void addObserver(T listener) {
        _observableList.add(listener);
    }

    public boolean removeObserver(T listener) {
        return _observableList.remove(listener);
    }

    public void appendConnection(CommandConnection newConnection) {
        _connectionQueue.add(newConnection);

        _observableList.forEach(listener -> notifyInThread(listener::notify));
    }


    /**
     *
     * @param consumer Consumer<ConnectionObservable>
     */
    protected void notifyInThread(Consumer<ConnectionObservable> consumer) {
        //Run the notification in its own thread per listener, so the listeners wont block our network stack

        Runnable runnable = () -> consumer.accept(this);

        new Thread(runnable).start();
    }

    public List<CommandConnection> getConnectionList() {
        List<CommandConnection> tmpList = _connectionQueue.stream().collect(toCollection(LinkedList::new));

        _connectionQueue.clear();

        return tmpList;
    }
}
