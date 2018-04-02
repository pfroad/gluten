package com.gluten.rpc

data class GlutenRequest(private val parameters: MutableMap<String, Any> = HashMap()) {
    fun addParameter(key: String, value: Any) {
        this.parameters!![key] = value
    }

    fun getParameter(key: String): Any? {
        return this.parameters!![key]
    }

    @Throws(NumberFormatException::class)
    fun getInt(key: String): Int? {
        return if (containKey(key)) Integer.parseInt(this.parameters!![key]?.toString()) else null
    }

    @Throws(NumberFormatException::class)
    fun getLong(key: String): Long? {
        return if (containKey(key)) java.lang.Long.parseLong(this.parameters!![key]?.toString()) else null
    }

    @Throws(NumberFormatException::class)
    fun getDouble(key: String): Double? {
        return if (containKey(key)) java.lang.Double.parseDouble(this.parameters!![key]?.toString()) else null
    }

    fun getString(key: String): String? {
        return if (containKey(key)) this.parameters!![key]?.toString() else null
    }

    fun containKey(key: String): Boolean {
        return this.parameters!!.containsKey(key)
    }
}