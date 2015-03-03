package at.fhv.puzzle2.communication.connection.protocoll.ethernet.tcp;

import at.fhv.puzzle2.communication.ConnectionClosedException;
import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class TCPNetworkConnection implements NetworkConnection {
    private final Socket _socket;
    private InputStream _inputStream;
    private OutputStream _outputStream;

    private final ByteBuffer _localBuffer = ByteBuffer.allocate(4096);

    public TCPNetworkConnection(Socket socket) {
        _socket = socket;
        try {
            _inputStream = _socket.getInputStream();
            _outputStream = _socket.getOutputStream();
        } catch (IOException e) {
            //Dont do anything here
        }
    }

    @Override
    public void sendBytes(byte[] data) throws IOException {
        byte[] sendBytes = new byte[data.length + 1];
        System.arraycopy(data, 0, sendBytes, 0, data.length);

        sendBytes[data.length] = '\0';

        _outputStream.write(sendBytes);
    }

    @Override
    public byte[] readBytes() throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while(true) {
            int result = _inputStream.read();

            if(result == -1) {
                throw new ConnectionClosedException();
            }

            if(result == '\0') {
                return bos.toByteArray();
            }

            bos.write(result);
        }
    }

    @Override
    public void close() throws IOException {
        _socket.close();
    }

    @Override
    public boolean isConnected() {
        return _socket != null && _socket.isConnected();
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
