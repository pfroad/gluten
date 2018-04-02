package com.gluten.rpc.proxy

import com.gluten.rpc.Filter
import com.gluten.rpc.Invocation
import com.gluten.rpc.Invoker
import javassist.util.proxy.MethodHandler
import java.io.Serializable
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.util.*

open class GlutenProxy: MethodHandler, InvocationHandler, Serializable {
    private val invoker: Invoker
    private val itface: Class<*>
    private val filterChain: LinkedList<Filter>?

    constructor(invoker: Invoker, itface: Class<*>): this(invoker, itface, LinkedList<Filter>())

    constructor(invoker: Invoker, itface: Class<*>, filterChain: LinkedList<Filter>?) {
        this.invoker = invoker
        this.itface = itface
        this.filterChain = filterChain
    }

    @Throws(Throwable::class)
    override fun invoke(self: Any?, thisMethod: Method?, proceed: Method?, args: Array<out Any>?): Any {
        return doInvoke(thisMethod, args)
    }

    @Throws(Throwable::class)
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        return doInvoke(method, args)
    }

    @Throws(Throwable::class)
    open fun doInvoke(method: Method?, args: Array<out Any>?): Any {
        val invocation: Invocation = Invocation.createInvocation(method!!, args)

        filterChain?.forEach({
            it.filter(invocation)
        })

        return invoker.invoke(invocation)
    }
}