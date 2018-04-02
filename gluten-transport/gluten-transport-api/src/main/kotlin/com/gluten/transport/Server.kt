package com.gluten.transport

import java.io.Closeable
import java.net.InetSocketAddress

interface Server: Closeable {
    fun close(timeout: Int)

    fun isClosed(): Boolean

    fun getLocalAddress(): InetSocketAddress

    fun getPort(): Int
}