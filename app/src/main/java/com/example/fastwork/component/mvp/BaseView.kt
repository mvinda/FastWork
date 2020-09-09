package com.example.fastwork.component.mvp
import android.content.Context
import com.example.fastwork.component.proguard.KeepClass
import io.reactivex.annotations.NonNull




/**
 * Created by Henry on 2019/4/1.
 *
 *
 * MVPPlugin 推荐实现
 *
 *
 * 常用业务抽象，建议使用继承关系，以达到统一的标准
 * 但请注意BaseView，并不是MVP实现的必须标准，也可不继承此类
 */
@KeepClass
interface BaseView {
    /**
     * 上下文对象，已经在[BasePresenterImpl]中实现，注意有区别与原本控件的Context，
     * 默认实现为ApplicationContext
     *
     * @return
     */
    @get:NonNull
    val context: Context

    /**
     * 调用使用注解定义的视图接口
     *
     * @param interfaceName 接口名
     * @param args          参数
     */
    fun invokeMethod(interfaceName: String, vararg args: Any)

    /**
     * 设置View注解里的实现类
     *
     * @param annotationView View注解的实现类
     */
    fun setAnnotationView(annotationView: Any)

}
