package at.fhv.puzzle2.communication.connection.networkPacket;

import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.io.IOException;
import java.util.Date;

public class SentNetworkPacket {
    private NetworkPacketHandler _destination;
    private Date _sentDate;
    private NetworkPacket _packet;

    public SentNetworkPacket(NetworkPacketHandler destination, NetworkPacket packet) {
        this._destination = destination;
        this._packet = packet;

        this._sentDate = new Date();
    }

    public void resendPacket() throws IOException {
        this._destination.sendMessage(this._packet, false);
        this._sentDate = new Date();
    }

    public long getTime() {
        return this._sentDate.getTime();
    }

    public NetworkConnection getDestinationConnection() {
        return this._destination.getUnderlyingConnection();
    }

    public Integer getSequenceID() {
        return _packet.getSequenceID();
    }
}
