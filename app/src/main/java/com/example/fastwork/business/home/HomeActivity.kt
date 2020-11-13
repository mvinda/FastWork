package com.example.fastwork.business.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.fastwork.R
import com.example.fastwork.base.BaseActivity
import com.example.fastwork.utils.log.Lg

class HomeActivity : Activity() {

    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }


}
