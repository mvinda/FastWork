package com.example.fastwork.utils.value;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;

public class ValueUtil {
    private static final boolean DEBUG=true;

    //    private static ThreadLocal<Long> sTime=new ThreadLocal<>();
    private static ThreadLocal<Stack<Long>> sTimes = new ThreadLocal<>();

    static {
        sTimes.set(new Stack<Long>());
    }

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static String formatDate(long date) {
        return dateFormat.format(date);
    }

    public static String formatTime(long date) {
        return timeFormat.format(date);
    }

    public static int compare(int lhs,int rhs){
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }
    public static int compare(long lhs,long rhs){
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }
    public static Date parseDate(String date) {
        if (date == null) return null;
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            Log.e("ValueUtil", "parseDate", e);
            return null;
        }
    }

    public static Integer toInteger(Object value) {
        Double v = toDouble(value);
        if (v != null) return v.intValue();
        return null;
    }

    public static Double toDouble(Object value) {
        if (value == null) return null;
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {

        }
        return null;
    }


    public static String getStr(Map<String, Object> map, String key, String def) {
        Object value = map.get(key);
        if (value == null) return def;
        return value.toString();
    }

    public static int getInt(Map<String, Object> map, String key, int def) {
        Object value = map.get(key);
        if (value == null) return def;
        Integer ret = toInteger(value);
        return ret == null ? def : ret;
    }

    public static double getFloat(Map<String, Object> map, String key, double def) {
        Object value = map.get(key);
        if (value == null) return def;
        Double ret = toDouble(value);
        return ret == null ? def : ret;
    }


    public static boolean getBool(Map<String, Object> map, String key, boolean def) {
        Object value = map.get(key);
        if (value == null) return def;
        return Boolean.parseBoolean(value.toString());
    }

    public static int dip2px(Context context, float dipValue) {

        float scale = context != null ? context.getResources().getDisplayMetrics().density : 1;
        return (int) (dipValue * scale + 0.5f);
    }






    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("\\-", "");
    }

    public static int parseInt(String s) {
        return parseInt(s, 0);
    }

    public static int parseInt(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return def;
    }

    public static float parseFloat(String name) {
        try {
            return Float.parseFloat(name);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
