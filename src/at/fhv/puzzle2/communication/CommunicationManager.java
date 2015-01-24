package at.fhv.puzzle2.communication;

import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.connection.ApplicationConnection;
import at.fhv.puzzle2.communication.application.connection.Base64ApplicationConnection;
import at.fhv.puzzle2.communication.application.connection.BaseApplicationConnection;
import at.fhv.puzzle2.communication.application.connection.CommandConnection;
import at.fhv.puzzle2.communication.application.connection.encryption.EncryptedApplicationConnection;
import at.fhv.puzzle2.communication.application.connection.encryption.Encryption;
import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.communication.connection.endpoint.DiscoverableEndPoint;
import at.fhv.puzzle2.communication.connection.endpoint.ListenableEndPoint;
import at.fhv.puzzle2.communication.connection.networkPacket.NetworkPacketHandler;
import at.fhv.puzzle2.communication.observable.CommandReceivedObservable;
import at.fhv.puzzle2.communication.observable.ConnectionObservable;
import at.fhv.puzzle2.communication.observer.ClosedConnectionObserver;
import at.fhv.puzzle2.communication.observer.MessageReceivedObserver;
import at.fhv.puzzle2.communication.observer.NewConnectionObserver;
import at.fhv.puzzle2.logging.Logger;

import java.io.IOException;
import java.util.*;

public class CommunicationManager {
    private static final String TAG = "communcation.CommunicationManager";

    private final List<CommandConnection> _connectionList;

    private final NetworkManager _networkManager;
    private final ApplicationConnectionManager _appConnectionManager;

    private final ConnectionObservable<NewConnectionObserver> _newConnectionObservable;
    private final ConnectionObservable<ClosedConnectionObserver> _closedConnectionObservable;
    private final CommandReceivedObservable _commandReceivedObservable;

    private Encryption _encryption = null;

    private CommunicationManager(String broadcastResponse, Encryption encryption) {
        _appConnectionManager = new ApplicationConnectionManager(this);

        _newConnectionObservable = new ConnectionObservable<>();
        _closedConnectionObservable = new ConnectionObservable<>();
        _commandReceivedObservable = new CommandReceivedObservable();

        _networkManager = new NetworkManager(this, broadcastResponse);

        _connectionList = Collections.synchronizedList(new LinkedList<>());

        _encryption = encryption;

        Logger.getLogger().info(TAG, "Communication-Stack initialized...");
    }

    public CommunicationManager(String broadcastTresponse) {
        this(broadcastTresponse, null);
    }

    public void startListeningForConnections() throws IOException {
        Logger.getLogger().trace(TAG, "Starting to listen for connections");

        _networkManager.startListeningForConnections();
    }

    public void stopListeningForConnections() throws IOException {
        Logger.getLogger().trace(TAG, "Stopped listening for connections");

        _networkManager.stopListeningForConnections();
    }

    public void startListeningForBroadcasts() throws IOException {
        Logger.getLogger().trace(TAG, "Starting to listen for broadcasts");

        _networkManager.startListeningForBroadcasts();
    }

    public void stopListeningForBroadcasts() throws IOException {
        Logger.getLogger().trace(TAG, "Stopped listening for broadcasts");

        _networkManager.stopListeningForBroadcasts();
    }

    public void addConnectionClosedObserver(ClosedConnectionObserver observer) {
        _closedConnectionObservable.addObserver(observer);
    }

    public boolean removeConnectionClosedObserver(ClosedConnectionObserver observer) {
        return _closedConnectionObservable.removeObserver(observer);
    }


    public void addConnectionListener(ListenableEndPoint endPoint) throws IOException {
        Logger.getLogger().trace(TAG, "Added connection listener");

        _networkManager.addConnectionListener(endPoint);
    }

    public void removeConnectionListener(ListenableEndPoint endPoint) throws IOException {
        _networkManager.removeConnectionListener(endPoint);
    }

    public void addBroadcastListener(DiscoverableEndPoint endPoint) throws IOException {
        _networkManager.addBroadcastListener(endPoint);
    }

    public void removeBroadcastListener(DiscoverableEndPoint endPoint) throws IOException {
        _networkManager.removeBroadcastListener(endPoint);
    }

    public void addNewConnectionObserver(NewConnectionObserver observer) {
        _newConnectionObservable.addObserver(observer);
    }

    public boolean removeNewConnectionObserver(NewConnectionObserver observer) {
        return _newConnectionObservable.removeObserver(observer);
    }

    public void addMessageReceivedObserver(MessageReceivedObserver observer) {
        _commandReceivedObservable.addObserver(observer);
    }

    public boolean removeMessageReceivedObserver(MessageReceivedObserver observable) {
        return _commandReceivedObservable.removeObserver(observable);
    }

    void commandRecieved(Command message) {
        _commandReceivedObservable.appendMessage(message);
    }

    void newConnectionEstablished(NetworkConnection networkConnection) {
        Logger.getLogger().debug(TAG, "New connection has been established");

        ApplicationConnection applicationConnection = new BaseApplicationConnection(new NetworkPacketHandler(networkConnection));
        applicationConnection = new Base64ApplicationConnection(applicationConnection);

        //If the user provided an encryption, use it
        if(_encryption != null) {
            Logger.getLogger().info(TAG, "Encryption is enabled");

            applicationConnection = new EncryptedApplicationConnection(applicationConnection, _encryption);
        }

        CommandConnection commandConnection = new CommandConnection(_appConnectionManager, applicationConnection);

        _connectionList.add(commandConnection);

        _appConnectionManager.listenForMessages(commandConnection);
        _newConnectionObservable.appendConnection(commandConnection);
    }

    void connectionClosed(CommandConnection connection) {
        Logger.getLogger().debug(TAG, "Connection has been lost");

        for(Iterator<CommandConnection> iterator = _connectionList.iterator(); iterator.hasNext(); ) {
            CommandConnection tmpConnection = iterator.next();

            if(Objects.equals(tmpConnection, connection)) {
                iterator.remove();
                _closedConnectionObservable.appendConnection(connection);

                return;
            }
        }
    }

    void connectionClosed(NetworkConnection networkConnection) {
        connectionClosed(new CommandConnection(_appConnectionManager,
                new BaseApplicationConnection(new NetworkPacketHandler(networkConnection))));
    }

    public void close() throws IOException {
        Logger.getLogger().info(TAG, "Shutting down the communication-stack...");

        //First stop all the send queues
        _connectionList.forEach(CommandConnection::stopSendQueue);

        _appConnectionManager.close();
        _networkManager.close();

    }
}
