package at.fhv.puzzle2.communication.connection.protocoll.ethernet.tcp;

import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.communication.connection.endpoint.ListenableEndPoint;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;

public class TCPEndpoint implements ListenableEndPoint {
    private ServerSocket _socket;
    private String _host;
    private int _port;

    public TCPEndpoint(String host, int port) {
        _host = host;
        _port = port;
    }

    @Override
    public NetworkConnection acceptNetworkConnection() throws IOException {
        try {
            Socket tmp = _socket.accept();

            return new TCPNetworkConnection(tmp);
        } catch (SocketException e) {
            //This happens, when we interrupt accept, so do nothing here

            return null;
        }
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
        return new TCPNetworkConnection(new Socket(_host, _port));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TCPEndpoint && ((TCPEndpoint) obj)._host.equals(this._host) && ((TCPEndpoint) obj)._port == this._port;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_host, _port);
    }
}
