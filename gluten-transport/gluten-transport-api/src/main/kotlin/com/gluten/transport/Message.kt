package com.gluten.transport

import java.io.Serializable

data class Message(val className: String,
                     val methodName: String,
                     val parameterNames: MutableMap<String, String>,
                     val parameterTypes: MutableMap<String, String>,
                     val parameters: MutableMap<String, Any>): Serializable {

}