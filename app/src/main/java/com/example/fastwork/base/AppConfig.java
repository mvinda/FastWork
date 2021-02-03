package com.example.fastwork.base;
;

public class AppConfig {

    public final static long MAX_CACHE_SIZE = 1024 * 1024 * 20;

    private static final String APP_FOLDER_NAME = "FAST_WORK";


    private static final String KEY_SAVE_COUNT = "save_count";
    private static final String KEY_DAY_TIME = "day_time";

    private static AppConfig instance = new AppConfig();

    private String mAppFolder;
    private String mCacheFolder;
    private Thread mMainThread;

    private String mImageCacheFolder;

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        return instance;
    }






}
