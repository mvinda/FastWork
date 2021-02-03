package com.example.fastwork.http.base_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 15/7/19.
 */
public class BaseListDataModel<T> {
    @SerializedName("success")
    private boolean mSuccess;
    @SerializedName("code")
    private int mCode;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("data")
    private Result<T> mData;

    public boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        this.mSuccess = success;
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

    public Result<T> getData() {
        return mData;
    }

    public void setData(Result<T> mData) {
        this.mData = mData;
    }

    public static class Result<T> {
        @SerializedName(value = "pageNumber", alternate = {"current_page", "page_number"})
        private int mPageNumber;
        @SerializedName(value = "perPage", alternate = {"per_page"})
        private int mPerPage;
        @SerializedName(value = "dataList", alternate = {"data", "templates"})
        private List<T> mData;
        private String red;
        private String red_app;
        private String red_pc;
        List<String> red_pid;
        private int total;

        public String getRed_app() {
            return red_app;
        }

        public void setRed_app(String red_app) {
            this.red_app = red_app;
        }

        public String getRed_pc() {
            return red_pc;
        }

        public void setRed_pc(String red_pc) {
            this.red_pc = red_pc;
        }

        public List<String> getRed_pid() {
            return red_pid;
        }

        public void setRed_pid(List<String> red_pid) {
            this.red_pid = red_pid;
        }

        public String getRed() {
            return red;
        }

        public void setRed(String red) {
            this.red = red;
        }

        public int getPageNumber() {
            return mPageNumber;
        }

        public void setPageNumber(int mPageNumber) {
            this.mPageNumber = mPageNumber;
        }

        public int getPerPage() {
            return mPerPage;
        }

        public void setPerPage(int mPerPage) {
            this.mPerPage = mPerPage;
        }

        public List<T> getData() {
            return mData;
        }

        public void setData(List<T> mData) {
            this.mData = mData;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal() {
            return total;
        }
    }

}
