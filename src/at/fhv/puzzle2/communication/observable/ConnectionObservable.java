package at.fhv.puzzle2.communication.observable;

import at.fhv.puzzle2.communication.ThreadUtil;
import at.fhv.puzzle2.communication.application.connection.CommandConnection;
import at.fhv.puzzle2.communication.observer.ConnectionObserver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.stream.Collectors.toCollection;

public class ConnectionObservable<T extends ConnectionObserver> {
    private final List<T> _observableList;
    private final BlockingQueue<CommandConnection> _connectionQueue;

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

        _observableList.forEach(listener -> ThreadUtil.runInThread(listener::notify, this));
    }

    public synchronized List<CommandConnection> getConnectionList() {
        List<CommandConnection> tmpList = _connectionQueue.stream().collect(toCollection(LinkedList::new));

        _connectionQueue.clear();

        return tmpList;
    }
}
