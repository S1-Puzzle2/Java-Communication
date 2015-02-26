package at.fhv.puzzle2.communication.connection.protocoll.ethernet.tcp;

import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.communication.connection.endpoint.ListenableEndPoint;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;

public class TCPEndpoint implements ListenableEndPoint {
    private ServerSocketChannel _socket;
    private final InetSocketAddress _localAddress;

    public TCPEndpoint(String host, int port) {
        _localAddress = new InetSocketAddress(host, port);
    }

    @Override
    public NetworkConnection acceptNetworkConnection() throws IOException {
        SocketChannel connection = _socket.accept();
        connection.configureBlocking(true);

        return new TCPNetworkConnection(connection);
    }

    @Override
    public void reserveListener() throws IOException {
        _socket = ServerSocketChannel.open();
        _socket.bind(_localAddress);
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
        SocketChannel connection = SocketChannel.open();
        connection.configureBlocking(true);

        connection.connect(_localAddress);
        return new TCPNetworkConnection(connection);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TCPEndpoint &&
                ((TCPEndpoint) obj)._localAddress.equals(this._localAddress) &&
                ((TCPEndpoint) obj)._socket.equals(this._socket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_localAddress);
    }
}
