package com.gluten.rpc

import com.gluten.common.Node
import com.gluten.registry.GlutenRegistry
import com.gluten.rpc.lb.LoadBalance

interface Invoker {
    fun invoke(invocation: Invocation): RpcResult?
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

    override fun invoke(invocation: Invocation): RpcResult? {
        val instances: List<Node> = registry!!.lookup(invocation.provider)

        if (instances == null || instances.isEmpty())
            return null

        val instance: Node? = loadBalance?.select(instances, invocation.provider)

        if (instance == null || !instance.isAvailable) {
            throw RpcException(RpcException.NETWORK_EXCEPTION, "No available service node!")
        }

        return doInvoke(instance, invocation)
    }

    abstract fun doInvoke(instance: Node, invocation: Invocation): RpcResult?
}