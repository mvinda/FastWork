package com.example.fastwork.business.home

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.fastwork.BuildConfig
import com.example.fastwork.R
import com.example.fastwork.utils.dialog.CustomerInputDialog
import com.example.fastwork.utils.dialog.DialogUtil
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : Activity() {
    private val DEFAULT_TIME_OUT = (if (BuildConfig.DEBUG) 120 else 60).toLong()
;

    val MAX_CACHE_SIZE = (1024 * 1024 * 20).toLong()


    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btn_webView.setOnClickListener {
            Toast.makeText(this, "测试webView框架", Toast.LENGTH_LONG).show()
        }
        val dialogUtil = DialogUtil(this)


        btn_dialog.setOnClickListener {

            dialogUtil.showAlertDialog("牛逼对话框", "牛逼对话框叫牛逼", "取消", "确认"
                , { dialog: DialogInterface, i: Int ->
                    Log.i("whd", "i" + i)

                }, { dialog: DialogInterface, i: Int ->
                    Log.i("whd", "i" + i)
                })
        }



        btn_dialog2.setOnClickListener {
            dialogUtil.showInputDialog("牛逼的输入框标题", "牛逼的输入框", "取消",
                "确认", "牛逼的提示语", { dialog: DialogInterface, index: Int ->

                }, { dialog: CustomerInputDialog, message: String ->
                    run {
                        dialog.dismiss()
                        Log.d("whd", message)

                    }
                })
        }

    }
}
