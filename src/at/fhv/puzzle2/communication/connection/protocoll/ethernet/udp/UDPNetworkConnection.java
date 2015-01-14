package at.fhv.puzzle2.communication.connection.protocoll.ethernet.udp;

import at.fhv.puzzle2.communication.ConnectionClosedException;
import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.io.IOException;
import java.net.*;

public class UDPNetworkConnection implements NetworkConnection {
    private InetAddress _host;
    private int _port;

    private DatagramSocket _socket;

    public UDPNetworkConnection(String host, int port) throws SocketException, UnknownHostException {
        this(InetAddress.getByName(host), port);
    }

    public UDPNetworkConnection(InetAddress host, int port) throws SocketException {
        _host = host;
        _port = port;

        _socket = new DatagramSocket();
    }

    @Override
    public void sendBytes(byte[] data) throws ConnectionClosedException {
        DatagramPacket packet = new DatagramPacket(data, data.length, _host, _port);

        try {
            _socket.send(packet);
        } catch (IOException e) {
            throw new ConnectionClosedException();
        }
    }

    @Override
    public byte[] readBytes() throws ConnectionClosedException {
        return null;
    }

    @Override
    public void close() throws IOException {
        _socket.close();
    }

    @Override
    public boolean isConnected() {
        return _socket.isConnected();
    }
}
