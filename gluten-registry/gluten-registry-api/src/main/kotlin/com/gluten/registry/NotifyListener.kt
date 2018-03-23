package com.gluten.registry

import com.gluten.common.URL

interface NotifyListener {
    fun notify(url: URL)
}