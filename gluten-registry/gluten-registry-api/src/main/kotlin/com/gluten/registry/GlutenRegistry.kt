package com.gluten.registry

import com.gluten.common.Node
import com.gluten.common.URL

interface GlutenRegistry {
    /**
     * register service node to registry center
     *
     * @param node: service node
     */
    fun register(node: Node)

    /**
     * unregister service node
     *
     * @param node: service node
     */
    fun unregister(node: Node)

    /**
     * subscribe service node
     *
     * @param node: service node
     * @param listener: process node update (update, delete. etc) event
     */
    fun subscribe(node: Node, listener: NotifyListener)

    /**
     * unsubscribe service node
     *
     * @param node: service node
     * @param listener: process node update (update, delete. etc) event
     */
    fun ubsubscribe(node: Node, listener: NotifyListener)

    /**
     * lookup service nodes for request url
     *
     * @param url: request url
     */
    fun lookup(provider: String): List<Node>
}