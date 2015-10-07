package org.lxs.ezmq.transport;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import org.lxs.ezmq.bean.Type;
import org.lxs.ezmq.bean.ZMQRole;
import org.lxs.ezmq.common.HeartBeatSender;
import org.lxs.ezmq.common.StateMonitor;
import org.lxs.ezmq.filter.FilterChain;
import org.lxs.ezmq.filter.MessageFilter;
import org.lxs.ezmq.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;
import reactor.Environment;
import reactor.rx.broadcast.Broadcaster;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author akalxs@gmail.com
 */
abstract public class BaseService extends AbstractExecutionThreadService {
    private static final Logger LOG = LoggerFactory.getLogger(BaseService.class);

    private ZMQ.Socket socket;
    private ZMQ.Context context;

    private EZMQSocket ezmqSocket;
    private int ioThreads;
    private boolean sendHeartBeat;
    private int heartBeatInterval;

    private HeartBeatSender heartBeatSender;
    private StateMonitor stateMonitor;

    private MessageHandler messageHandler;
    private FilterChain filterChain = new FilterChain();

    private Broadcaster<byte[]> broadcaster;

    static {
        Environment.initialize();
    }

    public BaseService(Type type, String addr, int port, ZMQRole role) {
        this(type, addr, port, 1, role, false, 10);
    }

    public BaseService(Type type, String addr, int port, int ioThreads, ZMQRole role, boolean sendHeartBeat, int heartBeatInterval) {
        ezmqSocket = new EZMQSocket();
        ezmqSocket.setAddr(addr);
        ezmqSocket.setPort(port);
        ezmqSocket.setRole(role);
        ezmqSocket.setType(type);

        this.ioThreads = ioThreads;
        this.sendHeartBeat = sendHeartBeat;
        this.heartBeatInterval = heartBeatInterval < 1 ? 10 : heartBeatInterval;
    }

    @Override
    protected void startUp() throws Exception {
        if (messageHandler == null) {
            throw new RuntimeException("no handler");
        }
        context = ZMQ.context(ioThreads);
        socket = context.socket(ezmqSocket.getRole().getRole());

        if (sendHeartBeat) {
            heartBeatSender = new HeartBeatSender(socket, ezmqSocket.getAddr(), ezmqSocket.getPort(), heartBeatInterval);
            heartBeatSender.start();
        }

        stateMonitor = new StateMonitor(context, socket, ezmqSocket.getAddr(), ezmqSocket.getPort());
        stateMonitor.start();

        ezmqSocket.setFilterChain(filterChain);

        broadcaster = Broadcaster.create();
        ezmqSocket.setBroadcaster(broadcaster);
        broadcaster.consume(bytes -> {
            System.out.println("::收到by:" + Arrays.toString(bytes));
        });

        String url = String.format("tcp://%1$s:%2$s", ezmqSocket.getAddr(), ezmqSocket.getPort());
        if (ezmqSocket.getType() == Type.SERVER) {
            socket.bind(url);
            LOG.trace("绑定地址:{}.", url);
        } else {
            socket.connect(url);
            LOG.trace("连接到地址:{}.", url);
        }
    }

    @Override
    protected void triggerShutdown() {
        LOG.warn("准备停止服务");

        if (heartBeatSender != null) {
            heartBeatSender.stopRunning();
        }
        stateMonitor.stopRunning();
        socket.close();
        context.term();
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void addMessageFilters(MessageFilter... filters) {
        filterChain.addMessageFilters(filters);
    }


    public EZMQSocket getEzmqSocket() {
        return ezmqSocket;
    }

    @Override
    abstract protected String serviceName();
}
