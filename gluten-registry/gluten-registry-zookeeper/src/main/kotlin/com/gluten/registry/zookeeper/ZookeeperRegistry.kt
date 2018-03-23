package com.gluten.registry.zookeeper

import com.gluten.common.Node
import com.gluten.common.URL
import com.gluten.registry.GlutenRegistry
import com.gluten.registry.NotifyListener

class ZookeeperRegistry: GlutenRegistry {
    override fun register(node: Node) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unregister(node: Node) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun subscribe(node: Node, listener: NotifyListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun ubsubscribe(node: Node, listener: NotifyListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lookup(url: URL): List<Node> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}