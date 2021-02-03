package com.example.fastwork.utils.secret;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;


public class MD5Util {
    private static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public MD5Util() {
    }

    public static String hexdigest(String string) {
        String s = null;

        try {
            s = hexdigest(string.getBytes());
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return s;
    }

    public static String hexdigest(byte[] bytes) {
        String s = null;

        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            e.update(bytes);
            byte[] tmp = e.digest();
            char[] str = getMd5Chars(tmp);

            s = new String(str);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return s;
    }

    private static char[] getMd5Chars(byte[] tmp) {
        char[] str = new char[32];
        int k = 0;

        for (int i = 0; i < 16; ++i) {
            byte byte0 = tmp[i];
            str[k++] = hexDigits[byte0 >>> 4 & 15];
            str[k++] = hexDigits[byte0 & 15];
        }
        return str;
    }

    public static String file(String filename) {
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            return new String(getMd5Chars(md.digest()));
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

}
