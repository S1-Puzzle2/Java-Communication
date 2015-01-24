package at.fhv.puzzle2.communication.connection.endpoint;

import at.fhv.puzzle2.communication.connection.Broadcast;

import java.io.IOException;

public interface DiscoverableEndPoint extends EndPoint {
    public void reserveBroadcastListener() throws IOException;
    public void freeBroadcastListener();

    public Broadcast receiveBroadcast() throws IOException;
}
