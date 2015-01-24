package at.fhv.puzzle2.communication;

import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.connection.CommandConnection;
import at.fhv.puzzle2.communication.application.listener.CommandListener;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
        for(CommandListener listener: _listenerList) {
            listener.close();
        }
    }

    public synchronized void connectionClosed(CommandConnection connection) {
        //Close the listener now
        for (CommandListener listener : _listenerList) {
            if (listener.getConnection().equals(connection)) {
                listener.close();

                break;
            }
        }

        _communicationManager.connectionClosed(connection);
    }

    public void commandRecieved(Command command) {
        _communicationManager.commandRecieved(command);
    }
}
