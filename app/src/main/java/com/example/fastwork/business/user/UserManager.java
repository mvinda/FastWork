package com.example.fastwork.business.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.example.fastwork.application.ApplicationHelper;

public class UserManager {

    private static final String TAG = "UserManager";

    private static final String USER_MODEL_FILE = "user";
    private static final String USER_LOGOUT_FILE = "user_logout";

    private static final String KEY_UID = "uid";
    private static final String KEY_TOKEN = "token";

    private static final String KEY_EMAIL = "email";
    private static final String KEY_NICKNAME = "nickname";
    private static final String KEY_ACCOUNT_BALANCE = "acountBalance";
    private static final String KEY_THUMB = "thumb";
    public static final String KEY_COMPANY = "company";
    private static final String KEY_TRUE_NAME = "true_name";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_PROVINCE = "province";
    private static final String KEY_CITY = "city";
    private static final String KEY_REGISTER_TIME = "register_time";
    private static final String KEY_INDUSTRY_TXT = "industry_txt";

    private static final String KEY_SAVED_EMAIL = "saved_email";
    private static final String KEY_SAFE_MOBILE = "safe_mobile";
    private static final String KEY_CHECK_TEL = "check_tel";
    private static final String KEY_PROFILE = "key_profile";
    private static final String KEY_IS_FIRST = "isFirst";

    private static UserManager instance;
    private UserModel mUserModel;
    private static SharedPreferences sp_user = getSavedFile();

    private UserManager() {
        mUserModel = getSavedUserModel();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private static SharedPreferences getSavedFile() {
        return ApplicationHelper.INSTANCE.getContext().
                getSharedPreferences(USER_MODEL_FILE, Context.MODE_PRIVATE);
    }

    public static boolean isLogin() {
        if (sp_user == null) {
            sp_user = getSavedFile();
        }
        return !TextUtils.isEmpty(sp_user.getString(KEY_TOKEN, ""));
    }

    public void saveUserModel(UserModel model) {
        sp_user.edit()
                .putString(KEY_UID, model.getUid())
                .putString(KEY_EMAIL, model.getEmail())
                .putString(KEY_NICKNAME, model.getNickname())
                .putString(KEY_ACCOUNT_BALANCE, model.getAccountBalance())
                .putString(KEY_THUMB, model.getAvatar())
                .putString(KEY_TRUE_NAME, model.getTrueName())
                .putString(KEY_MOBILE, model.getMobile())
                .putString(KEY_PROVINCE, model.getProvince())
                .putString(KEY_CITY, model.getCity())
                .putString(KEY_REGISTER_TIME, model.getRegisterTime())
                .putInt(KEY_CHECK_TEL, model.getCheckTel())
                .putString(KEY_SAFE_MOBILE, model.getSafeMobile())
                .apply();
        mUserModel = getSavedUserModel();
    }

    private UserModel getSavedUserModel() {
        UserModel userModel = new UserModel();
        userModel.setUid(sp_user.getString(KEY_UID, ""));
        userModel.setToken(sp_user.getString(KEY_TOKEN, ""));
        userModel.setEmail(sp_user.getString(KEY_EMAIL, ""));
        userModel.setNickname(sp_user.getString(KEY_NICKNAME, ""));
        userModel.setAccountBalance(sp_user.getString(KEY_ACCOUNT_BALANCE, ""));
        userModel.setAvatar(sp_user.getString(KEY_THUMB, ""));
        userModel.setCompany(sp_user.getString(KEY_COMPANY, ""));
        userModel.setTrueName(sp_user.getString(KEY_TRUE_NAME, ""));
        userModel.setMobile(sp_user.getString(KEY_MOBILE, ""));
        userModel.setProvince(sp_user.getString(KEY_PROVINCE, ""));
        userModel.setCity(sp_user.getString(KEY_CITY, ""));
        userModel.setRegisterTime(sp_user.getString(KEY_REGISTER_TIME, ""));
        userModel.setIndustryName(sp_user.getString(KEY_INDUSTRY_TXT, ""));

        userModel.setCheckTel(sp_user.getInt(KEY_CHECK_TEL, 0));
        userModel.setSafeMobile(sp_user.getString(KEY_SAFE_MOBILE, ""));
        userModel.setFirst(sp_user.getBoolean(KEY_IS_FIRST, false));
        return userModel;
    }





    public UserModel getUserModel() {
        if (mUserModel == null)
            mUserModel = getSavedUserModel();
        return mUserModel;
    }

    public void saveUserId(String uid) {
        sp_user.edit().putString(KEY_UID, uid).apply();
        mUserModel.setUid(uid);
    }

    public String getUserId() {
        if (getInstance() == null || getInstance().getUserModel() == null) return "";
        return getInstance().getUserModel().getUid();
    }

    public void saveToken(String token) {
        sp_user.edit().putString(KEY_TOKEN, token).apply();
        mUserModel.setToken(token);
    }

    public String getToken() {
        if (mUserModel == null) return "";
        return mUserModel.getToken();
    }

    public void saveIsFirst(boolean isfirst) {
        sp_user.edit().putBoolean(KEY_IS_FIRST, isfirst).apply();
        mUserModel.setFirst(isfirst);
    }

    public void saveNickName(String nickname) {
        sp_user.edit().putString(KEY_NICKNAME, nickname).apply();
        mUserModel.setNickname(nickname);
    }

    public void saveAccountBalance(String balance) {
        sp_user.edit().putString(KEY_ACCOUNT_BALANCE, balance).apply();
        mUserModel.setAccountBalance(balance);
    }

    public void saveUserAvatar(String avatar) {
        sp_user.edit().putString(KEY_THUMB, avatar).apply();
        mUserModel.setAvatar(avatar);
    }


    public String getCompany() {
        return sp_user.getString(KEY_COMPANY, "");
    }

    public void saveTrueName(String trueName) {
        sp_user.edit().putString(KEY_TRUE_NAME, trueName).apply();
        mUserModel.setTrueName(trueName);
    }

    public void saveMobile(String mobile) {
        sp_user.edit().putString(KEY_MOBILE, mobile).apply();
        mUserModel.setMobile(mobile);
    }

    public void saveCity(String province, String city) {
        sp_user.edit().putString(KEY_PROVINCE, province)
                .putString(KEY_CITY, city)
                .apply();
        mUserModel.setProvince(province);
        mUserModel.setCity(city);
    }

    public void saveCheckTel(int i) {
        sp_user.edit().putInt(KEY_CHECK_TEL, i).apply();
        mUserModel.setCheckTel(i);
    }


    public String getIndustryName() {
        return sp_user.getString(KEY_INDUSTRY_TXT, "");
    }

    public void refresh() {
        mUserModel = getSavedUserModel();
    }
}

