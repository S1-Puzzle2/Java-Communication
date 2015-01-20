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

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CommunicationManager {
    private List<CommandConnection> _connectionList;

    private NetworkManager _networkManager;
    private ApplicationConnectionManager _appConnectionManager;

    private ConnectionObservable<NewConnectionObserver> _newConnectionObservable;
    private ConnectionObservable<ClosedConnectionObserver> _closedConnectionObservable;
    private CommandReceivedObservable _commandReceivedObservable;

    private Encryption _encryption = null;

    public CommunicationManager(String broadcastResponse, Encryption encryption) {
        _appConnectionManager = new ApplicationConnectionManager(this);

        _newConnectionObservable = new ConnectionObservable<>();
        _closedConnectionObservable = new ConnectionObservable<>();
        _commandReceivedObservable = new CommandReceivedObservable();

        _networkManager = new NetworkManager(this, broadcastResponse);

        _connectionList = Collections.synchronizedList(new LinkedList<>());

        _encryption = encryption;
    }

    public CommunicationManager(String broadcastTresponse) {
        this(broadcastTresponse, null);
    }

    public void startListeningForConnections() throws IOException {
        _networkManager.startListeningForConnections();
    }

    public void stopListeningForConnections() throws IOException {
        _networkManager.stopListeningForConnections();
    }

    public void startListeningForBroadcasts() throws IOException {
        _networkManager.startListeningForBroadcasts();
    }

    public void stopListeningForBroadcasts() throws IOException {
        _networkManager.stopListeningForBroadcasts();
    }

    public void addConnectionClosedObserver(ClosedConnectionObserver observer) {
        _closedConnectionObservable.addObserver(observer);
    }

    public boolean removeConnectionClosedObserver(ClosedConnectionObserver observer) {
        return _closedConnectionObservable.removeObserver(observer);
    }


    public void addConnectionListener(ListenableEndPoint endPoint) throws IOException {
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

    protected void commandRecieved(Command message) {
        _commandReceivedObservable.appendMessage(message);
    }

    protected void newConnectionEstablished(NetworkConnection networkConnection) {
        ApplicationConnection applicationConnection = new BaseApplicationConnection(new NetworkPacketHandler(networkConnection));
        applicationConnection = new Base64ApplicationConnection(applicationConnection);

        //If the user provided an encryption, use it
        if(_encryption != null) {
            applicationConnection = new EncryptedApplicationConnection(applicationConnection, _encryption);
        }

        CommandConnection commandConnection = new CommandConnection(_appConnectionManager, applicationConnection);

        _connectionList.add(commandConnection);

        _appConnectionManager.listenForMessages(commandConnection);
        _newConnectionObservable.appendConnection(commandConnection);
    }

    protected void connectionClosed(CommandConnection connection) {
        boolean found = false;
        int i;
        for(i = 0; i < _connectionList.size(); i++) {
            if(Objects.equals(_connectionList.get(i), (connection))) {
                found = true;
                break;
            }
        }

        //Its possible, that we receive 2 different connection closed events for a single connection, so only propagate one
        if(found) {
            _connectionList.remove(i);
            _closedConnectionObservable.appendConnection(connection);
        }
    }

    protected void connectionClosed(NetworkConnection networkConnection) {
        connectionClosed(new CommandConnection(_appConnectionManager,
                new BaseApplicationConnection(new NetworkPacketHandler(networkConnection))));
    }

    public void close() throws IOException {
        _appConnectionManager.close();
        _networkManager.close();
    }
}
