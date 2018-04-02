package com.gluten.rpc.http

import com.gluten.common.Node
import com.gluten.common.URL
import com.gluten.registry.GlutenRegistry
import com.gluten.rpc.AbstractInvoker
import com.gluten.rpc.Invocation
import com.gluten.rpc.Result
import com.gluten.rpc.RpcResult
import com.gluten.rpc.http.utils.HttpUtils
import com.gluten.rpc.lb.LoadBalance
import org.apache.http.HttpEntity
import org.apache.http.HttpHeaders
import org.apache.http.ParseException
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.util.EntityUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import java.security.NoSuchAlgorithmException

class HttpInvoker(private val httpClient: CloseableHttpClient,
                  private var registry: GlutenRegistry?,
                  private var loadBalance: LoadBalance?): AbstractInvoker(registry, loadBalance) {

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(HttpInvoker::class.java)
    }

    override fun doInvoke(instance: Node, invocation: Invocation): RpcResult? {
        val requestURL: String? = requestURL(instance, invocation)
        val post = HttpPost(requestURL)
        var response: CloseableHttpResponse? = null

        try {
            val nvps = HttpUtils.buildValuePair(request.parameters)
            post.entity = UrlEncodedFormEntity(nvps!!, "UTF-8")
            val start = System.currentTimeMillis()
            response = this.httpClient.execute(post)
            logger.info("It spends " + (System.currentTimeMillis() - start) + " ms to finish this job!")
            val status = response!!.statusLine
            if (status.statusCode == 200) {
                val entity = response.entity

                try {
                    res = EntityUtils.toString(entity)
                    EntityUtils.consume(entity)
                } catch (var24: ParseException) {
                }

            } else {
                logger.info(response?.statusLine.reasonPhrase)
            }
        } catch (var25: IOException) {
        } catch (var26: NoSuchAlgorithmException) {
        } finally {
            try {

                response?.close()
            } catch (var23: IOException) {
            }

        }

        return res
    }

    fun doGet(requestUrl: String, request: RpcRequest): RpcResponse? {
        val queryParams: String = HttpUtils.queryParams(request.parameters)

        var response: CloseableHttpResponse? = null
        var entity: HttpEntity? = null

        try {
            val get = HttpGet(requestUrl + queryParams)
            response = httpClient.execute(get)
            val status = response.getStatusLine()
            if (status.statusCode == 200) {
                val entity = response.entity

                try {
                    res = EntityUtils.toString(entity)
                    EntityUtils.consume(entity)
                } catch (var24: ParseException) {
                }

            } else {
                logger.info(response?.statusLine.reasonPhrase)
            }
        } catch (e: IOException) {
        } catch (e: NoSuchAlgorithmException) {
        } finally {
            try {
                response?.close()
            } catch (e: IOException) {
            }
        }


    }

    fun receiveResponse(response: CloseableHttpResponse): RpcResponse? {
        val status = response.statusLine
        if (status.statusCode == 200) {
            val entity = response.entity
            response.getHeaders(HttpHeaders.CONTENT_TYPE)

            try {
                res = EntityUtils.toString(entity)
                EntityUtils.consume(entity)
            } catch (var24: ParseException) {
            }

        } else {
            logger.info(response?.statusLine.reasonPhrase)
        }
    }

    fun requestURL(instance: Node, invocation: Invocation): String? {
//        if (instance.protocol != url.protocol)
//            return null
        val requestURL = StringBuffer()
        requestURL.append(instance.protocol.value)
        requestURL.append("://")
        requestURL.append(instance.host)
        if (instance.port != 80) {
            requestURL.append(":")
            requestURL.append(instance.port)
        }

        requestURL.append("/")
        requestURL.append(invocation.getRequestMapping())

//        val queryParams: Map<String, String> = invocation.parameters

        return requestURL.toString()
    }
}

class HttpInvokers() {

}