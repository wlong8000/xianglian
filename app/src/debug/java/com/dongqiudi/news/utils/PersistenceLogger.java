package com.dongqiudi.news.utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/7/23.
 */

public class PersistenceLogger implements HttpLoggingInterceptor.Logger {

    private String content = "";

    @Override
    public void log(String message) {
        Log.e("http", message);
        content += message + "<br/>";
    }

    public String getContent(){
        return content;
    }
}
