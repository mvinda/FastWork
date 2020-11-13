package com.example.fastwork.business.home

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.fastwork.R
import com.example.fastwork.base.BaseActivity
import com.example.fastwork.utils.log.Lg

class HomeActivity : BaseActivity() {

    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }


    override fun initView() {
        Lg.d("whd","whd")
    }

    override fun initData() {
        Lg.d("whd","whd")

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

}
