package com.example.fastwork.application

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.example.fastwork.utils.sp.SharePreferenceUtil
import com.google.gson.Gson
import com.tencent.bugly.Bugly

class MyApplication : Application(), IApplication {

    lateinit var mGson: Gson
    lateinit var mSharePreferenceUtil: SharePreferenceUtil

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        Bugly.init(applicationContext, "1199c47db5", false)

        ApplicationHelper.INSTANCE.init(this)
    }


    override fun getContext(): Context {
        return this
    }

    override fun getPreference(): SharePreferenceUtil {
        if (mSharePreferenceUtil == null) {
            mSharePreferenceUtil = SharePreferenceUtil(this)
        }
        return mSharePreferenceUtil
    }

    override fun getGson(): Gson {
        if (mGson == null) {
            mGson = Gson()
        }
        return mGson
    }


}