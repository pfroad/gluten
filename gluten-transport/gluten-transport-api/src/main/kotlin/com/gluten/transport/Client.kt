package com.gluten.transport

import java.io.Closeable
import java.net.InetSocketAddress

interface Client: Closeable {
    fun isConnected(): Boolean

    @Throws(ConnectException::class)
    fun connect()

    @Throws(ConnectException::class)
    fun reconnect()

    @Throws(ConnectException::class)
    fun disconnect()

    fun isDisconnected(): Boolean

    @Throws(ConnectException::class)
    fun close(timeout: Int)

    fun isClose(): Boolean

    @Throws(ConnectException::class)
    fun send(message: Message, responseHandler: ChannelHandler)

    fun getChannel(): Channel

    fun getRemoteAddress(): InetSocketAddress

//    fun reset()
}