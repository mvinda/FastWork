package com.example.fastwork.business.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.fastwork.BuildConfig
import com.example.fastwork.R
import com.example.fastwork.base.AppConfig
import com.example.fastwork.http.HttpApi
import com.example.fastwork.http.HttpUtils
import com.example.fastwork.http.cookie.CookieManager
import com.example.fastwork.http.interceptor.HeaderInterceptor
import com.example.fastwork.http.interceptor.StatusCodeInterceptor
import com.maka.app.util.http.interceptor.CacheInterceptor
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit


class HomeActivity : Activity() {
    private val DEFAULT_TIME_OUT = (if (BuildConfig.DEBUG) 120 else 60).toLong()

    val MAX_CACHE_SIZE = (1024 * 1024 * 20).toLong()

    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btn_webView.setOnClickListener {
            Toast.makeText(this,"测试webView框架",Toast.LENGTH_LONG).show()
        }
    }
}
