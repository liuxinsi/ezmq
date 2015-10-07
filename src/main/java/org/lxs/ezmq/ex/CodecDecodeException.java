package org.lxs.ezmq.ex;

/**
 * @author akalxs@gmail.com
 */
public class CodecDecodeException extends RuntimeException{
    public CodecDecodeException() {
        super();
    }

    public CodecDecodeException(String message) {
        super(message);
    }

    public CodecDecodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodecDecodeException(Throwable cause) {
        super(cause);
    }

    protected CodecDecodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
