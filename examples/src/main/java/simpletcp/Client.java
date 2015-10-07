package simpletcp;

import org.lxs.ezmq.bean.ZMQRole;
import org.lxs.ezmq.filter.ObjectStreamCodecFilter;
import org.lxs.ezmq.handler.MessageHandlerAdapter;
import org.lxs.ezmq.transport.EZMQSocket;
import org.lxs.ezmq.transport.client.TCPConnector;

import java.util.concurrent.TimeUnit;

/**
 * @author akalxs@gmail.com
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {
        TCPConnector tc = new TCPConnector("127.0.0.1", 5555, ZMQRole.REQ);
        tc.addMessageFilters(new ObjectStreamCodecFilter<UserInfo>());
        tc.setMessageHandler(new MessageHandlerAdapter() {
            @Override
            public void recvMessage(EZMQSocket ezmqSocket, Object o) {

            }
        });
        tc.startAsync().awaitRunning();
        EZMQSocket socket = tc.getEzmqSocket();
        for (int i = 0; i < 100; i++) {
            UserInfo u = new UserInfo();
            u.setId(i + "");

            socket.sendMessage(u);
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
