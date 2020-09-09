package com.example.fastwork.component.mvp

/**
 * Created by WHD on 2019/4/1.
 *
 *
 * MVPPlugin
 */
interface BasePresenter<V : Any> {

    fun init()

    fun attachView(view: V)

    fun detachView()
}
