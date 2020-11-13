package com.example.fastwork.base

import android.app.Activity
import android.os.Bundle
import android.util.Log
import butterknife.ButterKnife
import com.example.fastwork.business.splash.SplashPresenter

abstract class BaseActivity : Activity() {
    private var mIsTopActivity = false
    open var mPresenter: SplashPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter = null
        }
    }


}