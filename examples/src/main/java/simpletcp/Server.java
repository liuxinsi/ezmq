package simpletcp;

import org.lxs.ezmq.bean.ZMQRole;
import org.lxs.ezmq.handler.MessageHandlerAdapter;
import org.lxs.ezmq.transport.EZMQSocket;
import org.lxs.ezmq.transport.server.TCPServer;

/**
 * @author akalxs@gmail.com
 */
public class Server {
    public static void main(String[] args) {
        TCPServer ts = new TCPServer("127.0.0.1", 5555, ZMQRole.REP);
        ts.setMessageHandler(new MessageHandlerAdapter() {
            @Override
            public void recvMessage(EZMQSocket ezmqSocket, Object o) {

            }
        });
        ts.startAsync();
    }
}
