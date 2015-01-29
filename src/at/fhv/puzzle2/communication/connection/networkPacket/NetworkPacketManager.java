package at.fhv.puzzle2.communication.connection.networkPacket;

import at.fhv.puzzle2.communication.NetworkConnectionManager;
import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.util.*;
import java.util.stream.Collectors;

public class NetworkPacketManager implements Runnable {
    private List<SentNetworkPacket> _packetSentList;

    private final Thread _localThread;
    private final Object _lock = new Object();

    private NetworkPacketManager() {
        _packetSentList = Collections.synchronizedList(new LinkedList<>());

        _localThread = new Thread(this);
        _localThread.start();
    }

    private volatile boolean _isRunning = true;

    @Override
    public void run() {
        while(_isRunning) {
            try {
                Thread.sleep(100000);

                synchronized (_lock) {
                    Iterator<SentNetworkPacket> iterator = _packetSentList.iterator();
                    while(iterator.hasNext()) {
                        SentNetworkPacket sentNetworkPacket = iterator.next();
                        if (new Date().getTime() - sentNetworkPacket.getTime() > 10000) {
                            sentNetworkPacket.resendPacket();
                            iterator.remove();
                        }
                    }
                }

            } catch (InterruptedException e) {
                //Do nothing here
            }
        }
    }

    public void close() {
        _isRunning = false;

        _localThread.interrupt();
    }

    public void sentNetworkPacket(NetworkPacket packet, NetworkPacketHandler destination) {
        synchronized (_lock) {
            _packetSentList.add(new SentNetworkPacket(destination, packet));
        }
    }

    public void receivedAcknowledge(NetworkPacket packet) {
        if(packet.getNetworkFlags().getAcknowledge()) {
            //The _packet was successfully sent, so we dont need to send it again
            removePacket(packet.getSequenceID());
        }
    }

    private void removePacket(Integer id) {
        synchronized (_lock) {
            Iterator<SentNetworkPacket> iterator = _packetSentList.iterator();
            while(iterator.hasNext()) {
                SentNetworkPacket packet = iterator.next();

                if(Objects.equals(packet.getSequenceID(), id)) {
                    iterator.remove();
                }
            }
        }
    }

    public void removePacketsByConnection(NetworkConnection connection) {
        synchronized (_lock) {
            Iterator<SentNetworkPacket> iterator = _packetSentList.iterator();
            while(iterator.hasNext()) {
                SentNetworkPacket packet = iterator.next();

                if(Objects.equals(packet.getDestinationConnection(), connection)) {
                    iterator.remove();
                }
            }
        }
    }

    private static NetworkPacketManager _instance;
    public static synchronized void initializeNetworkPacketManager() {
        _instance = new NetworkPacketManager();
    }

    public static NetworkPacketManager getInstance() {
        return _instance;
    }
}
