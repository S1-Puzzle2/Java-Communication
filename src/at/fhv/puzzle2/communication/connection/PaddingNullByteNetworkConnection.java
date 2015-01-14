package at.fhv.puzzle2.communication.connection;

import at.fhv.puzzle2.communication.connection.util.ByteArrayOperations;

import java.io.IOException;

public class PaddingNullByteNetworkConnection extends NetworkConnectionDecorator {
    public PaddingNullByteNetworkConnection(NetworkConnection connection) {
        super(connection);
    }

    @Override
    public void sendBytes(byte[] data) throws IOException {
        this._connection.sendBytes(ByteArrayOperations.appendByteToArray(data, (byte) '\0'));
    }

    @Override
    public byte[] readBytes() throws IOException {
        byte[] receivedBytes = _connection.readBytes();

        byte[] buffer = new byte[receivedBytes.length - 1];

        System.arraycopy(receivedBytes, 0, buffer, 0, buffer.length);

        return buffer;
    }

    @Override
    public void close() throws IOException {
        _connection.close();
    }

    @Override
    public boolean isConnected() {
        return _connection.isConnected();
    }
}
