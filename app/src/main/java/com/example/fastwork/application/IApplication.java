package com.example.fastwork.application;
import android.content.Context;
import com.example.fastwork.utils.sp.SharePreferenceUtil;
import com.google.gson.Gson;

public interface IApplication {
    Context getContext();

    SharePreferenceUtil getPreference();

    Gson getGson();

    void startMainService();
}
