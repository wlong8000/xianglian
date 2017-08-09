
package com.wl.lianba.utils;

import android.util.Log;

public class Trace {

    public static final int LEVEL_SILENCE = -1;

    public static final int LEVEL_ERROR = 0;

    public static final int LEVEL_WARN = 1;

    public static final int LEVEL_INFO = 2;

    public static final int LEVEL_DEBUG = 3;

    public static final int LEVEL_VERBOSE = 5;

    private static int level = LEVEL_VERBOSE;

    public static void setLevel(int l) {
        level = l;
    }

    public static void d(String TAG, String msg) {
        if (level >= LEVEL_DEBUG)
            Log.d(TAG, msg == null ? "msg is Null" : msg);
    }

    public static void e(String TAG, Object msg) {
        if (level >= LEVEL_ERROR)
            Log.e(TAG, msg == null ? "msg is Null" : msg.toString());
    }

    public static void v(String TAG, Object msg) {
        if (level >= LEVEL_VERBOSE)
            Log.v(TAG, msg == null ? "msg is Null" : msg.toString());
    }

    public static void i(String TAG, Object msg) {
        if (level >= LEVEL_INFO)
            Log.i(TAG, msg == null ? "msg is Null" : msg.toString());
    }

    public static void w(String TAG, Object msg) {
        if (level >= LEVEL_WARN)
            Log.w(TAG, msg == null ? "msg is Null" : msg.toString());
    }

    public static void d(String TAG, String msg, Throwable tr) {
        if (level >= LEVEL_DEBUG)
            Log.d(TAG, msg == null ? "msg is Null" : (msg + '\n' + Log.getStackTraceString(tr)));
    }

    public static void e(String TAG, Object msg, Throwable tr) {
        if (level >= LEVEL_ERROR)
            Log.e(TAG,
                    msg == null ? "msg is Null" : (msg.toString() + '\n' + Log
                            .getStackTraceString(tr)));
    }

    public static void v(String TAG, Object msg, Throwable tr) {
        if (level >= LEVEL_VERBOSE)
            Log.v(TAG,
                    msg == null ? "msg is Null" : (msg.toString() + '\n' + Log
                            .getStackTraceString(tr)));
    }

    public static void i(String TAG, Object msg, Throwable tr) {
        if (level >= LEVEL_INFO)
            Log.i(TAG,
                    msg == null ? "msg is Null" : (msg.toString() + '\n' + Log
                            .getStackTraceString(tr)));
    }

    public static void w(String TAG, Object msg, Throwable tr) {
        if (level >= LEVEL_WARN)
            Log.w(TAG,
                    msg == null ? "msg is Null" : (msg.toString() + '\n' + Log
                            .getStackTraceString(tr)));
    }
}
