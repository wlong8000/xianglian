package com.dongqiudi.news.utils;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

/**
 * Created by hujinghui on 17/3/1.
 */

public class StethoHelper {
    public static void addNetworkInterceptor(OkHttpClient.Builder clientBuilder) {
        clientBuilder.addNetworkInterceptor(new StethoInterceptor());
        clientBuilder.addNetworkInterceptor(new HttpLoggingInterceptor());
    }

    public static void init(Context context) {
        Stetho.initialize(Stetho.newInitializerBuilder(context)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context)).build());
    }
}
