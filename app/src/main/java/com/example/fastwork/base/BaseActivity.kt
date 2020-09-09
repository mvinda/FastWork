package com.example.fastwork.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife

abstract class BaseActivity : AppCompatActivity() {
    private var mIsTopActivity = false


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(getLayoutId())
        ButterKnife.bind(this)
        init()
    }

    protected abstract fun getLayoutId(): Int

    private fun init() {
        initView()
        initData()
    }

    abstract fun initData()

    abstract fun initView()

    override fun onResume() {
        super.onResume()
        mIsTopActivity = true

    }

    override fun onPause() {
        super.onPause()
        mIsTopActivity = false
    }


}