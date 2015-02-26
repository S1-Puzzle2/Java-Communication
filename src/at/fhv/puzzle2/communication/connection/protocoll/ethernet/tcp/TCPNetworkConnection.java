package at.fhv.puzzle2.communication.connection.protocoll.ethernet.tcp;

import at.fhv.puzzle2.communication.ConnectionClosedException;
import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPNetworkConnection implements NetworkConnection {
    private final SocketChannel _socket;
    private final ByteBuffer _localBuffer = ByteBuffer.allocate(4096);

    public TCPNetworkConnection(SocketChannel socket) {
        _socket = socket;
    }

    @Override
    public void sendBytes(byte[] data) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        _socket.write(buffer);
    }

    @Override
    public byte[] readBytes() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        while(true) {
            int result = _socket.read(_localBuffer);

            _localBuffer.flip();

            if(result == -1) {
                throw new ConnectionClosedException();
            }


            while(_localBuffer.hasRemaining()) {
                byte tmp = _localBuffer.get();
                buffer.write(tmp);

                if(tmp == '\0') {
                    _localBuffer.compact();
                    return buffer.toByteArray();
                }
            }

            _localBuffer.clear();
        }
    }

    @Override
    public void close() throws IOException {
        _socket.close();
    }

    @Override
    public boolean isConnected() {
        return _socket != null && _socket.isOpen();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof TCPNetworkConnection && ((TCPNetworkConnection)object)._socket.equals(this._socket);
    }

    @Override
    public int hashCode() {
        return _socket.hashCode();
    }
}
