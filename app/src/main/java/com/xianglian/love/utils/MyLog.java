package com.xianglian.love.utils;

import android.util.Log;

/**
  VERBOSE 类型调试信息，verbose啰嗦的意思 ；
  DEBUG 类型调试信息, debug调试信息 ；
  INFO  类型调试信息, 一般提示性的消息information ；
  WARN  类型调试信息，warning警告类型信息 ；
  ERROR 类型调试信息，错误信息 。
 */
public class MyLog {

	public static boolean DEBUG = true;
	public static String TAG = "info";

	public static void i(String tag, Object msg) {
		if (DEBUG)
			Log.i(tag, msg.toString());
	}
	
	public static void ii(Object msg) {
		if (DEBUG)
			Log.i(TAG, msg.toString());
	}

	public static void w(String tag, Object msg) {
		if (DEBUG)
			Log.w(tag, msg.toString());
	}

	public static void ww(Object msg) {
		if (DEBUG)
			Log.w(TAG, msg.toString());
	}
	
	public static void e(String tag, Object msg) {
		if (DEBUG)
			Log.e(tag, msg.toString());
	}
	
	public static void ee(Object msg) {
		if (DEBUG)
			Log.e(TAG, msg.toString());
	}
	
	public static void d(String tag, Object msg) {
		if (DEBUG)
			Log.e(tag, msg.toString());
	}
	
	public static void dd(Object msg) {
		if (DEBUG)
			Log.d(TAG, msg.toString());
	}
	
	public static void v(String tag, Object msg) {
		if (DEBUG)
			Log.v(tag, msg.toString());
	}
	
	public static void vv(Object msg) {
		if (DEBUG)
			Log.v(TAG, msg.toString());
	}
	

}
