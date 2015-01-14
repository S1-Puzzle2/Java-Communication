package at.fhv.puzzle2.communication.connection.endpoint;

import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.io.IOException;

public interface ListenableEndPoint extends EndPoint {
    NetworkConnection acceptNetworkConnection() throws IOException;

    void reserveListener() throws IOException;

    boolean freeListener() throws IOException;
}
