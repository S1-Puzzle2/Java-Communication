package at.fhv.puzzle2.communication.application.connection;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.io.IOException;

/**
 * Connection represents a Connection, which can send ApplicationMessages
 */
public interface ApplicationConnection {
    void sendApplicationMessage(ApplicationMessage command);
    ApplicationMessage receiveMessage();
    void close() throws IOException;
    NetworkConnection getUnderlyingConnection();
}
