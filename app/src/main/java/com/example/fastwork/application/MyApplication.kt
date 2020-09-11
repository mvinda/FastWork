package com.example.fastwork.application

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.tencent.bugly.Bugly

class MyApplication :Application(){


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        Bugly.init(applicationContext, "1199c47db5", false)
    }
}