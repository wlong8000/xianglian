package com.wl.lianba.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wanglong on 17/3/3.
 */

public class AppSharePreferences {
    public static final String SHARE_KEY = "share_preference_key";
    //用户名 s
    public static final String USER_NAME = "user_name";
    //密码 s
    public static final String PASSWORD = "password";
    //token s
    public static final String TOKEN = "token";
    //App第一次被启动 b
    public static final String FIRST_INTO = "is_first";
    //性别 i
    public static final String SEX = "sex";
    //是否填写个人信息 b
    public static final String USER_INFO = "user_info";

    public static void saveStrValue(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_KEY, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static String getStrValue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_KEY, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }


    public static void saveIntValue(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_KEY, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();
    }

    public static int getIntValue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_KEY, Context.MODE_PRIVATE);
        return sp.getInt(key, -1);
    }

    public static void saveBoolValue(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_KEY, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolValue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_KEY, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static boolean saveRegionPosition(Context context, int position) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_KEY, Context.MODE_PRIVATE);
        return sp.edit().putInt("region_position", position).commit();
    }

    public static int getRegionPosition(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_KEY, Context.MODE_PRIVATE);
        return sp.getInt("region_position", -1);
    }
}
