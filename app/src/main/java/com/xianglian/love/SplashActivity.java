package com.xianglian.love;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.hawk.Hawk;
import com.wl.appcore.Keys;
import com.wl.appcore.entity.UserEntity;
import com.xianglian.love.config.Config;
import com.xianglian.love.model.ConfigEntity;
import com.xianglian.love.user.SelectSexActivity;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.Trace;

import java.util.ArrayList;
import java.util.List;

/**
 * 启动页面
 */
public class SplashActivity extends BaseActivity {
    private final int REQUEST_PHONE_PERMISSIONS = 0;

    public SimpleDraweeView mSplashImg;

    public View mSkipView;

    private ConfigEntity mConfigEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clearNotification();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mConfigEntity = Hawk.get(Keys.CONFIG_INFO);
        if (mConfigEntity != null && !TextUtils.isEmpty(mConfigEntity.getServer_path()))
            Config.PATH = mConfigEntity.getServer_path();
        setupView();

        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)) {
                permissionsList.add(Manifest.permission.READ_PHONE_STATE);
            }
            if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (permissionsList.size() == 0) {

            } else {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_PHONE_PERMISSIONS);
            }
        } else {

        }
    }

    private void setupView() {
        mSplashImg = (SimpleDraweeView) findViewById(R.id.base_splash_image);
        mSkipView = findViewById(R.id.ad_skip);
        if (mConfigEntity != null && !TextUtils.isEmpty(mConfigEntity.getDefault_splash_img())) {
            mSplashImg.setImageURI(AppUtils.parse(mConfigEntity.getDefault_splash_img()));
        }
    }


    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void gotoMainActivity() {
        startActivity(MainActivity.getIntent(this));
        finish();
    }

    /**
     * 清楚所有通知栏通知
     */
    private void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null)
            notificationManager.cancelAll();

    }
}
