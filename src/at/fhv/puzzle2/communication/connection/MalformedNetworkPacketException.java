package at.fhv.puzzle2.communication.connection;

import java.io.IOException;

public class MalformedNetworkPacketException extends IOException {
    public MalformedNetworkPacketException(String packet) {
        super("Malformed packet: '" + packet + "'");
    }
}
