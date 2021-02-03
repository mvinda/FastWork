package com.example.fastwork.http.gson;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    public static boolean isNotEmpty(String str) {
        return !(str == null || str.length() == 0);
    }


    public static String createUUID() {
        return UUID.randomUUID().toString();
    }


    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "1[0-9]{10}";//"((^13[0-9]{9}|15[0-9]{9}|14[0-9]{9}|18[0-9]{9}|17[0-9]{9}|166[0-9]{8}|198[0-9]{8}|199[0-9]{8}$))";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public static String md5base64encode(String input) {
        try {
            if (input == null)
                return null;

            if (input.length() == 0)
                return null;

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes("UTF-8"));
            byte[] enc = md.digest();
            String base64str = Base64.encodeToString(enc, Base64.NO_WRAP);
            return base64str;

        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (UnsupportedEncodingException e) {
            return null;
        }

    }

    public static String base64encode(String input) {
        if (input == null)
            return null;

        if (input.length() == 0)
            return null;

        String base64str = Base64.encodeToString(input.getBytes(), Base64.NO_WRAP);
        return base64str;
    }


    public static String exChange(String str) {
        StringBuffer sb = new StringBuffer();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.append(Character.toLowerCase(c));
                } else if (Character.isLowerCase(c)) {
                    sb.append(Character.toUpperCase(c));
                }
            }
        }

        return sb.toString();
    }

    public static String getUrlName(String url) {
        int a = url.lastIndexOf("\\");
        int b = url.lastIndexOf("/");
        int index = a > b ? a : b;

        return url.substring(index + 1, url.length());
    }


    public static boolean isURL(String url) {
        return !(url == null || url.length() == 0) && (url.trim().toLowerCase().startsWith("http://") || url.trim().toLowerCase().startsWith("https://"));
    }

    //获取一个没有带参数的链接
    public static String getNoParamsUrl() {
        return "";
    }

    public static String getMakaTime(String time) {
        int date = new Date().getDate();
        int dates = date;
        try {
            dates = Integer.parseInt(getTimeOnlyDate(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int month = new Date().getMonth() + 1;
        int months = month;

        try {
            months = Integer.parseInt(getTimeOnlyMonth(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int m = month - months;
        int day = date - dates;

        if (m == 0 && day == 0) {
            return getTimeOnlyHoursAndMinute(time);
        }
//        else if (m == 0 && day == 1) {
//            return "昨天";
//        }
        return getTimeOnlyMonthAndDay(time);
    }

    public static String getTimeOnlyYearAndMonthAndDate() {
        Date date = new Date();
        return date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
    }

    //获取时和分
    public static String getTimeOnlyHoursAndMinute(String time) {
        int a = time.indexOf(":");
        return time.substring(a - 2, a + 3);
    }

    //获取年和月
    public static String getTimeOnlyYearAndMonth(String time) {
        int a = time.indexOf("-");
        return time.substring(a - 4, a + 3);

    }

    //获取的是月和日
    public static String getTimeOnlyMonthAndDay(String time) {
        int a = time.indexOf("-");
        return time.substring(a + 1, a + 7);
    }

    public static String getTimeOnlyDate(String time) {
        int a = time.indexOf("-");
        a = time.indexOf("-", a + 1);
        return time.substring(a + 1, a + 3);
    }

    public static String getTimeOnlyMonth(String time) {
        int a = time.indexOf("-");
        return time.substring(a + 1, a + 3);

    }

    public static int getRounded(float f) {
        String s = f + "";
        int index = s.indexOf('.');
        if (index == -1) return (int) f;
        char c = s.charAt(index + 1);
        if (c >= '5') return Integer.parseInt(s.substring(0, index)) + 1;
        return Integer.parseInt(s.substring(0, index));
    }

}
