package com.example.fastwork.http.interceptor;

import com.example.fastwork.http.exception.ApiException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class StatusCodeInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        int statusCode = response.code();  //拿的是HTTP请求的状态码
        switch (statusCode) {
            case 401:
            case 402:
                throw new ApiException(ApiException.CODE_SESSION_EXPIRED);
        }
        return response;
    }
}
