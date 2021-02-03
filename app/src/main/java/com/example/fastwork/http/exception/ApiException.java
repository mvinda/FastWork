package com.example.fastwork.http.exception;

import android.text.TextUtils;



public class ApiException extends RuntimeException {

    private static final String TAG = "ApiException";

    public static final int CODE_NETWORK_EXCEPTION = -2;
    public static final int CODE_GET_DATA_FAILED = -3;
    public static final int CODE_PARSE_DATA_ERROR = -4;  //数据解析出错（JSONException）
    public static final int CODE_SESSION_EXPIRED = 106; //session过期
    public static final int CODE_TOKEN_EXPIRED = 402;  //Token过期
    public static final int CODE_NOT_FOUND = 404;
    public static final int CODE_GET_WAY_TIMEOUT = 504;

    private int mResultCode;

    public ApiException(int resultCode) {
        super(getApiExceptionMessage(resultCode));
        mResultCode = resultCode;
    }

    public ApiException(int resultCode, String message) {
        super(TextUtils.isEmpty(getApiExceptionMessage(resultCode)) ? message : getApiExceptionMessage(resultCode));
        mResultCode = resultCode;
    }

    /**
     * 将错误码转换成为错误信息
     *
     * @param code 错误码
     * @return 错误信息
     */
    private static String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {

            case CODE_GET_WAY_TIMEOUT:
            case CODE_NETWORK_EXCEPTION:
                message = "网络异常";
                break;

            case CODE_PARSE_DATA_ERROR:
                message = "数据解析出错";
                break;

            case CODE_GET_DATA_FAILED:
                message = "获取数据失败";
                break;

            case CODE_NOT_FOUND:
                message = "网络地址不存在";
                break;

            //session & token 过期
            case CODE_TOKEN_EXPIRED:
            case CODE_SESSION_EXPIRED:
                message = "登录过期，请重新登录";
                break;

            default:
                break;
        }
        return message;
    }

    public int getResultCode() {
        return mResultCode;
    }
}
