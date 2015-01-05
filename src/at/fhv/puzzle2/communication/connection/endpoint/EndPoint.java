package at.fhv.puzzle2.communication.connection.endpoint;

import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.io.IOException;

public interface EndPoint {
        public NetworkConnection connect() throws IOException;
}
