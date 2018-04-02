package com.gluten.rpc.http.utils

import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import java.io.UnsupportedEncodingException
import java.security.NoSuchAlgorithmException
import java.util.*


class HttpUtils {
    companion object {
        @Throws(UnsupportedEncodingException::class, NoSuchAlgorithmException::class)
        fun buildValuePair(params: Map<String, Any>?): List<NameValuePair>? {
            var nvps: MutableList<NameValuePair>? = null
            return if (params != null && params.isNotEmpty()) {
                nvps = ArrayList()
                params.forEach { (key, value) -> nvps!!.add(BasicNameValuePair(key, value.toString())) }
                nvps
            } else {
                nvps
            }
        }

        fun queryParams(params: Map<String, Any>): String {
            val builder = StringBuilder()

            val paramKeys: MutableList<String> = ArrayList(params?.keys)
            paramKeys.sort()

            paramKeys?.forEach { key ->
                run {
                    builder.append(key)
                    builder.append("=")
                    builder.append(params[key])
                }
            }

            val queryParams: String = builder.toString()
            return if (queryParams.isNotEmpty()) queryParams.substring(0, queryParams.length - 1) else queryParams
//            builder.append("&")
//            builder.append("sign")
//            builder.append("=")
//            builder.append(HmacMD5Sign.sign(params, HttpConstants.secretKey))
        }
    }
}