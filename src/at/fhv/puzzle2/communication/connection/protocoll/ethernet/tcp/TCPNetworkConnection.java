package at.fhv.puzzle2.communication.connection.protocoll.ethernet.tcp;

import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.communication.connection.util.ByteArrayOperations;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPNetworkConnection implements NetworkConnection {
    private Socket _socket;

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
        InputStream inStream = _socket.getInputStream();

        byte[] receivedBytes = new byte[0];
        byte[] buffer = new byte[1024];

        int bytesRead;
        while((bytesRead = inStream.read(buffer)) != -1) {
            receivedBytes = ByteArrayOperations.appendByteArray(receivedBytes, buffer, bytesRead);

            if(inStream.available() <= 0) {
                //We've read all bytes available
                break;
            }
        }

        return receivedBytes;
    }

    @Override
    public void close() throws IOException {
        _socket.close();
    }

    @Override
    public boolean isConnected() {
        return _socket != null && _socket.isConnected();
    }
}
