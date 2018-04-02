package com.gluten.rpc

import com.gluten.rpc.annotations.GlutenClient
import com.gluten.rpc.annotations.MethodVersion
import com.gluten.rpc.annotations.RequestMapping
import java.lang.reflect.Method
import java.util.*

data class Invocation(val className: String,
                      val methodName: String,
                      val provider: String,
                      val parameterTypes: MutableMap<String, String>?,
                      val parameters: MutableMap<String, Any>?,
                      val returnType: Class<*>?,
                      val annotations: Array<out Annotation>?,
                      val parameterAnnotations: Array<out Array<out Annotation>>?,
                      val version: String? = null) {

    companion object {
        @Throws(Throwable::class)
        fun createInvocation(method: Method, args: Array<out Any>?): Invocation {
            val provider = method.declaringClass.getAnnotation(GlutenClient::class.java)?.value
            assert(provider == null)

            val className = method.declaringClass.simpleName
            val methodName = method.name
            var parameters: MutableMap<String, Any>? = HashMap()
            var parameterTypes: MutableMap<String, String>? = HashMap()

            if (args != null && args.isNotEmpty()) {
                parameters= HashMap()
                parameterTypes = HashMap()

                val methodParameters = method.parameters

                for (i in methodParameters.indices) {
                    val parameterName = methodParameters[i].name
                    parameters[parameterName] = args[i]
                    parameterTypes[parameterName] = args[i].javaClass.typeName
                }
            }

            val annotations = method.declaredAnnotations
            val returnType = method.returnType
            val parameterAnnotations = method.parameterAnnotations
            val version = method.getAnnotation(MethodVersion::class.java)?.version

            return Invocation(className,
                    methodName,
                    provider!!,
                    parameterTypes,
                    parameters,
                    returnType,
                    annotations,
                    parameterAnnotations,
                    version)
        }
    }

    fun getRequestMapping(): String {
        return getAnnotation(RequestMapping::class.java)?.value?.get(0) ?: methodName
    }

    fun <T> getAnnotation(annotationType: Class<T>): T? {
        return annotations?.find { it.javaClass == annotationType } as T ?: null
    }

    override fun hashCode(): Int {
        var result = className.hashCode()
        result = 31 * result + methodName.hashCode()
        result = 31 * result + (parameterTypes?.hashCode() ?: 0)
        result = 31 * result + (parameters?.hashCode() ?: 0)
        result = 31 * result + (returnType?.hashCode() ?: 0)
        result = 31 * result + (annotations?.let { Arrays.hashCode(it) } ?: 0)
        result = 31 * result + (parameterAnnotations?.let { Arrays.hashCode(it) } ?: 0)
        result = 31 * result + (version?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Invocation

        if (className != other.className) return false
        if (methodName != other.methodName) return false
        if (parameterTypes != other.parameterTypes) return false
        if (parameters != other.parameters) return false
        if (returnType != other.returnType) return false
        if (!Arrays.equals(annotations, other.annotations)) return false
        if (!Arrays.equals(parameterAnnotations, other.parameterAnnotations)) return false
        if (version != other.version) return false

        return true
    }
}