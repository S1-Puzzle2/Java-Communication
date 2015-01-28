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

class NetworkManager {
    private final ListenerManager<ConnectionListener> _connectionListenerManager;
    private final ListenerManager<BroadcastListener> _broadcastListenerManager;

    private final String _broadcastResponse;

    private final NetworkConnectionManager _networkConnectionManager;
    private final NetworkPacketManager _networkPacketManager;

    private final BlockingQueue<NetworkConnection> _newConnectionQueue;

    public NetworkManager(CommunicationManager communicationManager, String broadcastResponse) {
        _newConnectionQueue = new LinkedBlockingQueue<>();

        _connectionListenerManager = new ListenerManager<>();
        _broadcastListenerManager = new ListenerManager<>();

        _broadcastResponse = broadcastResponse;

        _networkConnectionManager = new NetworkConnectionManager(communicationManager, _newConnectionQueue);

        NetworkPacketManager.initializeNetworkPacketManager(_networkConnectionManager);
        _networkPacketManager = NetworkPacketManager.getInstance();
    }

    public NetworkConnectionManager getNetworkConnectionManager() {
        return _networkConnectionManager;
    }

    void addConnectionListener(ListenableEndPoint listenableEndPoint) throws IOException {
        _connectionListenerManager.addListener(new ConnectionListener(listenableEndPoint, _newConnectionQueue));
    }

    void removeConnectionListener(ListenableEndPoint listenableEndPoint) {
        _connectionListenerManager.removeConnectionListener(new ConnectionListener(listenableEndPoint, _newConnectionQueue));
    }

    void startListeningForConnections() throws IOException {
        _connectionListenerManager.startListening();
    }

    void stopListeningForConnections() throws IOException {
        _connectionListenerManager.stopListening();
    }

    void addBroadcastListener(DiscoverableEndPoint discoverableEndPoint) throws IOException {
        _broadcastListenerManager.addListener(new BroadcastListener(discoverableEndPoint, _broadcastResponse));
    }

    void removeBroadcastListener(DiscoverableEndPoint discoverableEndPoint) {
        _broadcastListenerManager.removeConnectionListener(new BroadcastListener(discoverableEndPoint, _broadcastResponse));
    }

    void startListeningForBroadcasts() throws IOException {
        _broadcastListenerManager.startListening();
    }

    void stopListeningForBroadcasts()  throws IOException {
        _broadcastListenerManager.stopListening();
    }

    public void close() throws IOException {
        _connectionListenerManager.stopListening();
        _broadcastListenerManager.stopListening();

        _networkConnectionManager.close();
        _networkPacketManager.close();
    }
}
