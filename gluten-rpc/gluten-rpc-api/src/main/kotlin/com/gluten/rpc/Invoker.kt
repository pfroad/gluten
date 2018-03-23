package com.gluten.rpc

import com.gluten.common.Node
import com.gluten.common.URL
import com.gluten.registry.GlutenRegistry
import com.gluten.registry.zookeeper.ZookeeperRegistry
import com.gluten.rpc.lb.LoadBalance
import com.gluten.rpc.lb.RandomLoadBalance

data class Result(val value: Object,
                  val exception: Throwable) {
    fun hasException(): Boolean {
        return exception != null
    }
}

interface Invoker {
    fun invoke(url: URL): Result?
}

abstract class AbstractInvoker(private var registry: GlutenRegistry?,
                               private var loadBalance: LoadBalance?): Invoker {
//    private var loadBalance: LoadBalance? = null
//
//    private var registry: GlutenRegistry? = null
//
//    constructor(registry: GlutenRegistry?, loadBalance: LoadBalance?) : this() {
//        if (loadBalance == null) {
//            this.loadBalance = RandomLoadBalance()
//        } else {
//            this.loadBalance = loadBalance!!
//        }
//
//        if (registry == null) {
//            this.registry = ZookeeperRegistry()
//        } else {
//            this.registry = registry
//        }
//    }

    override fun invoke(url: URL): Result? {
        val instances: List<Node> = registry!!.lookup(url)

        if (instances == null || instances.isEmpty())
            return null

        val instance: Node? = loadBalance?.select(instances, url)

        return doInvoke(instance, url)
    }

    abstract fun doInvoke(instance: Node?, url: URL): Result?
}