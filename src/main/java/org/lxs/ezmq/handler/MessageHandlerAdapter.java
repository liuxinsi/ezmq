package org.lxs.ezmq.handler;

import org.lxs.ezmq.transport.EZMQSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author akalxs@gmail.com
 */
abstract public class MessageHandlerAdapter<T> implements MessageHandler<T> {
    private static final Logger LOG = LoggerFactory.getLogger(MessageHandlerAdapter.class);

    @Override
    public void socketAccepted() {
        // nothing
        LOG.trace("socket accepted");
    }

    @Override
    abstract public void recvMessage(EZMQSocket socket, T message);

    @Override
    public void socketDisconnected() {
        // nothing
        LOG.trace("socket disconnected");
    }

    @Override
    public void exceptionCaught(Exception ex) {
        LOG.error("got a exception ", ex);
    }
}
