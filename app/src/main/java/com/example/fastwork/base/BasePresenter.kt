package com.example.fastwork.base

import com.example.fastwork.utils.log.Lg


open class BasePresenter<T : IBaseView> : IBasePresenter {
    var mView: T? = null


    override fun detachView() {
        if (mView != null) {
            mView = null
        }
    }

    fun attachView(view: T) {
        mView = view
    }
}