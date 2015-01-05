import at.fhv.puzzle2.communication.CommunicationManager;
import at.fhv.puzzle2.communication.connection.protocoll.ethernet.tcp.TCPEndpoint;
import at.fhv.puzzle2.communication.connection.protocoll.ethernet.udp.UDPEndpoint;
import at.fhv.puzzle2.communication.observable.ConnectionObservable;
import at.fhv.puzzle2.communication.observable.MessageReceivedObservable;
import at.fhv.puzzle2.communication.observer.MessageReceivedObserver;
import at.fhv.puzzle2.communication.observer.NewConnectionObserver;

import java.io.IOException;

public class Main implements NewConnectionObserver, MessageReceivedObserver {
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        try {
            CommunicationManager cm = new CommunicationManager("PUZZLE2");
            cm.addMessageReceivedObserver(this);
            cm.addNewConnectionObserver(this);
            cm.addConnectionListener(new TCPEndpoint("0.0.0.0", 4711));
            cm.addBroadcastListener(new UDPEndpoint("0.0.0.0", 4712));

            cm.startListeningForConnections();
            cm.startListeningForBroadcasts();

            System.in.read();

            cm.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notify(ConnectionObservable observable) {
        System.out.println("New connection!!!");
    }

    @Override
    public void messageReceived(MessageReceivedObservable messageReceivedObservable) {
        System.out.println("Messages received!!!");
        messageReceivedObservable.getMessageList().forEach(message -> System.out.println(message.getMessage()));
        System.out.println();
    }
}
