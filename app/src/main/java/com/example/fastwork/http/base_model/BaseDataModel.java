package com.example.fastwork.http.base_model;

import com.google.gson.annotations.SerializedName;


public class BaseDataModel<T> {
    @SerializedName("success")
    private boolean mSuccess;
    @SerializedName("errorCode")
    private int mCode;
    @SerializedName("errorMsg")
    private String mMessage;
    @SerializedName("data")
    private T mData;

    public BaseDataModel() {
    }

    public BaseDataModel(T data) {
        mData = data;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        this.mData = data;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int mCode) {
        this.mCode = mCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        mSuccess = success;
    }
}
