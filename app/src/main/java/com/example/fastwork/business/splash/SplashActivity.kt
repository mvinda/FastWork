package com.example.fastwork.business.splash

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.bumptech.glide.Glide
import com.example.fastwork.R
import com.example.fastwork.base.BaseActivity
import com.example.fastwork.business.home.HomeActivity
import com.example.fastwork.http.HttpApi
import com.example.fastwork.http.HttpUtils
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : BaseActivity<SplashPresenter>(), SplashContract.View {


    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        if (mPresenter == null) {
            mPresenter = SplashPresenter()
        }

        splashImage.setOnClickListener { mPresenter?.onSplashImageClick() }
        splashJump.setOnClickListener { mPresenter?.onJumpClick() }

    }

    override fun initData() {
        mPresenter?.attachView(this)
        mPresenter?.init()
    }


    override fun setSplashImage(image: String?) {
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.fillAfter = true
        alphaAnimation.duration = 500
        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAnimationEnd(animation: Animation?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onAnimationRepeat(animation: Animation?) {
                Glide.with(this@SplashActivity)
                    .load(image)
                    .into(splashImage)
            }

        })


//        val httpUtils = HttpUtils()
//        val createApi = httpUtils.createApi(HttpApi::class.java).wangzhanzuo().compose(HttpTransformer<BaseDataModel<MarketWindowModel>, MarketWindowModel>());

    }

    override fun setSplashImageVisibility(visibility: Int) {
        splashImage.visibility = visibility
    }

    override fun setJumpText(text: String) {
        splashJump.text = text
    }

    override fun setJumpVisibility(visibility: Int) {
        splashJump.visibility = visibility
    }

    override fun startHome() {
        HomeActivity.open(this)
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    override fun hideDialog() {
        //nothing
    }

    override fun showDialog() {
        //nothing
    }


}