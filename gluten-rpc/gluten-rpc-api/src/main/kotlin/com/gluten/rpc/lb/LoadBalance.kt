package com.gluten.rpc.lb

import com.gluten.common.Node
import com.gluten.common.URL

interface LoadBalance {
    fun select(instances: List<Node>, url: URL): Node
}

abstract class AbstractLoadBalance: LoadBalance {

    override fun select(instances: List<Node>, url: URL): Node {
        if (instances?.size > 1) {
            return doSelect(instances, url)
        }

        return instances?.first()
    }

    abstract fun doSelect(invokers: List<Node>, url: URL): Node
}