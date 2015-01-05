package at.fhv.puzzle2.communication.application.connection;

import at.fhv.puzzle2.communication.application.model.ApplicationMessage;

import java.io.IOException;

/**
 * Connection represents a Connection, which can send ApplicationMessages
 */
public interface ApplicationConnection {
    public void sendApplicationMessage(ApplicationMessage message) throws IOException;
    public ApplicationMessage readApplicationMessage() throws IOException;
    public void close() throws IOException;
}
