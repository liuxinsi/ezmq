package org.lxs.ezmq.filter;

import com.google.protobuf.MessageLite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author akalxs@gmail.com
 */
public class ProtobufCodecFilter implements CodecFilter {
    private static final Logger LOG = LoggerFactory.getLogger(ProtobufCodecFilter.class);
    private MessageLite protobufType;

    public ProtobufCodecFilter(MessageLite protobufType) {
        this.protobufType = protobufType;
    }

    @Override
    public byte[] encode(Object obj) throws IOException {
        LOG.trace("encode object :{}.", obj);

        byte[] data = new byte[0];
        if (obj instanceof MessageLite) {
            data = ((MessageLite) obj).toByteArray();
        }
        if (obj instanceof MessageLite.Builder) {
            data = ((MessageLite.Builder) obj).build().toByteArray();
        }
        if (data == null) {
            throw new IOException("unsupport protobuf encode");
        }
        LOG.trace("encoded object :{} to bytes:{}", obj, data.length);
        return data;
    }

    @Override
    public Object decode(byte[] data) throws IOException, ClassNotFoundException {
        LOG.trace("decode bytes:{}", data.length);

        Object obj = protobufType.getParserForType().parseFrom(data);
        LOG.trace("decoded object:{} from byte:{}.", obj, data.length);
        return obj;
    }
}
