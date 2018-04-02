package com.gluten.rpc

class RpcException: RuntimeException {

    companion object {
        val UNKONW_EXCEPTION = 0
        val TIMEOUT_EXCEPTION = 1
        val NETWORK_EXCEPTION = 2
        val FORBIDDEN_EXCEPTION = 3
        val SERIALIZATION_EXCEPTION = 4
    }

    private val code: Int = 0

    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(code: Int) : super()
    constructor(code: Int, message: String?) : super(message)
    constructor(code: Int, message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
    constructor(code: Int, cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)
}