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
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMFriendshipSettings;
import com.tencent.TIMLogLevel;
import com.tencent.TIMManager;
import com.tencent.TIMUserProfile;
import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.presentation.presenter.SplashPresenter;
import com.tencent.qcloud.presentation.viewfeatures.SplashView;
import com.tencent.qcloud.tlslibrary.service.TLSService;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.wl.appchat.TimHelper;
import com.wl.appchat.model.UserInfo;
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
public class SplashActivity extends BaseActivity implements SplashView, TIMCallBack {
    private final int REQUEST_PHONE_PERMISSIONS = 0;

//    private static final int LOGIN_RESULT_CODE = 100;

    private SplashPresenter mPresenter;

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
                init();
            } else {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_PHONE_PERMISSIONS);
            }
        } else {
            init();
        }
    }

    private void setupView() {
        mSplashImg = (SimpleDraweeView) findViewById(R.id.base_splash_image);
        mSkipView = findViewById(R.id.ad_skip);
        if (mConfigEntity != null && !TextUtils.isEmpty(mConfigEntity.getDefault_splash_img())) {
            mSplashImg.setImageURI(AppUtils.parse(mConfigEntity.getDefault_splash_img()));
        }
    }

    private void init() {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        int loglvl = pref.getInt("loglvl", TIMLogLevel.DEBUG.ordinal());
        //初始化IMSDK
        InitBusiness.start(getApplicationContext(), loglvl);
        //初始化TLS
        TlsBusiness.init(getApplicationContext());
        //设置刷新监听
        RefreshEvent.getInstance();
        String id = TLSService.getInstance().getLastUserIdentifier();
        UserInfo.getInstance().setId(id);
        UserInfo.getInstance().setUserSig(TLSService.getInstance().getUserSig(id));

        long flags = 0;
        flags |= TIMFriendshipManager.TIM_PROFILE_FLAG_ALLOW_TYPE
                | TIMFriendshipManager.TIM_PROFILE_FLAG_FACE_URL
                | TIMFriendshipManager.TIM_PROFILE_FLAG_NICK;
//        TIMManager.getInstance().initFriendshipSettings(flags, null);
        mPresenter = new SplashPresenter(this, mConfigEntity != null
                ? Integer.parseInt(mConfigEntity.getSplash_time()) * 1000 : 2000);
        mPresenter.start();
    }


    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if (mPresenter != null) mPresenter.stop();
    }

    @Override
    public void navToHome() {
        //登录之前要初始化群和好友关系链缓存
        FriendshipEvent.getInstance().init();
        GroupEvent.getInstance().init();
        UserEntity entity = Hawk.get(Keys.USER_TIM_SIGN);
        if (entity != null) {
            LoginBusiness.loginIm(entity.getUsername(), entity.getUser_sign(), this);
        } else {
            gotoMainActivity();
        }
    }

    private void gotoMainActivity() {
        startActivity(MainActivity.getIntent(this));
        finish();
    }

    @Override
    public void navToLogin() {
        Intent intent = SelectSexActivity.getIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean isUserLogin() {
        return !AppUtils.isLogin(this) && TextUtils.isEmpty((CharSequence) Hawk.get(Keys.SEX));
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

    @Override
    public void onError(int i, String s) {
        Trace.e(TAG, "login error : code " + i + " " + s);
        switch (i) {
            case 6208:
                //离线状态下被其他终端踢下线
//                NotifyDialog dialog = new NotifyDialog();
//                dialog.show(getString(R.string.kick_logout), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        navToHome();
//                    }
//                });
                break;
            case 6200:
                Toast.makeText(this, getString(R.string.login_error_timeout), Toast.LENGTH_SHORT).show();
                navToLogin();
                break;
            default:
                Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
                navToLogin();
                break;
        }
    }

    @Override
    public void onSuccess() {
        TimHelper.getInstance().initMessage();

//        String refreshedToken = null;
//        FirebaseInstanceId firebaseInstanceId = FirebaseInstanceId.getInstance();
//        if (firebaseInstanceId != null) refreshedToken = firebaseInstanceId.getToken();
//        Log.d(TAG, "refreshed token: " + refreshedToken);
//
//        if (!TextUtils.isEmpty(refreshedToken)) {
//            TIMOfflinePushToken param = new TIMOfflinePushToken();
//            param.setToken(refreshedToken);
//            param.setBussid(169);
//            TIMManager.getInstance().setOfflinePushToken(param);
//        }

        Log.d(TAG, "imsdk env " + TIMManager.getInstance().getEnv());
        gotoMainActivity();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                } else {
                    Toast.makeText(this, getString(R.string.need_permission), Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
