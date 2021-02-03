package com.example.fastwork.business.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 15/7/18.
 */
public class UserModel {

    @SerializedName("uid")
    private String mUid;
    @SerializedName("token")
    private String mToken;
    @SerializedName("isFirst")
    private boolean mIsFirst;   //判断是否第一次进行三方登录

    @SerializedName("email")
    private String mEmail;
    @SerializedName("nickname")
    private String mNickname;
    @SerializedName(value = "acountBalance", alternate = {"accountBalance"})
    private String mAccountBalance;
    @SerializedName("thumb")
    private String mAvatar;
    @SerializedName("company")
    private String mCompany;
    @SerializedName("truename")
    private String mTrueName;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("province")
    private String mProvince;
    @SerializedName("city")
    private String mCity;
    @SerializedName("register_time")
    private String mRegisterTime;
    @SerializedName("industry")  //行业id
    private String mIndustry;
    @SerializedName("industry_txt")
    private String mIndustryName;

    @SerializedName("check_tel")
    private int mCheckTel;
    @SerializedName("safe_mobile")
    private String mSafeMobile;


    public String getAccountBalance() {
        return mAccountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.mAccountBalance = accountBalance;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String mUid) {
        this.mUid = mUid;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    public String getNickname() {
        return mNickname;
    }

    public void setNickname(String mNickname) {
        this.mNickname = mNickname;
    }

    public int getCheckTel() {
        return mCheckTel;
    }

    public void setCheckTel(int mCheckTel) {
        this.mCheckTel = mCheckTel;
    }

    public String getSafeMobile() {
        return mSafeMobile;
    }

    public void setSafeMobile(String mSafeMobile) {
        this.mSafeMobile = mSafeMobile;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mMobile) {
        this.mMobile = mMobile;
    }

    public boolean isFirst() {
        return mIsFirst;
    }

    public void setFirst(boolean first) {
        mIsFirst = first;
    }

    public String getRegisterTime() {
        return mRegisterTime;
    }

    public void setRegisterTime(String registerTime) {
        mRegisterTime = registerTime;
    }



    public void setCompany(String company) {
        mCompany = company;
    }

    public String getTrueName() {
        return mTrueName;
    }

    public void setTrueName(String trueName) {
        mTrueName = trueName;
    }

    public String getProvince() {
        return mProvince;
    }

    public void setProvince(String province) {
        mProvince = province;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getIndustry() {
        return mIndustry;
    }

    public void setIndustry(String industry) {
        mIndustry = industry;
    }


    public void setIndustryName(String industryName) {
        mIndustryName = industryName;
    }
}
