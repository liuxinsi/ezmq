package org.lxs.ezmq.filter;

import java.io.IOException;

/**
 * @author akalxs@gmail.com
 */
public interface CodecFilter {
    public byte[] encode(Object obj) throws IOException;

    public Object decode(byte[] data) throws IOException, ClassNotFoundException;
}
