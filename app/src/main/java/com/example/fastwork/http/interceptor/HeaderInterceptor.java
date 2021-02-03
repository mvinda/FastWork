package com.example.fastwork.http.interceptor;

import android.os.Build;
import android.text.TextUtils;
import com.example.fastwork.application.ApplicationHelper;
import com.example.fastwork.business.user.UserManager;
import com.example.fastwork.http.Key;
import com.example.fastwork.utils.SystemUtil;
import com.example.fastwork.utils.log.Lg;
import com.example.fastwork.utils.secret.DESUtil;
import okhttp3.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class HeaderInterceptor implements Interceptor {

    private static final String TAG = "HeaderInterceptor";
    private static final String VERSION = "version";
    private static final String BUILD = "build";
    private static final String DEVICE = "device";
    private static final String AGENT = "User-Agent";
    private static final String BUNDLE_ID = "bundleId";
    private static final String SIGN = "sign";
    private static final String CHANNEL = "channel";
    private static final String ABTEST_TYPE = "abTestType";
    private static final String PLATFORM_TYPE = "platformType";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        HashMap<String, String> params = null;
        String signature = null;
        String method = oldRequest.method();
        if (!HttpMethod.METHOD_GET.equals(method)) {
            RequestBody body = oldRequest.body();
            if (body instanceof FormBody) {
                FormBody formBody = (FormBody) body;
                params = new HashMap<>();
                for (int i = 0; i < formBody.size(); i++) {
                    params.put(formBody.name(i), formBody.value(i));
//                    params.put(formBody.encodedName(i), formBody.encodedValue(i));
                }
                if (UserManager.isLogin() && UserManager.getInstance() != null) {
                    params.put(Key.KEY_UID, UserManager.getInstance().getUserId());
                    params.put(Key.KEY_TOKEN, UserManager.getInstance().getToken());
                }
                params.put("timestamp", new Date().getTime() + "");
                signature = DESUtil.getSignature(params);
            }
        }
        Request.Builder newBuilder = chain.request()
                .newBuilder()
                .addHeader(BUILD, SystemUtil.getAppVersion() + "")
                .addHeader(VERSION, SystemUtil.getAppVersionName(ApplicationHelper.INSTANCE.getContext()))
                .addHeader(DEVICE, "android")
                .addHeader(AGENT, checkBuildHeader(Build.MODEL + "/" + SystemUtil.getAndroidId()))
                .addHeader(PLATFORM_TYPE, "Android");


        if (UserManager.isLogin() && UserManager.getInstance() != null) {
            newBuilder.addHeader(Key.KEY_UID, UserManager.getInstance().getUserId());
            newBuilder.addHeader(Key.KEY_TOKEN, UserManager.getInstance().getToken());
        }
        if (!TextUtils.isEmpty(signature)) {
            newBuilder.addHeader(SIGN, signature);
        }
        addNewRequestBody(params, newBuilder, method);
        return chain.proceed(newBuilder.build());
    }

    private String checkBuildHeader(String header) {
        if (header == null)
            return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int length = header.length(), i = 0; i < length; ++i) {
            char c = header.charAt(i);
            if ((c > 31 || c == 9) && c < 127) {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    private RequestBody buildNewRequestBody(HashMap<String, String> params) {
        if (params == null)
            return null;
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                continue;
            }
            try {
                builder.add(entry.getKey(), entry.getValue());
            } catch (Throwable e) {
                e.printStackTrace();
                Lg.w(TAG, "build new request body error", e);
            }
        }
        if (UserManager.isLogin()) {
            if (!params.containsKey(Key.KEY_UID)) {
                builder.add(Key.KEY_UID, UserManager.getInstance().getUserId());
            }
            if (!params.containsKey(Key.KEY_TOKEN)) {
                builder.add(Key.KEY_TOKEN, UserManager.getInstance().getToken());
            }
        }
        return builder.build();
    }

    private void addNewRequestBody(HashMap<String, String> params, Request.Builder builder, String method) {
        if (params == null || builder == null)
            return;
        RequestBody requestBody = buildNewRequestBody(params);
        if (requestBody == null)
            return;
        switch (method) {

            case HttpMethod.METHOD_POST:
                builder.post(requestBody);
                break;

            case HttpMethod.METHOD_PUT:
                builder.put(requestBody);
                break;

            case HttpMethod.METHOD_DELETE:
                builder.delete(requestBody);
                break;

            case HttpMethod.METHOD_PATCH:
                builder.patch(requestBody);
                break;

            default:
                break;
        }
    }
}
