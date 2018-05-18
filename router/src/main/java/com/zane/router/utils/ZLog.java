package com.zane.router.utils;

import android.util.Log;

/**
 * Created by Zane on 2017/4/16.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public final class ZLog {
    private static final String TAG = "easyrouter";
    private static boolean DEBUG = true;

    private ZLog(){}

    public static void setDebug(boolean DEBUG) {
        ZLog.DEBUG = DEBUG;
    }

    public static void i(String message) {
        if (DEBUG) {
            Log.i(TAG, message);
        }
    }

    public static void e(String message) {
        Log.e(TAG, message);
    }
}

