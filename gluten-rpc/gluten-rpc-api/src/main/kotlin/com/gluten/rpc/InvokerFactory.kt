package com.gluten.rpc

interface InvokerFactory<T> {
    fun createInvoker(parameters: T): Invoker
}