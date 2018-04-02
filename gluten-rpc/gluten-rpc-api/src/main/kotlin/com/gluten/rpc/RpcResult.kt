package com.gluten.rpc

import java.util.*

data class RpcResult(val resultCode: String,
                     val data: Any?,
                     val timestamp: Date = Date()) {

}