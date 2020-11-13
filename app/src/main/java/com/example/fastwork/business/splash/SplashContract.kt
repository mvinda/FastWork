package com.example.fastwork.business.splash

import com.example.fastwork.base.IBasePresenter
import com.example.fastwork.base.IBaseView

interface SplashContract {
    interface View : IBaseView {
        fun setSplashImage(image: String?)
        fun setSplashImageVisibility(visibility: Int)
        fun setJumpText(text: String)
        fun setJumpVisibility(visibility: Int)
        fun startHome()
    }

    interface Presenter {
        fun init()
        fun onSplashImageClick()
        fun onJumpClick()
    }
}

