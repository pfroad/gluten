package com.gluten.common

data class Node(val host: String,
                val port: Int,
                val path: String,
                val serviceName: String,
                var password: String? = null,
                var username: String? = null,
                val isAvailable: Boolean,
                val weight: Int = 0,
                val protocol: Protocol) {
}