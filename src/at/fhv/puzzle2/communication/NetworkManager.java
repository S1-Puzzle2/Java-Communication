package at.fhv.puzzle2.communication;

import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.communication.connection.endpoint.DiscoverableEndPoint;
import at.fhv.puzzle2.communication.connection.endpoint.ListenableEndPoint;
import at.fhv.puzzle2.communication.connection.listener.BroadcastListener;
import at.fhv.puzzle2.communication.connection.listener.ConnectionListener;
import at.fhv.puzzle2.communication.connection.listener.ListenerManager;
import at.fhv.puzzle2.communication.connection.networkPacket.NetworkPacketManager;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkManager {
    private ListenerManager<ConnectionListener> _connectionListenerManager;
    private ListenerManager<BroadcastListener> _broadcastListenerManager;

    private String _broadcastResponse;

    private NetworkConnectionManager _networkConnectionManager;
    private NetworkPacketManager _networkPacketManager;

    private BlockingQueue<NetworkConnection> _newConnectionQueue;

    public NetworkManager(CommunicationManager communicationManager, String broadcastResponse) {
        _newConnectionQueue = new LinkedBlockingQueue<>();

        _connectionListenerManager = new ListenerManager<>();
        _broadcastListenerManager = new ListenerManager<>();

        _broadcastResponse = broadcastResponse;

        _networkConnectionManager = new NetworkConnectionManager(communicationManager, _newConnectionQueue);
        _networkPacketManager = NetworkPacketManager.getInstance();
    }

    protected void addConnectionListener(ListenableEndPoint listenableEndPoint) throws IOException {
        _connectionListenerManager.addListener(new ConnectionListener(listenableEndPoint, _newConnectionQueue));
    }

    protected void removeConnectionListener(ListenableEndPoint listenableEndPoint) throws IOException {
        _connectionListenerManager.removeConnectionListener(new ConnectionListener(listenableEndPoint, _newConnectionQueue));
    }

    protected void startListeningForConnections() throws IOException {
        _connectionListenerManager.startListening();
    }

    protected void stopListeningForConnections() throws IOException {
        _connectionListenerManager.stopListening();
    }

    protected void addBroadcastListener(DiscoverableEndPoint discoverableEndPoint) throws IOException {
        _broadcastListenerManager.addListener(new BroadcastListener(discoverableEndPoint, _broadcastResponse));
    }

    protected void removeBroadcastListener(DiscoverableEndPoint discoverableEndPoint) throws IOException {
        _broadcastListenerManager.removeConnectionListener(new BroadcastListener(discoverableEndPoint, _broadcastResponse));
    }

    protected void startListeningForBroadcasts() throws IOException {
        _broadcastListenerManager.startListening();
    }

    protected void stopListeningForBroadcasts()  throws IOException {
        _broadcastListenerManager.stopListening();
    }

    public void close() throws IOException {
        _connectionListenerManager.stopListening();
        _broadcastListenerManager.stopListening();

        _networkConnectionManager.close();
        _networkPacketManager.close();
    }
}
