package at.fhv.puzzle2.communication.connection;

import java.io.IOException;

public class PaddingNullByteNetworkConnection extends NetworkConnectionDecorator {
    public PaddingNullByteNetworkConnection(NetworkConnection connection) {
        super(connection);
    }

    @Override
    public void sendBytes(byte[] data) throws IOException {
        this._connection.sendBytes(appendByteToArray(data, (byte) '\0'));
    }

    private static byte[] appendByteToArray(byte[] destination, byte src) {
        byte[] tmpArray = new byte[destination.length + 1];
        System.arraycopy(destination, 0, tmpArray, 0, destination.length);
        tmpArray[tmpArray.length - 1] = src;

        return tmpArray;
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
