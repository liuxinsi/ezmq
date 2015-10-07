package org.lxs.ezmq.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;

import java.util.concurrent.TimeUnit;

/**
 * @author akalxs@gmail.com
 */
public class HeartBeatSender extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(HeartBeatSender.class);
    private volatile boolean running = true;
    private ZMQ.Socket socket;
    private int heartBeatInterval;
    private byte[] beats;
    private String addr;
    private int port;

    public HeartBeatSender(ZMQ.Socket socket, String addr, int port, int heartBeatInterval) {
        this(socket, addr, port, heartBeatInterval, null);
    }

    public HeartBeatSender(ZMQ.Socket socket, String addr, int port, int heartBeatInterval, byte[] beats) {
        this.socket = socket;
        this.heartBeatInterval = heartBeatInterval;
        if (beats == null) {
            this.beats = new byte[]{0x01};
        } else {
            this.beats = beats;
        }
        this.addr = addr;
        this.port = port;

        LOG.trace("建立心跳on：{}:{}", addr, port);
        setName("HB-Sender-On-" + addr + ":" + port);
    }

    @Override
    public void run() {
        while (running) {
            boolean b = socket.send(beats, ZMQ.NOBLOCK);
            LOG.trace("send heart beat：{}。", b);
            try {
                TimeUnit.SECONDS.sleep(heartBeatInterval);
            } catch (InterruptedException e) {
                LOG.warn("发送心时候被打断", e);
                break;
            }
        }
        LOG.warn("退出对：{}:{}的心跳发送。", addr, port);
    }

    public void stopRunning() {
        running = false;
        interrupt();
    }
}
