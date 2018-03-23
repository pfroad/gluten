package com.gluten.rpc.lb

import com.gluten.common.Node
import com.gluten.common.URL
import java.util.*

class RandomLoadBalance: AbstractLoadBalance() {

    private val random: Random = Random()

    override fun doSelect(instances: List<Node>, url: URL): Node {
        val isSameWeight: Boolean = instances.map { instance -> instance.weight }.distinct().size == 1

        if (isSameWeight) {
            val totalWeight: Int = instances
                    .map { instance -> instance.weight }
                    .reduce { total, weight -> total + weight }

            var offset: Int = random.nextInt(totalWeight)

            for (instance: Node in instances) {
                offset -= instance.weight

                if (offset < 0) {
                    return instance
                }
            }
        }

        return instances[random.nextInt(instances.size)]
    }

}