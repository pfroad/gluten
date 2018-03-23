package com.gluten.common

data class URL(val protocol: Protocol,
               val path: String,
//               val node: Node,
               val parameters: Map<String, String>) {

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

enum class Protocol(val protocol: String) {
    HTTP("http"), THRIFT("thrift"), GRPC("grpc")
}