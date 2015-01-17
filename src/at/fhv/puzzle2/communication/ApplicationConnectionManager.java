package at.fhv.puzzle2.communication;

import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.connection.CommandConnection;
import at.fhv.puzzle2.communication.application.listener.CommandListener;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ApplicationConnectionManager {
    private CommunicationManager _communicationManager;
    private List<CommandListener> _listenerList;

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
        for(int i = 0; i < _listenerList.size(); i++) {
            CommandListener listener = _listenerList.get(i);

            if(listener.getConnection().equals(connection)) {
                try {
                    listener.close();
                } catch (IOException e) {
                    //We dont care about it now, we close the listener anyway
                }

                //_listenerList.remove(i);

                break;
            }
        }

        _communicationManager.connectionClosed(connection);
    }

    public void commandRecieved(Command command) {
        _communicationManager.commandRecieved(command);
    }
}
