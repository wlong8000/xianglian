package com.xianglian.love.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wanglong on 17/3/3.
 */

public class AppSharePreferences {
    public static final String SHARE_KEY = "share_preference_key";

    //App第一次被启动 b
    public static final String FIRST_INTO = "is_first";

    public static void saveBoolValue(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_KEY, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }

    public static int getRegionPosition(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_KEY, Context.MODE_PRIVATE);
        return sp.getInt("region_position", -1);
    }
}
