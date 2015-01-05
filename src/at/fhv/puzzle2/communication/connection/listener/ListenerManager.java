package at.fhv.puzzle2.communication.connection.listener;


import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ListenerManager<T extends Listener> {
    private List<T> _listenerList;
    private boolean _listeningForConnections = false;

    public ListenerManager() {
        _listenerList = Collections.synchronizedList(new LinkedList<>());
    }

    public void addListener(T listener) throws IOException {
        if(_listeningForConnections) {
            listener.startListening();
        }

        _listenerList.add(listener);
    }

    public boolean removeConnectionListener(T listener) {
        return _listenerList.remove(listener);
    }

    public void startListening() throws IOException {
        _listeningForConnections = true;

        for(T listener: _listenerList) {
            listener.startListening();
        }
    }

    public void stopListening()  throws IOException {
        _listeningForConnections = false;

        for(T listener: _listenerList) {
            listener.stopListening();
        }
    }
}
