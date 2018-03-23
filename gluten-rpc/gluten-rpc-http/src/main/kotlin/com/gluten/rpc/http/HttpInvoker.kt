package com.gluten.rpc.http

import com.gluten.common.Node
import com.gluten.common.URL
import com.gluten.registry.GlutenRegistry
import com.gluten.rpc.AbstractInvoker
import com.gluten.rpc.Result
import com.gluten.rpc.lb.LoadBalance
import org.apache.http.impl.client.CloseableHttpClient

class HttpInvoker(private val httpClient: CloseableHttpClient,
                  private var registry: GlutenRegistry?,
                  private var loadBalance: LoadBalance?): AbstractInvoker(registry, loadBalance) {
    override fun doInvoke(instance: Node?, url: URL): Result? {

    }
}