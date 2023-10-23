package com.jjshome.mobile.datastatistics.utils;

import android.util.Log;

import com.jjshome.mobile.datastatistics.BuildConfig;

/**
 * 大数据打印日志
 * WQ on 2017/3/6
 * wq@jjshome.com
 */

public class DataLog {
    private final static String TAG = "UploadData";

    public static void setIsDebug(boolean isDebug) {
        DataLog.isDebug = isDebug;
    }

    //默认不显示日志
    public static boolean isDebug = BuildConfig.DEBUG_LOG;

    public static void v(Object o) {
        if (isDebug) Log.v(TAG, o.toString());
    }

    public static void v(String tag, Object msg) {
        if (isDebug) Log.v(tag, msg.toString());
    }

    public static void v(String tag, String format, Object... args) {
        if (isDebug) Log.v(tag, String.format(format, args));
    }

    public static void d(Object o) {
        if (isDebug) Log.d(TAG, o.toString());
    }

    public static void d(String tag, Object msg) {
        if (isDebug) Log.d(tag, msg.toString());
    }

    public static void d(String tag, String format, Object... args) {
        if (isDebug) Log.d(tag, String.format(format, args));
    }

    public static void i(Object o) {
        if (isDebug) Log.i(TAG, o.toString());
    }

    public static void i(String tag, Object msg) {
        if (isDebug) Log.i(tag, msg.toString());
    }

    public static void i(String tag, String format, Object... args) {
        if (isDebug) Log.i(tag, String.format(format, args));
    }

    public static void w(Object o) {
        if (isDebug) Log.w(TAG, o.toString());
    }

    public static void w(String tag, Object msg) {
        if (isDebug) Log.w(tag, msg.toString());
    }

    public static void w(String tag, String format, Object... msg) {
        if (isDebug) Log.w(tag, String.format(format, msg));
    }

    public static void e(Object o) {
        if (isDebug) Log.e(TAG, o.toString());
    }

    public static void e(String tag, Object msg) {
        if (isDebug) Log.e(tag, msg.toString());
    }

    public static void e(String tag, String format, Object... msg) {
        if (isDebug) Log.w(tag, String.format(format, msg));
    }

    public static void logExc(Exception e) {
        if (isDebug) {
            Log.e("exception", e.toString());
            e.printStackTrace();
        }
    }

    public static void logExc(String tag, Exception e) {
        if (isDebug) {
            Log.e(tag, e.toString());
            e.printStackTrace();
        }
    }
}
