package org.lxs.ezmq.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author akalxs@gmail.com
 */
public class ObjectStreamCodecFilter implements CodecFilter {
    private static final Logger LOG = LoggerFactory.getLogger(ObjectStreamCodecFilter.class);

    @Override
    public byte[] encode(Object obj) throws IOException {
        LOG.trace("encode object :{}.", obj);

        byte[] data;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            oos.flush();
            data = bos.toByteArray();
        }
        LOG.trace("encoded object :{} to bytes:{}", obj, data.length);
        return data;
    }

    @Override
    public Object decode(byte[] data) throws IOException, ClassNotFoundException {
        LOG.trace("decode bytes:{}", data.length);

        Object obj;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream bis = new ObjectInputStream(bais)) {
            obj = bis.readObject();
        }
        LOG.trace("decoded object:{} from byte:{}.", obj, data.length);
        return obj;
    }
}
