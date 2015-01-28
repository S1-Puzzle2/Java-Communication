package at.fhv.puzzle2.communication.connection.protocoll.ethernet.tcp;

import at.fhv.puzzle2.communication.ConnectionClosedException;
import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class TCPNetworkConnection implements NetworkConnection {
    private final Socket _socket;

    public TCPNetworkConnection(Socket socket) {
        _socket = socket;
    }

    @Override
    public void sendBytes(byte[] data) throws IOException {
        OutputStream outStream = _socket.getOutputStream();
        outStream.write(data);
        outStream.flush();
    }

    @Override
    public byte[] readBytes() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            InputStream inStream = _socket.getInputStream();

            byte tmpByte;
            while((tmpByte = (byte) inStream.read()) != -1) {
                buffer.write(tmpByte);

                if(tmpByte == '\0') {
                    break;
                }
            }

            if(tmpByte == -1) {
                throw new ConnectionClosedException();
            }

            return buffer.toByteArray();
        } catch(SocketException e) {
            //Occurs when the other end closes the connection
            throw new ConnectionClosedException();
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
