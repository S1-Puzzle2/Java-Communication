package at.fhv.puzzle2.communication.connection.networkPacket;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceIDGenerator {
    private static AtomicInteger _atomicInteger = new AtomicInteger(0);

    public static int getNextSequenceID() {
        return _atomicInteger.getAndIncrement();
    }
}
