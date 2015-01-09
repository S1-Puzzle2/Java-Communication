package at.fhv.puzzle2.communication.connection.networkPacket;

import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.util.Date;

public class SentNetworkPacket {
    public NetworkConnection destination;
    public Date sentDate;
    public NetworkPacket packet;

    public SentNetworkPacket(NetworkConnection destination, NetworkPacket packet, Date sentDate) {
        this.destination = destination;
        this.packet = packet;
        this.sentDate = sentDate;
    }
}
