package com.gluten.rpc.http

import com.gluten.registry.GlutenRegistry
import com.gluten.rpc.lb.LoadBalance
import org.apache.http.HttpResponse
import org.apache.http.client.HttpRequestRetryHandler
import org.apache.http.client.config.RequestConfig
import org.apache.http.conn.ConnectionKeepAliveStrategy
import org.apache.http.impl.client.*
import org.apache.http.protocol.HttpContext
import org.apache.http.util.Args
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext

class HttpInvokerBuilder {
    private var sslContext: SSLContext? = null
    private var retryHandler: HttpRequestRetryHandler = DefaultHttpRequestRetryHandler(0, false)
    private var connectionKeepAliveStrategy: ConnectionKeepAliveStrategy = object: DefaultConnectionKeepAliveStrategy() {
        override fun getKeepAliveDuration(response: HttpResponse?, context: HttpContext?): Long {
//            return super.getKeepAliveDuration(response, context)
            var keepAlive: Long = super.getKeepAliveDuration(response, context)
            if (keepAlive == -1L) {
                keepAlive = 5000
            }
            return keepAlive
        }
    }
    private var lb: LoadBalance? = null
    private var glutenRegistry: GlutenRegistry? = null
//    private var requestLast: LinkedList<HttpRequestInterceptor>? = null
//    private var responseLast: LinkedList<HttpResponseInterceptor>? = null
//    private var requestFirst: LinkedList<HttpRequestInterceptor>? = null
//    private var responseFirst: LinkedList<HttpResponseInterceptor>? = null

    private var evictExpiredConnections: Boolean = false
    private var evictIdleConnections: Boolean = false
    private var maxIdleTime: Long = 0

    private var socketTimeout: Int = 3000
    private var connectTimeout: Int = 1000
    private var connTimeToLive: Long = -1   // MILLISECONDS
//    private var keepAlive: Boolean? = null
    private var maxConnTotal: Int = 0
    private var maxConnTotalPerRoute: Int = 0
    private var connectionRequestTimeout: Int = 500

    fun setSSLContext(sslContext: SSLContext): HttpInvokerBuilder {
        this.sslContext = sslContext
        return this
    }

    fun setRetryHandler(httpRequestRetryHandler: HttpRequestRetryHandler): HttpInvokerBuilder {
        this.retryHandler = httpRequestRetryHandler
        return this
    }

    fun setConnectionKeepAliveStrategy(connectionKeepAliveStrategy: ConnectionKeepAliveStrategy): HttpInvokerBuilder {
        this.connectionKeepAliveStrategy = connectionKeepAliveStrategy
        return this
    }

//    /**
//     * Adds this protocol interceptor to the tail of the protocol processing list.
//     *
//     * @param httpRequestInterceptor
//     */
//    fun addInterceptorLast(httpRequestInterceptor: HttpRequestInterceptor): HttpInvokerBuilder {
//        if (httpRequestInterceptor == null)
//            return this
//
//        if (requestLast == null) {
//            requestLast = LinkedList()
//        }
//        requestLast!!.addLast(httpRequestInterceptor)
//        return this
//    }
//
//    /**
//     * Adds this protocol interceptor to the tail of the protocol processing list.
//     *
//     * @param httpResponseInterceptor
//     */
//    fun addInterceptorLast(httpResponseInterceptor: HttpResponseInterceptor): HttpInvokerBuilder {
//        if (httpResponseInterceptor != null)
//            return this
//
//        if (responseLast == null) {
//            responseLast = LinkedList()
//        }
//        responseLast!!.addLast(httpResponseInterceptor)
//        return this
//    }
//
//    /**
//     * Adds this protocol interceptor to the head of the protocol processing list.
//     *
//     * @param httpRequestInterceptor
//     */
//    fun addInterceptorFirst(httpRequestInterceptor: HttpRequestInterceptor): HttpInvokerBuilder {
//        if (httpRequestInterceptor == null)
//            return this
//
//        if (requestFirst == null) {
//            requestFirst = LinkedList()
//        }
//        requestFirst!!.addFirst(httpRequestInterceptor)
//        return this
//    }
//
//    /**
//     * Adds this protocol interceptor to the head of the protocol processing list.
//     *
//     * @param httpResponseInterceptor
//     */
//    fun addInterceptorFirst(httpResponseInterceptor: HttpResponseInterceptor): HttpInvokerBuilder {
//        if (httpResponseInterceptor != null)
//            return this
//
//        if (responseFirst == null) {
//            responseFirst = LinkedList()
//        }
//        responseFirst!!.addFirst(httpResponseInterceptor)
//        return this
//    }

    fun setEvictExpiredConnections(evictExpiredConnections: Boolean = false): HttpInvokerBuilder {
        this.evictExpiredConnections = evictExpiredConnections
        return this
    }

    fun setEvictIdleConnections(evictIdleConnections: Boolean = false): HttpInvokerBuilder {
        this.evictIdleConnections = evictIdleConnections
        return this
    }

    /**
     * interval for run evict idle http connection task
     *
     * @param maxIdleTime
     */
    fun setMaxIdleTime(maxIdleTime: Long = 0): HttpInvokerBuilder {
        this.maxIdleTime = maxIdleTime
        return this
    }

    /**
     * Max wait time for client to transaction data
     *
     * @param socketTimeout
     */
    fun setSocketTimeout(socketTimeout: Int = 0): HttpInvokerBuilder {
        this.socketTimeout = socketTimeout
        return this
    }

    /**
     * Max wait time to connect server in request
     *
     * @param connectTimeout
     */
    fun setConnectTimeout(connectTimeout: Int = 0): HttpInvokerBuilder {
        this.connectTimeout = connectTimeout
        return this
    }


    /**
     * Http connection live time, ms
     *
     * @param connTimeToLive
     */
    fun setConnTimeToLive(connTimeToLive: Long = 0): HttpInvokerBuilder {
        this.connTimeToLive = connTimeToLive
        return this
    }

    /**
     * Max connections in httpclient
     *
     * @param maxTotal
     */
    fun setMaxConnTotal(maxTotal: Int = 0): HttpInvokerBuilder {
        this.maxConnTotal = maxTotal
        return this
    }

    /**
     * Max connections to a host in http client
     *
     * @param connMaxTotalPerRoute
     */
    fun setMaxConnTotalPerRoute(maxConnTotalPerRoute: Int = 0): HttpInvokerBuilder {
        this.maxConnTotalPerRoute = maxConnTotalPerRoute
        return this
    }

    /**
     * Max wait time to request a connection from connection pool
     *
     * @param connectionRequestTimeout
     */
    fun setConnectionRequestTimeout(connectionRequestTimeout: Int = 500): HttpInvokerBuilder {
        this.connectionRequestTimeout = connectionRequestTimeout
        return this
    }

    fun build(): HttpInvoker {
        Args.notNull(this.retryHandler, "Retry handler cannot be null")
        Args.notNull(this.connectionKeepAliveStrategy, "Connection keep alive strategy cannot be null")

        val httpClientBuilder: HttpClientBuilder = HttpClients.custom()

        httpClientBuilder.setRetryHandler(retryHandler)
        httpClientBuilder.setKeepAliveStrategy(connectionKeepAliveStrategy)
        if (sslContext != null) {
            httpClientBuilder.setSSLContext(sslContext)
        }

        if (this.evictExpiredConnections) {
            httpClientBuilder.evictExpiredConnections()
        }
        if (this.evictIdleConnections) {
            httpClientBuilder.evictIdleConnections(this.maxIdleTime, TimeUnit.MILLISECONDS)
        }

        val requestConfig: RequestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(this.connectionRequestTimeout)
                .build()
        httpClientBuilder.setDefaultRequestConfig(requestConfig)
        httpClientBuilder.setConnectionTimeToLive(this.connTimeToLive, TimeUnit.MILLISECONDS)
        httpClientBuilder.setMaxConnTotal(this.maxConnTotal)
        httpClientBuilder.setMaxConnPerRoute(this.maxConnTotalPerRoute)

        val httpClient: CloseableHttpClient = httpClientBuilder.build()

        return HttpInvoker(httpClient, glutenRegistry, lb)
    }
}