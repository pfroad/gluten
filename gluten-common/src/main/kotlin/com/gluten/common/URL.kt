package com.gluten.common

data class URL(val protocol: Protocol,
               val path: String,
               val method: String,
               var username: String? = null,
               var password: String? = null) {

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }
}

enum class Protocol(val value: String) {
    HTTP("http"),
    HTTPS("https"),
    THRIFT("thrift"),
    GRPC("grpc")
}