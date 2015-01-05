package at.fhv.puzzle2.communication.observable;

import at.fhv.puzzle2.communication.application.connection.ApplicationConnection;
import at.fhv.puzzle2.communication.observer.ConnectionObserver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class ConnectionObservable<T extends ConnectionObserver> {
    private List<T> _observableList;
    private BlockingQueue<ApplicationConnection> _connectionQueue;

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

    public void appendConnection(ApplicationConnection newConnection) {
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

    public List<ApplicationConnection> getConnectionList() {
        List<ApplicationConnection> tmpList = _connectionQueue.stream().collect(LinkedList::new, LinkedList::add, LinkedList::addAll);

        _connectionQueue.clear();

        return tmpList;
    }
}
