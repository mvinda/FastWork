package com.maka.app.util.http.interceptor

import com.example.fastwork.business.user.UserManager
import com.example.fastwork.http.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response


class CacheInterceptor : Interceptor {

    private val mNotCacheSet = hashSetOf<String>()

    init {
        mNotCacheSet.add("/api/v1/users/${UserManager.getInstance().userId}")
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val noCache = mNotCacheSet.contains(request.url().uri().path)
        //没网络的情况强制读取缓存(必须判断，避免断网情况下获取不到缓存)
        if (!NetworkUtils.isConnected() && !noCache) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
        }
        val response = chain.proceed(request)
        return if (NetworkUtils.isConnected() || noCache) {
            val maxAge = 0 //有网络时，不缓存，最大保存时长为0(而且获取用户信息接口不缓存)
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .build()
        } else {
            val maxStale = 60 * 60 * 24 * 28  //没有网络的情况下，缓存失效的时间为4周
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
        }
    }
}