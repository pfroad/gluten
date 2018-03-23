package com.gluten.rpc.lb

import com.gluten.common.Node
import com.gluten.common.URL
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicInteger

class RoundRobinLoadBalance: AbstractLoadBalance() {
    private val sequences: ConcurrentMap<String, AtomicInteger> = ConcurrentHashMap()

    override fun doSelect(instances: List<Node>, url: URL): Node {
        val serviceKey: String = url.path
        var sequence: AtomicInteger? = sequences[serviceKey]

        if (sequence == null) {
            sequences.putIfAbsent(serviceKey, AtomicInteger())
            sequence = sequences[serviceKey]
        }

        val currentSeq: Int = sequence?.getAndIncrement() ?: 0

        return instances[currentSeq % instances.size]
    }
}