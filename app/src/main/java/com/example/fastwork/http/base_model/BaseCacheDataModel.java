package com.example.fastwork.http.base_model;

/**
 * Created by youjiannuo on 15/8/11.
 */
public class BaseCacheDataModel {

    private long time = 0;

    private String data = "";

    private String name = "";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
