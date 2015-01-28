package at.fhv.puzzle2.communication;

import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.connection.CommandConnection;
import at.fhv.puzzle2.communication.application.listener.CommandListener;

import java.io.IOException;
import java.util.*;

public class ApplicationConnectionManager {
    private final CommunicationManager _communicationManager;
    private final List<CommandListener> _listenerList;

    public ApplicationConnectionManager(CommunicationManager communicationManager) {
        _communicationManager = communicationManager;
        _listenerList = Collections.synchronizedList(new LinkedList<>());
    }

    public void listenForMessages(CommandConnection connection) {
        _listenerList.add(new CommandListener(this, connection));
    }

    public void close() throws IOException {
        _listenerList.forEach(CommandListener::close);
    }

    public synchronized void closeListener(CommandConnection connection) {
        //Close the listener now and remove it from the list
        Iterator<CommandListener> iterator = _listenerList.iterator();
        while(iterator.hasNext()) {
            CommandListener listener = iterator.next();

            if(Objects.equals(listener.getConnection(), connection)) {
                listener.close();
                iterator.remove();

                break;
            }
        }
    }

    public synchronized void connectionClosed(CommandConnection connection) {
        closeListener(connection);

        _communicationManager.connectionClosed(connection);
    }

    public void commandRecieved(Command command) {
        _communicationManager.commandRecieved(command);
    }
}
