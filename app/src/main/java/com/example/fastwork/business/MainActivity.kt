package com.example.fastwork.business

import android.os.Bundle
import com.example.fastwork.R
import com.example.fastwork.base.BaseActivity
import com.example.fastwork.utils.grant.PermissionsManager
import com.example.fastwork.utils.grant.PermissionsResultAction
import java.security.Permissions

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun getLayoutId(): Int {

        return 0
    }

    override fun initData() {
    }

    override fun initView() {
    }

}
