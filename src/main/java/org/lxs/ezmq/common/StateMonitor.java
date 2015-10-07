package org.lxs.ezmq.common;

import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author akalxs@gmail.com
 */
public class StateMonitor extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(StateMonitor.class);
    private static Map<Integer, String> eventMap = new HashMap<>();

    private final ImmutableSet<Integer> freqEventSet = ImmutableSet
            .of(ZMQ.EVENT_CONNECT_DELAYED, ZMQ.EVENT_CONNECT_RETRIED, ZMQ.EVENT_CLOSED);
    private volatile boolean running = true;
    private ZMQ.Socket monitorSocket;
    private String addr;
    private int port;

    static {
        Field[] fields = ZMQ.class.getFields();
        Arrays.stream(fields)
                .filter(field -> field.getName().startsWith("EVENT"))
                .forEach(field -> {
                    try {
                        eventMap.put((int) field.get(null),
                                field.getName());
                    } catch (IllegalAccessException e) {
                        LOG.warn("加载ZMQ事件值异常", e);
                    }
                });
    }

    public StateMonitor(ZMQ.Context context, ZMQ.Socket socketOnMonitor, String addr, int port) {
        String monitorUrl = "inproc://monitor:" + addr + ":" + port;
        boolean b = socketOnMonitor.monitor(monitorUrl, ZMQ.EVENT_ALL);

        monitorSocket = context.socket(ZMQ.PAIR);
        monitorSocket.connect(monitorUrl);

        this.addr = addr;
        this.port = port;

        setName("State-Monitor-On-" + addr + ":" + port);
        LOG.debug("建立监控on：{}/{}。", monitorUrl, b);
    }

    @Override
    public void run() {
        Set<String> tempSet = new HashSet<>(5);
        int limit = 0;
        while (running) {
            zmq.ZMQ.Event event = zmq.ZMQ.Event.read(monitorSocket.base());
            if (event != null) {
                // 特频繁
                if (freqEventSet.contains(event.event)) {
                    tempSet.add(eventMap.get(event.event));
                    if (limit > 60) {
                        LOG.trace("pre events：{}", tempSet);
                        tempSet.clear();
                        limit = 0;
                    }
                    limit++;
                } else {
                    LOG.trace("event：{}。", eventMap.get(event.event));
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                LOG.warn("监控：{}被打断，停止监控。", event != null ? event.addr : null);
                break;
            }
        }
        LOG.warn("退出对：{}:{}的监控。", addr, port);
    }

    public void stopRunning() {
        running = false;
        interrupt();
        monitorSocket.close();
    }
}
