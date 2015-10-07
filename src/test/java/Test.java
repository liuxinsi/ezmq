import com.google.common.collect.Lists;
import org.lxs.ezmq.bean.ZMQRole;
import org.lxs.ezmq.filter.CodecFilter;
import org.lxs.ezmq.filter.MessageFilter;
import org.lxs.ezmq.filter.ObjectStreamCodecFilter;
import org.lxs.ezmq.handler.MessageHandlerAdapter;
import org.lxs.ezmq.transport.EZMQSocket;
import org.lxs.ezmq.transport.client.TCPConnector;
import org.lxs.ezmq.transport.server.TCPServer;
import reactor.Environment;
import reactor.rx.broadcast.Broadcaster;

import java.net.Socket;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author akalxs@gmail.com
 */
public class Test {


    public static void main(String[] args) throws InterruptedException {

    }
}
