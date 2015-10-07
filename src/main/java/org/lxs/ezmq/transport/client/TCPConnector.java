package org.lxs.ezmq.transport.client;

import org.lxs.ezmq.bean.Type;
import org.lxs.ezmq.bean.ZMQRole;
import org.lxs.ezmq.transport.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author akalxs@gmail.com
 */
public class TCPConnector extends BaseService {
    private static final Logger LOG = LoggerFactory.getLogger(TCPConnector.class);

    public TCPConnector(String addr, int port, ZMQRole role) {
        super(Type.CLIENT, addr, port, role);
    }

    public TCPConnector(String addr, int port, int ioThreads, ZMQRole role, boolean sendHeartBeat, int heartBeatInterval) {
        super(Type.CLIENT, addr, port, ioThreads, role, sendHeartBeat, heartBeatInterval);
    }


    @Override
    protected void run() throws Exception {
        while (isRunning()) {
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Override
    protected String serviceName() {
        return "TCPClient";
    }
}

