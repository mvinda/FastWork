package com.example.fastwork.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder

abstract class BaseFragment : Fragment() {

    private lateinit var mView: View
    private lateinit var mUnbinder: Unbinder
    protected var mIsInitData: Boolean = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (getLayoutId() > 0) {
            mView = inflater.inflate(getLayoutId(), container, false)
            mUnbinder = ButterKnife.bind(this, mView)
            return mView
        } else {
            return super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    abstract fun initData()


    abstract fun getLayoutId(): Int

    override fun onDestroyView() {
        super.onDestroyView()
        mUnbinder?.unbind()
    }

}
