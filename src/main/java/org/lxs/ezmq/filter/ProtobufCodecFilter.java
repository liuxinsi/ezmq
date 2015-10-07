package org.lxs.ezmq.filter;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import org.lxs.ezmq.ex.CodecDecodeException;
import org.lxs.ezmq.ex.CodecEncodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public byte[] encode(Object obj) {
        LOG.trace("encode object :{}.", obj);

        byte[] data = new byte[0];
        if (obj instanceof MessageLite) {
            data = ((MessageLite) obj).toByteArray();
        }
        if (obj instanceof MessageLite.Builder) {
            data = ((MessageLite.Builder) obj).build().toByteArray();
        }
        if (data == null) {
            throw new CodecEncodeException("unsupport protobuf encode");
        }
        LOG.trace("encoded object :{} to bytes:{}", obj, data.length);
        return data;
    }

    @Override
    public Object decode(byte[] data) {
        LOG.trace("decode bytes:{}", data.length);

        Object obj;
        try {
            obj = protobufType.getParserForType().parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            LOG.error(e.getMessage(), e);
            throw new CodecDecodeException(e);
        }
        LOG.trace("decoded object:{} from byte:{}.", obj, data.length);
        return obj;
    }
}
