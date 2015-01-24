package at.fhv.puzzle2.communication.connection.protocoll.ethernet.udp;

import at.fhv.puzzle2.communication.connection.Broadcast;
import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.communication.connection.endpoint.DiscoverableEndPoint;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.Charset;

public class UDPEndpoint implements DiscoverableEndPoint {
    private DatagramSocket _serverSocket;

    private final String _host;
    private final int _port;

    public UDPEndpoint(String host, int port) {
        _host = host;
        _port = port;
    }

    @Override
    public NetworkConnection connect() throws IOException {
        return new UDPNetworkConnection(_host, _port);
    }

    @Override
    public void reserveBroadcastListener() throws IOException {
        _serverSocket = new DatagramSocket(_port);
    }

    @Override
    public void freeBroadcastListener() {
        _serverSocket.close();

        _serverSocket = null;
    }

    @Override
    public Broadcast receiveBroadcast() throws IOException {
        byte[] broadcastData = new byte[1024];

        DatagramPacket packet = new DatagramPacket(broadcastData, broadcastData.length);

        _serverSocket.receive(packet);

        String message = new String(broadcastData, Charset.forName("UTF-8"));
        return new Broadcast(new UDPNetworkConnection(packet.getAddress(), packet.getPort()), message);
    }
}
