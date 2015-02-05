package at.fhv.puzzle2.communication.application.connection;

import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.util.Optional;

/**
 * Connection represents a Connection, which can send ApplicationMessages
 */
public interface ApplicationConnection {
    void sendApplicationMessage(ApplicationMessage command);
    Optional<ApplicationMessage> receiveMessage();
    void close();
    NetworkConnection getUnderlyingConnection();
}
