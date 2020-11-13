package com.example.fastwork.base

import com.example.fastwork.business.splash.SplashContract

open class BasePresenter : IBasePresenter {
    var mView: SplashContract.View? = null

    override fun detachView() {
        if (mView != null) {
            mView = null
        }
    }

    override fun attachView(view: IBaseView) {
        mView = view as SplashContract.View
    }
}