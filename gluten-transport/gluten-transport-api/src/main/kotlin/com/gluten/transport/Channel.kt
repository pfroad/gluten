package com.gluten.transport

import java.io.Closeable
import java.io.IOException
import java.net.InetSocketAddress

interface Channel: Closeable {
    @Throws(IOException::class)
    fun close(timeout: Int)

    @Throws(ConnectException::class)
    fun send(message: Message, channelHandler: ChannelHandler?)

    fun getRemoteAddress(): InetSocketAddress?

    fun isConnected(): Boolean

    fun isClosed(): Boolean

    fun getLocalAddress(): String

//    @Throws(ConnectException::class)
//    fun connect()
}