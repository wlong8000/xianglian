package com.wl.appcore.utils;

import android.content.Context;
import android.text.TextUtils;

import com.orhanobut.hawk.Hawk;
import com.wl.appcore.Keys;

/**
 * Created by wanglong on 2018/8/2.
 */

public class AppUtils2 {

    /**
     * 判断用户是否登录
     */
    public static boolean isLogin(Context context) {
        return !TextUtils.isEmpty(getToken());
    }

    public static String getToken() {
        return Hawk.get(Keys.TOKEN);
    }
}
