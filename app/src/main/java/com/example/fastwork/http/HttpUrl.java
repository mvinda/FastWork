package com.example.fastwork.http;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.example.fastwork.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;
import org.json.JSONObject;

public class HttpUrl {
    public static final boolean CAN_SELECT_SERVER = BuildConfig.DEBUG;
    public static final String FILE_RES_CONFIGS = "file_res_configs";
    public static final String KEY_RES_CONFIGS = "key_res_configs";

    public static boolean IS_TEST = true;
    //测试服务器
    public static final String TEST_IP = BuildConfig.DEBUG ? "http://test.maka.im/" : "";
    //正式服务器
    private static final String FORMAL_IP = "http://maka.im/";
    //测试环境项目
    private static final String TEST_PROJECT_URL = BuildConfig.DEBUG ? "http://test.viewer.maka.im/k/" : "";
    //正式环境项目地址
    private static final String FORMAL_PROJECT_URL = "http://viewer.maka.im/k/";
    //测试图片地址
    public static String TEST_PICTURE_URL = BuildConfig.DEBUG ? "http://test.img1.maka.im/" : "";
    //正式图片地址
    public static String PICTURE_URL = "http://img1.maka.im/";
    //测试资源域名
    private static String TEST_RES_URL = BuildConfig.DEBUG ? "http://test.res.maka.im/" : "";
    //正式资源域名
    private static String FORMAL_RES_URL = "http://res.maka.im/";
    public static String FONT_RES_URL = "http://font.maka.im";
    public static String TEST_FONT_RES_URL = "http://test.font.maka.im";

    //测试环境
    public static String IP;
    public static String WEB_IP;
    //项目地址
    public static String PROJECT_URL;

    public static final String MY_CLOUD_MUSIC_END = "/data/uploadedMusic.json";

    //资源域名
    public static String RES_URL;


    public static void setupResHost(org.json.JSONObject param) {
        String imageHost = param.optString("imageHost");
        String jsonHost = param.optString("jsonHost");
        String fontHost = param.optString("fontHost");
        RES_URL = FORMAL_RES_URL = TEST_RES_URL = jsonHost + "/";
        TEST_FONT_RES_URL = FONT_RES_URL = fontHost + "/";
        PICTURE_URL = TEST_PICTURE_URL = imageHost + "/";
    }


    public static void initUrl() {
        if (IP == null) {
            IP = IS_TEST ? TEST_IP : FORMAL_IP;
            PROJECT_URL = IS_TEST ? TEST_PROJECT_URL : FORMAL_PROJECT_URL;
            RES_URL = IS_TEST ? TEST_RES_URL : FORMAL_RES_URL;
        } else {
            PROJECT_URL = TEST_PROJECT_URL;
        }

        if (TextUtils.isEmpty(WEB_IP)) {
            WEB_IP = IS_TEST ? "https://test.maka.im/" : "https://maka.im/";
        }

        //图片上传

    }


}
