package org.lxs.ezmq.filter;

/**
 * @author akalxs@gmail.com
 */
public interface MessageFilter<T> {
    public byte[] sendMessage(T message);

    public T recvMessage(byte[] bytes);
}
