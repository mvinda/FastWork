package com.maka.app.util.cache

import android.content.Context

interface CacheType {

    companion object {
        const val RETROFIT_SERVICE_CACHE_TYPE_ID = 0
    }

    fun getCacheTypeId(): Int

    fun calculateCacheSize(context: Context): Int
}