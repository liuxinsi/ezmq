package org.lxs.ezmq.filter;

import org.lxs.ezmq.ex.CodecDecodeException;
import org.lxs.ezmq.ex.CodecEncodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * @author akalxs@gmail.com
 */
public class ObjectStreamCodecFilter<T> implements CodecFilter<T>, MessageFilter<T> {
    private static final Logger LOG = LoggerFactory.getLogger(ObjectStreamCodecFilter.class);

    @Override
    public byte[] encode(T obj) {
        LOG.trace("encode object :{}.", obj);

        byte[] data;
        try {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(obj);
                oos.flush();
                data = bos.toByteArray();
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new CodecEncodeException(e);
        }
        LOG.trace("encoded object :{} to bytes:{}", obj, data.length);
        return data;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T decode(byte[] data) {
        LOG.trace("decode bytes:{}", data.length);

        Object obj;
        try {
            try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
                 ObjectInputStream bis = new ObjectInputStream(bais)) {
                obj = bis.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new CodecDecodeException(e);
        }
        LOG.trace("decoded object:{} from byte:{}.", obj, data.length);
        return (T) obj;
    }

    @Override
    public byte[] sendMessage(T message) {
        return encode(message);
    }

    @Override
    public T recvMessage(byte[] bytes) {
        return decode(bytes);
    }
}
