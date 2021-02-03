package com.example.fastwork.http.base_model;

/**
 * Created by youjiannuo on 15/7/1.
 */
public class BaseMessageModel extends BaseModel {

    private String error = "";
    private String msg = "";
    private String code = "";
    private String data = "";

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
