package at.fhv.puzzle2.communication.connection.protocoll.ethernet.tcp;

import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.communication.connection.endpoint.ListenableEndPoint;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class TCPEndpoint implements ListenableEndPoint {
    private ServerSocket _socket;
    //private final InetAddress _localAddress;
    private String _host;
    private int _port;

    public TCPEndpoint(String host, int port) {
        _host = host;
        _port = port;
    }

    @Override
    public NetworkConnection acceptNetworkConnection() throws IOException {
        return new TCPNetworkConnection(_socket.accept());
    }

    @Override
    public void reserveListener() throws IOException {
        _socket = new ServerSocket(_port);
    }

    @Override
    public boolean freeListener() throws IOException {
        if(_socket != null) {
            _socket.close();

            _socket = null;

            return true;
        }

        return false;
    }

    @Override
    public NetworkConnection connect() throws IOException {
        Socket socket = new Socket(_host, _port);

        return new TCPNetworkConnection(socket);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TCPEndpoint &&
                 ((TCPEndpoint) obj)._socket.equals(this._socket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_host, _port);
    }
}
