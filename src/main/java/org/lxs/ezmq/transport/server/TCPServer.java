package org.lxs.ezmq.transport.server;

import org.lxs.ezmq.bean.Type;
import org.lxs.ezmq.bean.ZMQRole;
import org.lxs.ezmq.transport.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author akalxs@gmail.com
 */
public class TCPServer extends BaseService {
    private static final Logger LOG = LoggerFactory.getLogger(TCPServer.class);

    public TCPServer(String addr, int port, ZMQRole role) {
        super(Type.SERVER, addr, port, role);
    }

    public TCPServer(String addr, int port, int ioThreads, ZMQRole role, boolean sendHeartBeat, int heartBeatInterval) {
        super(Type.SERVER, addr, port, ioThreads, role, sendHeartBeat, heartBeatInterval);
    }

    @Override
    protected void run() throws Exception {

        while (isRunning()) {
            TimeUnit.SECONDS.sleep(1);
        }
        LOG.warn("TCPServer停止服务");
    }

    @Override
    protected String serviceName() {
        return "TCPServer";
    }
}
