package at.fhv.puzzle2.communication;

import at.fhv.puzzle2.communication.application.connection.ApplicationConnection;
import at.fhv.puzzle2.communication.application.connection.Base64ApplicationConnection;
import at.fhv.puzzle2.communication.application.connection.BaseApplicationConnection;
import at.fhv.puzzle2.communication.application.connection.encryption.EncryptedApplicationConnection;
import at.fhv.puzzle2.communication.application.connection.encryption.Encryption;
import at.fhv.puzzle2.communication.application.model.ApplicationMessage;
import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.communication.connection.NetworkPacketManager;
import at.fhv.puzzle2.communication.connection.endpoint.DiscoverableEndPoint;
import at.fhv.puzzle2.communication.connection.endpoint.ListenableEndPoint;
import at.fhv.puzzle2.communication.observable.ConnectionObservable;
import at.fhv.puzzle2.communication.observable.MessageReceivedObservable;
import at.fhv.puzzle2.communication.observer.ClosedConnectionObserver;
import at.fhv.puzzle2.communication.observer.MessageReceivedObserver;
import at.fhv.puzzle2.communication.observer.NewConnectionObserver;

import java.io.IOException;

public class CommunicationManager {
    private NetworkManager _networkManager;
    private ApplicationConnectionManager _appConnectionManager;

    private ConnectionObservable<NewConnectionObserver> _newConnectionObservable;
    private ConnectionObservable<ClosedConnectionObserver> _closedConnectionObservable;
    private MessageReceivedObservable _messageReceivedObservable;

    private Encryption _encryption = null;

    public CommunicationManager(String broadcastResponse, Encryption encryption) {
        _appConnectionManager = new ApplicationConnectionManager();

        _newConnectionObservable = new ConnectionObservable<>();
        _closedConnectionObservable = new ConnectionObservable<>();
        _messageReceivedObservable = new MessageReceivedObservable();

        _networkManager = new NetworkManager(this, broadcastResponse);

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
        _messageReceivedObservable.addObserver(observer);
    }

    public boolean removeMessageReceivedObserver(MessageReceivedObserver observable) {
        return _messageReceivedObservable.removeObserver(observable);
    }

    public void messageReceived(ApplicationMessage message) {
        _messageReceivedObservable.appendMessage(message);
    }

    void newConnectionEstablished(NetworkConnection networkConnection) {
        ApplicationConnection applicationConnection = new BaseApplicationConnection(new NetworkPacketManager(networkConnection));
        applicationConnection = new Base64ApplicationConnection(applicationConnection);

        //If the user provided an encryption, use it
        if(_encryption != null) {
            applicationConnection = new EncryptedApplicationConnection(applicationConnection, _encryption);
        }

        _appConnectionManager.listenForMessages(this, applicationConnection);

        _newConnectionObservable.appendConnection(applicationConnection);
    }

    public void connectionClosed(ApplicationConnection applicationConnection) {
        _closedConnectionObservable.appendConnection(applicationConnection);
    }

    public void close() throws IOException {
        _appConnectionManager.close();
        _networkManager.close();
    }
}
