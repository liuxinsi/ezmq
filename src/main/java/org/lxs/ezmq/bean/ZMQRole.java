package org.lxs.ezmq.bean;

/**
 * 封装zeromq socket类型。
 *
 * @author akalxs@gmail.com
 */
public enum ZMQRole {
    /**
     * Flag to specify a exclusive pair of sockets.
     */
    PAIR(zmq.ZMQ.ZMQ_PAIR),
    /**
     * Flag to specify a PUB socket, receiving side must be a SUB or XSUB.
     */
    PUB(zmq.ZMQ.ZMQ_PUB),
    /**
     * Flag to specify the receiving part of the PUB or XPUB socket.
     */
    SUB(zmq.ZMQ.ZMQ_SUB),
    /**
     * Flag to specify a REQ socket, receiving side must be a REP.
     */
    REQ(zmq.ZMQ.ZMQ_REQ),
    /**
     * Flag to specify the receiving part of a REQ socket.
     */
    REP(zmq.ZMQ.ZMQ_REP),
    /**
     * Flag to specify a DEALER socket (aka XREQ).
     * DEALER is really a combined ventilator / sink
     * that does load-balancing on output and fair-queuing on input
     * with no other semantics. It is the only socket type that lets
     * you shuffle messages out to N nodes and shuffle the replies
     * back, in a raw bidirectional asynch pattern.
     */
    DEALER(zmq.ZMQ.ZMQ_DEALER),
    /**
     * Old alias for DEALER flag.
     * Flag to specify a XREQ socket, receiving side must be a XREP.
     *
     * @deprecated As of release 3.0 of zeromq, replaced by {@link #DEALER}
     */
    XREQ(zmq.ZMQ.ZMQ_DEALER),
    /**
     * Flag to specify ROUTER socket (aka XREP).
     * ROUTER is the socket that creates and consumes request-reply
     * routing envelopes. It is the only socket type that lets you route
     * messages to specific connections if you know their identities.
     */
    ROUTER(zmq.ZMQ.ZMQ_ROUTER),
    /**
     * Old alias for ROUTER flag.
     * Flag to specify the receiving part of a XREQ socket.
     *
     * @deprecated As of release 3.0 of zeromq, replaced by {@link #ROUTER}
     */
    XREP(zmq.ZMQ.ZMQ_ROUTER),
    /**
     * Flag to specify the receiving part of a PUSH socket.
     */
    PULL(zmq.ZMQ.ZMQ_PULL),
    /**
     * Flag to specify a PUSH socket, receiving side must be a PULL.
     */
    PUSH(zmq.ZMQ.ZMQ_PUSH),
    /**
     * Flag to specify a XPUB socket, receiving side must be a SUB or XSUB.
     * Subscriptions can be received as a message. Subscriptions start with
     * a '1' byte. Unsubscriptions start with a '0' byte.
     */
    XPUB(zmq.ZMQ.ZMQ_XPUB),
    /**
     * Flag to specify the receiving part of the PUB or XPUB socket. Allows
     */
    XSUB(zmq.ZMQ.ZMQ_XSUB),

    /**
     * Flag to specify a STREAMER device.
     */
    STREAMER(zmq.ZMQ.ZMQ_STREAMER),
    /**
     * Flag to specify a FORWARDER device.
     */
    FORWARDER(zmq.ZMQ.ZMQ_FORWARDER),
    /**
     * Flag to specify a QUEUE device.
     */
    QUEUE(zmq.ZMQ.ZMQ_QUEUE);
    private int role;

    private ZMQRole(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }
}
