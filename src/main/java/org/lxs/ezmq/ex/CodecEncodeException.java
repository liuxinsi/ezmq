package org.lxs.ezmq.ex;

/**
 * @author akalxs@gmail.com
 */
public class CodecEncodeException extends RuntimeException{
    public CodecEncodeException() {
        super();
    }

    public CodecEncodeException(String message) {
        super(message);
    }

    public CodecEncodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodecEncodeException(Throwable cause) {
        super(cause);
    }

    protected CodecEncodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
