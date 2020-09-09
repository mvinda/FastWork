package com.example.fastwork.business

import android.os.Bundle
import com.example.fastwork.R
import com.example.fastwork.base.BaseActivity
import com.example.fastwork.utils.grant.PermissionsManager
import com.example.fastwork.utils.grant.PermissionsResultAction
import java.security.Permissions

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        PermissionsManager.instance.requestPermissionsIfNecessaryForResult(this,
            arrayOf(android.Manifest.permission.ACCESS_WIFI_STATE), object : PermissionsResultAction() {
                override fun onDenied(permission: String) {
                }

                override fun onGranted() {
                }
            })
    }
}
