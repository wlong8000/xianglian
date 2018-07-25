package com.tencent.qcloud.presentation.presenter;

import android.os.Handler;
import com.tencent.qcloud.presentation.viewfeatures.SplashView;


/**
 * 闪屏界面逻辑
 */
public class SplashPresenter {
    private static final String TAG = SplashPresenter.class.getSimpleName();

    private SplashView view;

    private int mDisplayTime;

    private Handler mHandler = new Handler();

    public SplashPresenter(SplashView view, int display) {
        this.view = view;
        this.mDisplayTime = display;
    }

    /**
     * 加载页面逻辑
     */
    public void start() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!view.isUserLogin()) {
                    view.navToHome();
                } else {
                    view.navToLogin();
                }
            }
        }, mDisplayTime);
    }

    public void stop() {
        mHandler.removeCallbacksAndMessages(null);
    }


}
