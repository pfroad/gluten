package com.gluten.rpc.lb

import com.gluten.common.Node
import com.gluten.common.URL
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicInteger

class RoundRobinLoadBalance: AbstractLoadBalance() {
    private val sequences: ConcurrentMap<String, AtomicInteger> = ConcurrentHashMap()

    override fun doSelect(instances: List<Node>, url: String): Node {
        var sequence: AtomicInteger? = sequences[url]

        if (sequence == null) {
            sequences.putIfAbsent(url, AtomicInteger())
            sequence = sequences[url]
        }

        val currentSeq: Int = sequence?.getAndIncrement() ?: 0

        return instances[currentSeq % instances.size]
    }
}