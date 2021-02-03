package com.example.fastwork.base

import android.app.Activity
import android.os.Bundle
import butterknife.ButterKnife

abstract class BaseActivity<P : BasePresenter<*>> : Activity() {
    private var mIsTopActivity = false

    var mPresenter: P? = null


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

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter?.detachView()
            mPresenter = null
        }

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