package com.example.fastwork.http

interface IHttpUtils {

    /**
     * 根据传入的 Class 获取对应的 Retrofit Service
     *
     * @param service 接口 Class
     */
    fun <T> createApi(service: Class<T>): T
}