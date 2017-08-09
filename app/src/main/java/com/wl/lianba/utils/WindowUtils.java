package com.wl.lianba.utils;

import android.os.Build;
import android.text.TextUtils;


public class WindowUtils {
	/**
	 * 判断手机ROM是否是小米
	 *
	 * @return
	 */
	public static boolean isXiaomiRom() {
		String name = Build.BRAND;
		return !TextUtils.isEmpty(name) && name.toLowerCase().equals("xiaomi");
	}

}
