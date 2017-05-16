package track.msgtest.messenger.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jdbc.datasource.ConnectionHandle;
import track.msgtest.messenger.User;
import track.msgtest.messenger.messages.LoginMessage;
import track.msgtest.messenger.messages.Message;
import track.msgtest.messenger.messages.TextMessage;
import track.msgtest.messenger.messages.Type;


/**
 * Сессия связывает бизнес-логику и сетевую часть.
 * Бизнес логика представлена объектом юзера - владельца сессии.
 * Сетевая часть привязывает нас к определнному соединению по сети (от клиента)
 */
public class Session implements Runnable {
    static Logger log = LoggerFactory.getLogger(Session.class);

    private User user;
    private Socket socket;
    private Protocol protocol;
    private MessengerServer server;

    private InputStream in;
    private OutputStream out;

    private volatile boolean isActive;
    private byte[] buffer = new byte[1024 * 16]; // 16 kb

    public Session(Socket socket, MessengerServer server) throws IOException {
        this.socket = socket;
        this.server = server;

        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    public void send(Message msg) throws ProtocolException, IOException {
        log.info(msg.toString());
        out.write(server.getProtocol().encode(msg));
        out.flush();
    }

    public void onMessage(Message msg) {
        // TODO: Пришло некое сообщение от клиента, его нужно обработать
        System.out.println(msg);
        switch (msg.getType()) {
            case MSG_LOGIN:
                System.out.println("LOGIN");
                if (user != null) {
                    TextMessage sendMessage = new TextMessage();
                    sendMessage.setType(Type.MSG_STATUS);
                    sendMessage.setText("already logged in");
                    try {
                        send(sendMessage);
                    } catch (ProtocolException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    LoginMessage loginMessage = (LoginMessage) msg;

                    user = new User(loginMessage.getName(), loginMessage.getPass());
                    user = server.getUserStore().getUser(loginMessage.getName(),
                            loginMessage.getPass());
                    if (user == null) {
                        user = server.getUserStore().addUser(user);
                    }
                    server.getActiveUsers().put(user.getId(), this);
                    System.out.println("LOGIN SUCCESS");
                }
                break;
            case MSG_TEXT:
                if (user == null) {
                    TextMessage sendMessage = new TextMessage();
                    sendMessage.setType(Type.MSG_STATUS);
                    sendMessage.setText("Log in first");
                    try {
                        send(sendMessage);
                    } catch (ProtocolException | IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void close() {
        // TODO: закрыть in/out каналы и сокет. Освободить другие ресурсы, если необходимо
    }

    @Override
    public void run() {
        byte [] binMessage = new byte[10000];
        while (true) {
            try {
                in.read(binMessage);
                Message message = server.getProtocol().decode(binMessage);
                onMessage(message);
            } catch (IOException | ProtocolException e) {
                e.printStackTrace();
            }
        }
    }
}