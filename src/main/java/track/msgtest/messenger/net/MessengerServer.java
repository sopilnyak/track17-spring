package track.msgtest.messenger.net;

import org.eclipse.jetty.server.Server;
import track.msgtest.messenger.store.MessageStore;
import track.msgtest.messenger.store.UserStore;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class MessengerServer {

    public static final int DEFAULT_MAX_CONNECT = 16;

    private int port;

    private MessageStore messageStore;
    private UserStore userStore;

    private int maxConnection = DEFAULT_MAX_CONNECT;

    private ExecutorService service;

    private ConcurrentHashMap<Long, Session> activeUsers;

    public ConcurrentHashMap<Long, Session> getActiveUsers() {
        return activeUsers;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    private Protocol protocol;

    public MessageStore getMessageStore() {
        return messageStore;
    }

    public UserStore getUserStore() {
        return userStore;
    }

    public void start() throws IOException {
        activeUsers = new ConcurrentHashMap<>();
        service = Executors.newFixedThreadPool(DEFAULT_MAX_CONNECT);
        port = 8899;
        protocol = new StringProtocol();

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started");

        while (true) {
            Socket socket = serverSocket.accept();
            Session session = new Session(socket, this);
            service.submit(session);
        }

    }

    public void stop() {
    }

    public static void main(String[] args) throws IOException {
        MessengerServer server = new MessengerServer();
        server.start();
    }
}
