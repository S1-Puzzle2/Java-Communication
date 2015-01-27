package at.fhv.puzzle2.communication.application.connection;

import at.fhv.puzzle2.communication.ApplicationConnectionManager;
import at.fhv.puzzle2.communication.ConnectionClosedException;
import at.fhv.puzzle2.communication.application.ApplicationMessage;
import at.fhv.puzzle2.communication.application.command.Command;
import at.fhv.puzzle2.communication.application.command.CommandFactory;
import at.fhv.puzzle2.communication.application.command.MalformedCommandException;
import at.fhv.puzzle2.communication.application.command.UnknownCommandException;
import at.fhv.puzzle2.communication.application.command.commands.error.MalformedCommand;
import at.fhv.puzzle2.communication.application.command.commands.error.UnknownCommand;
import at.fhv.puzzle2.communication.connection.NetworkConnection;
import at.fhv.puzzle2.logging.Logger;

import java.io.IOException;
import java.net.SocketException;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandConnection {
    private static final String TAG = "communication.CommandConnection";

    private final ApplicationConnectionManager _connectionManager;
    private final ApplicationConnection _applicationConnection;
    private final ConnectionSendQueue _connectionSendQueue;

    public CommandConnection(ApplicationConnectionManager connectionManager, ApplicationConnection applicationConnection) {
        _connectionManager = connectionManager;
        _applicationConnection = applicationConnection;

        _connectionSendQueue = new ConnectionSendQueue(this);
    }

    public void sendCommand(Command command) {
        _connectionSendQueue.enqueueCommand(command);
    }

    public void stopSendQueue() {
        _connectionSendQueue.stopSendQueue();
    }

    public Command receiveCommand() {
        while(true) {
            try {
                final ApplicationMessage receivedMessage = _applicationConnection.receiveMessage();
                if(receivedMessage == null) {
                    return null;
                }

                Logger.getLogger().debug(TAG, "Received app-message: " + receivedMessage.getMessage());

                final Command command;
                try {
                    command = CommandFactory.parseCommand(receivedMessage);
                    command.setConnection(this);

                    return command;
                } catch (UnknownCommandException e) {
                    UnknownCommand unknownCommand = new UnknownCommand(e.getMessage());
                    sendCommand(unknownCommand);
                } catch(MalformedCommandException e) {
                    MalformedCommand malformedCommand = new MalformedCommand(e.getMessage());
                    sendCommand(malformedCommand);
                }

            } catch (IOException e) {
                if(e instanceof ConnectionClosedException || e instanceof SocketException) {
                    _connectionManager.connectionClosed(this);
                } else {
                    e.printStackTrace();
                }

                return null;
            }
        }
    }

    public void close() {
        try {
            _applicationConnection.close();
        } catch (IOException e) {
            //We dont really care about exceptions while closing the connection
            //e.printStackTrace();
        }
    }

    NetworkConnection getUnderlyingConnection() {
        return _applicationConnection.getUnderlyingConnection();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof CommandConnection && Objects.equals(this.getUnderlyingConnection(), ((CommandConnection)object).getUnderlyingConnection());
    }

    class ConnectionSendQueue implements Runnable {
        private static final String TAG = "communication.ConnectionSendQueue";

        private final CommandConnection _connection;
        private final BlockingQueue<ApplicationMessage> _sendQueue;
        private volatile boolean _isRunning = true;
        private final Thread _localThread;

        public ConnectionSendQueue(CommandConnection connection) {
            _connection = connection;

            _sendQueue = new LinkedBlockingQueue<>();

            _localThread = new Thread(this);
            _localThread.start();
        }

        public void stopSendQueue() {
            _isRunning = false;

            _localThread.interrupt();
        }

        public void enqueueCommand(Command command) {
            if(!_sendQueue.offer(new ApplicationMessage(command.toJSONString()))) {
                Logger.getLogger().warn(TAG, "We lost packets, why?!");
            }
        }

        @Override
        public void run() {
            while(_isRunning) {
                try {
                    ApplicationMessage message = _sendQueue.take();

                    try {
                        Logger.getLogger().debug(TAG, "Sending app-message: " + message.getMessage());

                        _applicationConnection.sendApplicationMessage(message);
                    } catch (IOException e) {
                        if(e instanceof ConnectionClosedException || e instanceof SocketException) {
                            _connectionManager.connectionClosed(_connection);
                        } else {
                            e.printStackTrace();
                        }

                        break;
                    }
                } catch (InterruptedException e) {
                    //Do nothing here
                }
            }
        }
    }
}
