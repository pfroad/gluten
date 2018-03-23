package com.gluten.common

data class Node(val host: String,
                val port: Int,
                val path: String,
                val serviceName: String,
                val password: String,
                val username: String,
                val isAvailable: Boolean,
                val weight: Int = 0) {

}