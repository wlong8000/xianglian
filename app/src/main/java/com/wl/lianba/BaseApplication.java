package com.wl.lianba;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;


/**
 * Created by wanglong on 17/2/25.
 */

public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
