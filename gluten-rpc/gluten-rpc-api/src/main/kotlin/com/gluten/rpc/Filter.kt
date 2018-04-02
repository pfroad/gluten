package com.gluten.rpc

interface Filter {
    @Throws(RpcException::class)
    fun filter(invocation: Invocation)
}