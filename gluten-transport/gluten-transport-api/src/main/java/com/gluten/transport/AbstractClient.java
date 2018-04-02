package com.gluten.transport;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.locks.ReentrantLock;

abstract public class AbstractClient implements Client {
    private final ReentrantLock connectLock = new ReentrantLock();
    private boolean isClosed = true;
    private boolean isConnected = false;
    private boolean isDisconnected = true;

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public void connect() throws ConnectException {
        connectLock.lock();
        if (isConnected()) {
            return;
        }

        try {
            isConnected = true;
            doConnect();
        } catch (ConnectException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new ConnectException("Connect " + getRemoteAddress() + " from " + simpleClass()
                    + ", error message " + throwable.getMessage(), throwable);
        } finally {
            connectLock.unlock();
        }
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        Channel channel = getChannel();
        if (channel != null)
            return channel.getRemoteAddress();
        return this.getServerAddress();
    }

    protected abstract InetSocketAddress getServerAddress();

    private String simpleClass() {
        return this.getClass().getSimpleName();
    }

    protected abstract void doConnect() throws ConnectException;

    @Override
    public void reconnect() throws ConnectException {
        disconnect();
        connect();
    }

    @Override
    public void disconnect() throws ConnectException {
        connectLock.lock();
        try {
            Channel channel = getChannel();
            if (channel != null)
                channel.close();

            doDisconnect();
        } catch (Throwable e) {
            throw new ConnectException(e.getMessage(), e);
        } finally {
            connectLock.unlock();
        }
    }

    protected abstract void doDisconnect();

    @Override
    public boolean isDisconnected() {
        return isDisconnected;
    }

    @Override
    public void close(int timeout) throws ConnectException {
        try {
            close();
        } catch (IOException e) {
            throw new ConnectException(e);
        }
    }

    @Override
    public boolean isClose() {
        return isClosed;
    }

    @Override
    public void send(@NotNull Message message, ChannelHandler responseHandler) throws ConnectException {
        if (!isConnected) {
            connect();
        }

        final Channel channel = getChannel();
        if (channel == null || !channel.isConnected()) {
            throw new ConnectException("Failed to send message, because channel is closed!");
        }

        channel.send(message, responseHandler);
    }


    @Override
    public void close() throws IOException {
        isClosed = true;
        doClose();

    }

    protected abstract void doClose() throws IOException;
}
