package com.example.fastwork.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import com.example.fastwork.application.ApplicationHelper;
import com.example.fastwork.utils.log.Lg;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import java.util.Random;


public class SystemUtil {
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    private static final String KEY_UUID = "uuid";
    private static String deviceId = null;
    private static final File BUILD_PROP_FILE = new File(Environment.getRootDirectory(), "build.prop");
    private static Properties sBuildProperties;
    private static final Object sBuildPropertiesLock = new Object();

    public static String getDeviceId(Context context) {
        if (deviceId != null) {
            return deviceId;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return telephonyManager != null ? telephonyManager.getDeviceId() : null;
        }
        return "";
    }

    public static boolean isPhone(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断APP是否处于后台状态
     *
     * @param context
     * @return
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
                if (runningProcesses != null) {
                    for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                        if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            for (String activeProcess : processInfo.pkgList) {
                                if (activeProcess.equals(context.getPackageName())) {
                                    isInBackground = false;
                                }
                            }
                        }
                    }
                }
            } else {
                List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
                if (taskInfo != null && taskInfo.size() > 0) {
                    ComponentName componentInfo = taskInfo.get(0).topActivity;
                    if (componentInfo.getPackageName().equals(context.getPackageName())) {
                        isInBackground = false;
                    }
                }
            }
        }
        return isInBackground;
    }

    /**
     * 检查SDCard是否存在
     *
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SDCard的地址
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static int[] getPhoneScreenWH(Context context) {
        int wh[] = new int[2];
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        wh[0] = dm.widthPixels;
        wh[1] = dm.heightPixels;
        return wh;
    }



    public static int spTopx(float spValue, float fontScale) {
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int pxTosp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 延迟显示
     *
     * @param v
     */
    public static void showInputMethodManagerDelay(View v) {
        showInputMethodManager(v, 200, null);
    }

    /**
     * 马上显示
     *
     * @param v
     */
    public static void showInputMethodManagerNow(View v, onInputMethodListener l) {
        showInputMethodManager(v, 0, l);
    }

    public static void showInputMethodManager(final View v, long time, final onInputMethodListener l) {
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    getInputMethodManager(v.getContext()).showSoftInput(v, 0);
                    if (l != null) l.onInputMethodShow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, time);

    }

    //判断软键盘是都出现
    public static boolean isInputMethodShow(View v) {

//        return  context.getWindow().peekDecorView() != null;
        InputMethodManager imm = getInputMethodManager(v.getContext());
        return imm != null && imm.isActive(v);
//        return context.getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED;
    }

    /**
     * 关闭输入软键盘
     *
     * @param v
     */
    public static boolean closeInputMethodManager(View v) {
        if (v == null) return false;
        return getInputMethodManager(v.getContext()).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static InputMethodManager getInputMethodManager(Context context) {
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    /**
     * 获取Android的版本号
     *
     * @return
     */
    public static String getAndroidId() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     */
    public static String getPhone() {
        return Build.MODEL;
    }

    /**
     * 获取Android系统的api
     *
     * @return
     */
    public static int getAndroidApi() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取一个随机数
     *
     * @param startNum 从那个位置开始
     * @param endNum   从那个位置结束
     * @return
     */
    public static int getRandom(int startNum, int endNum) {
        return startNum + (int) (Math.random() * endNum);
    }

    public static int getRandom(int endNum) {
        return getRandom(0, endNum);
    }

    /**
     * 生成指定位数的随机数
     *
     * @param num 多少位数
     * @return
     */
    public static String getRandomNum(int num) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            builder.append(getRandom(10));
        }
        return builder.toString();
    }


    //获取项目的版本
    public static int getAppVersion() {
        Context context = ApplicationHelper.INSTANCE.getContext();
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Integer.MAX_VALUE;
    }


    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            //versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取指定位数由字母和数据组成的随机字符穿
     *
     * @param num
     * @return
     */
    public static String getAlphabetNum(int num) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < num; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }

    //启动硬件加速
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void startLayerTypeHardWare(View view) {
        if (view != null)
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }


    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值 ， 或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return resultData;
    }

    //跳转到主界面
    public static void startHome(Activity activity) {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);

        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        activity.startActivity(mHomeIntent);
    }


    //获取项目包名
    public static String getPackageName(Context context) {
        if (context == null) return "you";
        try {
            return context.getPackageName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "you";
    }

    public static void println(String text) {
        System.out.println(text);
    }

    public static void printlnInfo(String text) {
        Log.i("youjiannuo", text);
    }



    public static Drawable getDrawable(int drawableId, Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), drawableId, options);
        Drawable drawable = context.getResources().getDrawable(drawableId);
        if (drawable == null) return null;
        drawable.setBounds(0, 0, options.outWidth, options.outHeight);
        return drawable;
    }



    public static void openNotificationSetting(Context context) {
        Intent intent = new Intent();
        String packageName = context.getPackageName();
        if (Build.VERSION.SDK_INT >= 26) {
            // android 8.0引导
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", packageName);
        } else if (Build.VERSION.SDK_INT >= 21) {
            // android 5.0-7.0
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", packageName);
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else {
            // 其他
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", packageName, null));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static boolean isKeyboardShowing(Activity activity) {
        //获取当前屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return screenHeight - rect.bottom != 0;
    }

    public static void closeKeyboard(Activity activity) {
        if (isKeyboardShowing(activity)) {
            View view = activity.getWindow().peekDecorView();
            if (view != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private static int getAppHeight(Activity paramActivity) {
        return paramActivity.getWindow().getDecorView().getHeight();
    }

    public static int getScreenHeight(Activity paramActivity) {
        return paramActivity.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            Log.w("SystemUtils", "getStatusBarHeight", e);

        }
        return statusBarHeight;
    }

    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                return process.processName;
            }
        }
        return null;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                try {
                    fstream = new FileReader("/sys/class/net/eth0/address");
                } catch (FileNotFoundException e1) {
                }
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getDaoHangHeight(Context context) {
        if (!isNavigationBarShow(context)) return 0;
        int result = 0;
        int resourceId = 0;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0 && context.getResources().getBoolean(rid)) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            result = context.getResources().getDimensionPixelSize(resourceId);

        }
        Lg.d("SystemUtils", "getDaoHangHeight: height=" + result + ", resourceId=" + resourceId);
        return result;
    }

    public static boolean isNavigationBarShow(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static boolean isInstalled(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }



    public static boolean isActivityExists(Context context, String pkg, String cls) {
        Intent intent = new Intent();
        intent.setClassName(pkg, cls);
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent, 0);
        return !(resolveInfos == null || resolveInfos.size() == 0);
    }

    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = "";
            if (!TextUtils.isEmpty(info.versionName)) {
                if (info.versionName.contains("-")) {
                    version = info.versionName.substring(0, info.versionName.indexOf("-"));
                } else {
                    version = info.versionName;
                }
            }
            return version;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getDeviceVersion() {
        return Build.MODEL + " " + Build.VERSION.SDK_INT;
    }

    private static Properties getBuildProperties() {
        synchronized (sBuildPropertiesLock) {
            if (sBuildProperties == null) {
                sBuildProperties = new Properties();
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(BUILD_PROP_FILE);
                    sBuildProperties.load(fis);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return sBuildProperties;
    }

    public static boolean isMIUI() {
        return getBuildProperties().containsKey("ro.miui.ui.version.name");
    }

    public static void setMaxAspect(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null) {
                applicationInfo.metaData.putString("android.max_aspect", "2.5");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }



    public interface onInputMethodListener {
        void onInputMethodShow();
    }
}
