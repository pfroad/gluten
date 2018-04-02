package com.gluten.transport;

import com.gluten.common.utils.NetUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

abstract public class AbstractChannel implements Channel {
    private boolean connected = false;
    private boolean closed = true;

    @Override
    public void close(int timeout) throws IOException {
        close();
    }

    public void close() throws IOException {
        closed = true;
        connected = false;
        doClose();
    }

    protected abstract void doClose() throws IOException;

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void send(@NotNull Message message, ChannelHandler responseHandler) throws ConnectException {
        if (isClosed()) {
            throw new ConnectException("Channel has been closed, channel:" + getLocalAddress() + "->" + getRemoteAddress());
        }

        doSend(message, responseHandler);
    }

    protected abstract void doSend(Message message, ChannelHandler responseHandler);

//    @NotNull
//    @Override
//    public InetSocketAddress getRemoteAddress() {
//        return serverAddress;
//    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @NotNull
    @Override
    public String getLocalAddress() {
        return NetUtils.getLocalHost();
    }
}
