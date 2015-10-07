package org.lxs.ezmq.filter;

/**
 * @author akalxs@gmail.com
 */
public interface CodecFilter<T> {
    public byte[] encode(T obj);

    public T decode(byte[] data);
}
