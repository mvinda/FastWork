package com.example.fastwork.application;

import android.content.Context;
import com.example.fastwork.utils.sp.SharePreferenceUtil;
import com.google.gson.Gson;

/**
 * Created by WHD on 2019/4/9.
 * <p>
 * Description:工具类 拿到各种工具
 */
public enum ApplicationHelper {
    INSTANCE;
    private IApplication mIApplication;

    public void init(IApplication application) {
        mIApplication = application;
    }

    public Context getContext() {
        if (mIApplication == null) {
            return null;
        }
        return mIApplication.getContext();
    }

    public SharePreferenceUtil getPreference() {
        if (mIApplication == null) {
            return null;
        }
        return mIApplication.getPreference();
    }

    public Gson getGson() {
        if (mIApplication == null) {
            return null;
        }
        return mIApplication.getGson();
    }

}
