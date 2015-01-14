package at.fhv.puzzle2.communication.connection.networkPacket;

import at.fhv.puzzle2.communication.NetworkConnectionManager;
import at.fhv.puzzle2.communication.ConnectionClosedException;
import at.fhv.puzzle2.communication.connection.NetworkConnection;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class NetworkPacketManager implements Runnable {
    private NetworkConnectionManager _netNetworkConnectionManager;
    private List<SentNetworkPacket> _packetSentList;

    private Thread _localThread;
    private final Object _lock = new Object();

    private NetworkPacketManager(NetworkConnectionManager networkConnectionManager) {
        _netNetworkConnectionManager = networkConnectionManager;
        _packetSentList = Collections.synchronizedList(new LinkedList<>());

        _localThread = new Thread(this);
        _localThread.start();
    }

    private volatile boolean _isRunning;
    @Override
    public void run() {
        while(_isRunning) {
            try {
                Thread.sleep(10000);

                for(SentNetworkPacket sentNetworkPacket: _packetSentList) {
                    if(new Date().getTime() - sentNetworkPacket.sentDate.getTime() > 10000) {
                        try {
                            resendNetworkPacket(sentNetworkPacket);
                        } catch (IOException e) {
                            _netNetworkConnectionManager.connectionClosed(sentNetworkPacket.destination);
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

    public void sentNetworkPacket(NetworkPacket packet, NetworkConnection destination, Date sentDate) {
        synchronized (_lock) {
            _packetSentList.add(new SentNetworkPacket(destination, packet, sentDate));
        }
    }

    public void receivedAcknowledge(NetworkPacket packet) throws IOException {
        Integer sequenceID = packet.getSequenceID();
        if(packet.getNetworkFlags().getAcknowledge()) {
            //The packet was successfully sent, so we dont need to send it again
            List<SentNetworkPacket> tmpList = Collections.synchronizedList(new LinkedList<>());

            synchronized (_lock) {
                _packetSentList.stream()
                        .filter(sentPacket -> sequenceID.compareTo(sentPacket.packet.getSequenceID()) != 0)
                        .collect(Collectors.toCollection(() -> tmpList));

                _packetSentList = tmpList;
            }
        } else {
            //Acknowledge is false, so something went wrong while transmitting it, resend
            for(SentNetworkPacket sentNetworkPacket : _packetSentList) {
                if(sequenceID.compareTo(sentNetworkPacket.packet.getSequenceID()) == 0) {
                    resendNetworkPacket(sentNetworkPacket);

                    break;
                }
            }
        }
    }

    private void resendNetworkPacket(SentNetworkPacket packet) throws IOException {
        packet.destination.sendBytes(packet.packet.getBytes());
        packet.sentDate = new Date();
    }

    private static NetworkPacketManager _instance;
    public static synchronized void initializeNetworkPacketManager(NetworkConnectionManager networkConnectionManager) {
        _instance = new NetworkPacketManager(networkConnectionManager);
    }

    public static NetworkPacketManager getInstance() {
        return _instance;
    }
}
