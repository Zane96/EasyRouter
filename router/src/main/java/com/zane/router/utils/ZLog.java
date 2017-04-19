package com.zane.router.utils;

import android.util.Log;

/**
 * Created by Zane on 2017/4/16.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public final class ZLog {

    private static boolean DEBUG = true;

    private ZLog(){}

    public static void setDebug(boolean DEBUG) {
        ZLog.DEBUG = DEBUG;
    }

    public static void i(String key, String message) {
        if (DEBUG) {
            Log.i(key, message);
        }
    }

    public static void e(String key, String message) {
        Log.e(key, message);
    }
}

