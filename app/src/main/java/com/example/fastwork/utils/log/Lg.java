package com.example.fastwork.utils.log;

import android.util.Log;
import de.mindpipe.android.logging.log4j.LogConfigurator;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class Lg {
    private static boolean inited;
    private static String sLogDir;


    public static void init(String logDir, boolean debug) {
        sLogDir = logDir;
        createDir(sLogDir);
        LogConfigurator config = new LogConfigurator();
//        if (debug) {
//            config.setRootLevel(Level.DEBUG);
//        } else {
//            config.setRootLevel(Level.INFO);
//        }
        config.setFileName(logDir + "/app.log");
        config.setMaxFileSize(1024 * 1024);
        config.setMaxBackupSize(10);
        config.setImmediateFlush(true);
        config.setFilePattern("%d [%p]\t%m%n");
        config.setUseLogCatAppender(false);
        try {
            config.configure();
            inited = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        i("Lg", "Logger initialized--------------------------------------");
    }

    private static boolean createDir(String filePath) {
        File fileSaveDir = new File(filePath);
        if (!fileSaveDir.exists())
            return fileSaveDir.mkdirs();
        return true;
    }
    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable e) {
        Log.d(tag, msg, e);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
        if (!inited) {
            return;
        }
        final Logger logger = Logger.getRootLogger();
        if (logger != null) {
            logger.info(tag + "/" + msg);
        }
    }

    public static void i(String tag, String msg, Throwable e) {
        Log.i(tag, msg, e);
        if (!inited) {
            return;
        }
        final Logger logger = Logger.getRootLogger();
        if (logger != null) {
            logger.info(tag + "/" + msg, e);
        }
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
        if (!inited) {
            return;
        }
        final Logger logger = Logger.getRootLogger();
        if (logger != null) {
            logger.warn(tag + "/" + msg);
        }
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
        if (!inited) {
            return;
        }
        final Logger logger = Logger.getRootLogger();
        if (logger != null) {
            logger.error(tag + "/" + msg);
        }
    }

    public static void e(String tag, String msg, Throwable ex) {
        Log.e(tag, msg, ex);
        if (!inited) {
            return;
        }
        final Logger logger = Logger.getRootLogger();
        if (logger != null) {
            logger.error(tag + "/" + msg, ex);
        }
    }

    public static void f(String tag, String msg, Throwable throwable) {
        Log.e(tag, msg, throwable);
        if (!inited) {
            return;
        }
        final Logger logger = Logger.getRootLogger();
        if (logger != null) {
            logger.fatal(tag + "/" + msg, throwable);
        }
    }

    public static void v(String tag, String msg) {
        Log.v(tag, msg);

    }

    public static void w(String tag, String msg, Throwable e) {
        Log.w(tag, msg, e);
        if (!inited) {
            return;
        }
        final Logger logger = Logger.getRootLogger();
        if (logger != null) {
            logger.warn(tag + "/" + msg, e);
        }
    }

    public static void writeContent(String content, String filename) {
        if (sLogDir == null) {
            return;
        }
        try {
            IOUtils.writeToFile(content, new File(sLogDir, filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File zipLog() {
        if (sLogDir == null) {
            return null;
        }
        File zipFile = new File(sLogDir + ".zip");
        try {
            XZip.zipFolder(new File(sLogDir), zipFile);
            return zipFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
