package at.fhv.puzzle2.communication;

import at.fhv.puzzle2.communication.application.connection.ApplicationConnection;
import at.fhv.puzzle2.communication.application.listener.MessageListener;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ApplicationConnectionManager {
    private List<MessageListener> _listenerList;

    public ApplicationConnectionManager() {
        _listenerList = Collections.synchronizedList(new LinkedList<MessageListener>());
    }

    public void listenForMessages(CommunicationManager communicationManager, ApplicationConnection connection) {
        _listenerList.add(new MessageListener(communicationManager, connection));
    }

    public void close() throws IOException {
        for(MessageListener listener: _listenerList) {
            listener.close();
        }
    }
}
