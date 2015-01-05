package at.fhv.puzzle2.communication.connection.endpoint;

import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.io.IOException;

public interface ListenableEndPoint extends EndPoint {
    public NetworkConnection acceptNetworkConnection() throws IOException;

    public void reserveListener() throws IOException;

    public boolean freeListener() throws IOException;
}
