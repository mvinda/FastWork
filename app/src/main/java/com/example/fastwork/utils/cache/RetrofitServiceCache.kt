package com.maka.app.util.cache

import android.app.ActivityManager
import android.content.Context

class RetrofitServiceCache : CacheType {

    companion object {
        private const val MAX_SIZE = 150
        private const val MAX_SIZE_MULTIPLIER = 0.002f
    }

    override fun getCacheTypeId(): Int {
        return CacheType.RETROFIT_SERVICE_CACHE_TYPE_ID
    }

    override fun calculateCacheSize(context: Context): Int {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val cacheSize = (activityManager.memoryClass * MAX_SIZE_MULTIPLIER * 1024).toInt()
        if (cacheSize >= MAX_SIZE)
            return MAX_SIZE
        return cacheSize
    }
}