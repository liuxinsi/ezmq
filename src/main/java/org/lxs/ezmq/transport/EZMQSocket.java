package org.lxs.ezmq.transport;

import org.lxs.ezmq.bean.Type;
import org.lxs.ezmq.bean.ZMQRole;
import org.lxs.ezmq.filter.FilterChain;
import org.lxs.ezmq.filter.MessageFilter;
import reactor.rx.broadcast.Broadcaster;

/**
 * @author akalxs@gmail.com
 */
public class EZMQSocket {
    private String addr;
    private int port;
    private ZMQRole role;
    private Type type;

    private FilterChain filterChain;
    private Broadcaster<byte[]> broadcaster;

    public void sendMessage(Object message) {
        if (filterChain == null || filterChain.getFilters() == null) {
            // todo
            throw new RuntimeException("not filters");
        }
        MessageFilter[] filters = filterChain.getFilters();
        byte[] data = null;
        for (MessageFilter mf : filters) {
            data = mf.sendMessage(data == null ? message : data);
        }
        broadcaster.onNext(data);
    }

    public void setFilterChain(FilterChain filterChain) {
        this.filterChain = filterChain;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ZMQRole getRole() {
        return role;
    }

    public void setRole(ZMQRole role) {
        this.role = role;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setBroadcaster(Broadcaster<byte[]> broadcaster) {
        this.broadcaster = broadcaster;
    }
}
