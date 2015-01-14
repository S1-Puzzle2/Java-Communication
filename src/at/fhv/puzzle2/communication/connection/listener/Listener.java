package at.fhv.puzzle2.communication.connection.listener;

import java.io.IOException;

interface Listener {
    public void startListening() throws IOException;
    public void stopListening() throws  IOException;
}
