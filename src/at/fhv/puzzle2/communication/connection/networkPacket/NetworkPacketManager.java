package at.fhv.puzzle2.communication.connection.networkPacket;

import at.fhv.puzzle2.communication.NetworkConnectionManager;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class NetworkPacketManager implements Runnable {
    private final NetworkConnectionManager _netNetworkConnectionManager;
    private List<SentNetworkPacket> _packetSentList;

    private final Thread _localThread;
    private final Object _lock = new Object();

    private NetworkPacketManager(NetworkConnectionManager networkConnectionManager) {
        _netNetworkConnectionManager = networkConnectionManager;
        _packetSentList = Collections.synchronizedList(new LinkedList<>());

        _localThread = new Thread(this);
        _localThread.start();
    }

    private volatile boolean _isRunning = true;

    @Override
    public void run() {
        while(_isRunning) {
            try {
                Thread.sleep(10000);

                synchronized (_lock) {
                    for (SentNetworkPacket sentNetworkPacket : _packetSentList) {
                        if (new Date().getTime() - sentNetworkPacket.getTime() > 10000) {
                            try {
                                sentNetworkPacket.resendPacket();
                            } catch (IOException e) {
                                removePacket(sentNetworkPacket.getSequenceID());

                                _netNetworkConnectionManager.connectionClosed(sentNetworkPacket.getDestinationConnection());
                            }
                        }
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
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
        List<SentNetworkPacket> tmpList = Collections.synchronizedList(new LinkedList<>());

        synchronized (_lock) {
            _packetSentList.stream()
                    .filter(sentPacket -> id.compareTo(sentPacket.getSequenceID()) != 0)
                    .collect(Collectors.toCollection(() -> tmpList));

            _packetSentList = tmpList;
        }
    }

    private static NetworkPacketManager _instance;
    public static synchronized void initializeNetworkPacketManager(NetworkConnectionManager networkConnectionManager) {
        _instance = new NetworkPacketManager(networkConnectionManager);
    }

    public static NetworkPacketManager getInstance() {
        return _instance;
    }
}
