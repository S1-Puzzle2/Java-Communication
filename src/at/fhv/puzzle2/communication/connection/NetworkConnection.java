package at.fhv.puzzle2.communication.connection;

import java.io.IOException;

public interface NetworkConnection {
    public void sendBytes(byte[] data) throws IOException;
    public byte[] readBytes() throws IOException;

    public void close() throws IOException;
    public boolean isConnected();
}
