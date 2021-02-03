package com.example.fastwork.http

import com.example.fastwork.http.base_model.BaseDataModel
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*



interface HttpApi {

    @GET
    fun getData(@Url url: String,
                @QueryMap params: Map<String, String>): Observable<ResponseBody>

    @GET
    fun getAsyncData(@Url url: String?,
                     @QueryMap params: Map<String, String>): Call<ResponseBody>

    @FormUrlEncoded
    @POST
    fun postData(@Url url: String,
                 @FieldMap params: Map<String, String>): Observable<ResponseBody>

    @FormUrlEncoded
    @POST
    fun postAsyncData(@Url url: String,
                      @FieldMap params: Map<String, String>): Call<ResponseBody>

    @PUT
    fun putData(@Url url: String,
                @Body params: Map<String, String>): Observable<ResponseBody>

    @PUT
    fun putAsyncData(@Url url: String,
                     @Body params: Map<String, String>): Call<ResponseBody>

    @DELETE
    fun deleteData(@Url url: String,
                   @Body params: Map<String, String>): Observable<ResponseBody>

    @DELETE
    fun deleteAsyncData(@Url url: String,
                        @Body params: Map<String, String>): Call<ResponseBody>





}
