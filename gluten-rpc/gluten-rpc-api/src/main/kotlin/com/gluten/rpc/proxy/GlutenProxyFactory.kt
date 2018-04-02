package com.gluten.rpc.proxy

import com.gluten.rpc.Filter
import com.gluten.rpc.Invoker
import javassist.util.proxy.ProxyFactory
import javassist.util.proxy.ProxyObject
import java.lang.reflect.Proxy
import java.util.*

class GlutenProxyFactory {
    companion object {
        fun <T> newJdkProxyInstance(invoker: Invoker, itface: Class<*>, filterChain: LinkedList<Filter>?): T {
            return Proxy.newProxyInstance(itface.classLoader, arrayOf(itface), GlutenProxy(invoker, itface, filterChain)) as T
        }

        fun <T> newInstance(invoker: Invoker, itface: Class<*>, filterChain: LinkedList<Filter>?, proxyKit: ProxyKit): T {
            return when (proxyKit) {
                ProxyKit.JDK -> newJdkProxyInstance(invoker, itface, filterChain)
                ProxyKit.JAVASSIST -> newJavassistProxyInstance(invoker, itface, filterChain)
                else -> TODO("Unknown proxy type!!!")
            }
        }

        fun <T> newJavassistProxyInstance(invoker: Invoker, itface: Class<*>, filterChain: LinkedList<Filter>?): T {
            val proxyFactory = ProxyFactory()
            proxyFactory.superclass = itface

            val clazz: Class<*> = proxyFactory.createClass()
            val instance: Any = clazz.newInstance()
            (instance as ProxyObject).handler = GlutenProxy(invoker, itface, filterChain)
            return instance as T
        }

        fun <T> newDefaultProxyInstance(invoker: Invoker, itface: Class<*>, filterChain: LinkedList<Filter>?): T {
            return newJavassistProxyInstance(invoker, itface, filterChain)
        }
    }
}

enum class ProxyKit {
    JDK, JAVASSIST, CGLIB
}