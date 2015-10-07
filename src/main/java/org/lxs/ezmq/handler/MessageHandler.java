package org.lxs.ezmq.handler;

import org.lxs.ezmq.transport.EZMQSocket;

/**
 * @author akalxs@gmail.com
 */
public interface MessageHandler<T> {
    public void socketAccepted();

    public void recvMessage(EZMQSocket socket, T message);

    public void socketDisconnected();

    public void exceptionCaught(Exception ex);
}
