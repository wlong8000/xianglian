package com.xianglian.love;

import android.Manifest;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
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
import com.huawei.android.pushagent.PushManager;
import com.orhanobut.hawk.Hawk;
import com.tencent.TIMCallBack;
import com.tencent.TIMLogLevel;
import com.tencent.TIMManager;
import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.presentation.presenter.SplashPresenter;
import com.tencent.qcloud.presentation.viewfeatures.SplashView;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.wl.appchat.TimHelper;
import com.wl.appchat.utils.PushUtil;
import com.xianglian.love.config.Keys;
import com.wl.appcore.entity.UserEntity;
import com.xianglian.love.model.ConfigEntity;
import com.xianglian.love.user.LoginActivity;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.Trace;
import com.xiaomi.mipush.sdk.MiPushClient;

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
        mSplashImg = findViewById(R.id.base_splash_image);
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
//        String id = TLSService.getInstance().getLastUserIdentifier();
//        UserInfo.getInstance().setId(id);
//        UserInfo.getInstance().setUserSig(TLSService.getInstance().getUserSig(id));
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
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void navToLogin() {
        Intent intent = LoginActivity.getIntent(this);
        startActivity(intent);
    }

    @Override
    public boolean isUserLogin() {
//        return !TextUtils.isEmpty(AppUtils.getToken(this));
        return true;
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
        //初始化程序后台后消息推送
//        PushUtil.getInstance();
//        //初始化消息监听
//        MessageEvent.getInstance();
//        String deviceMan = android.os.Build.MANUFACTURER;
//        //注册小米和华为推送
//        if (deviceMan.equals("Xiaomi") && shouldMiInit()) {
//            MiPushClient.registerPush(getApplicationContext(), "2882303761517480335", "5411748055335");
//        } else if (deviceMan.equals("HUAWEI")) {
//            PushManager.requestToken(this);
//        }
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

    /**
     * 判断小米推送是否已经初始化
     */
//    private boolean shouldMiInit() {
//        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
//        if (am == null) return false;
//        List<ActivityManager.RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
//        String mainProcessName = getPackageName();
//        int myPid = android.os.Process.myPid();
//        for (ActivityManager.RunningAppProcessInfo info : processInfoList) {
//            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
//                return true;
//            }
//        }
//        return false;
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(TAG, "onActivityResult code:" + requestCode);
//        if (LOGIN_RESULT_CODE == requestCode) {
//            Log.d(TAG, "login error no " + TLSService.getInstance().getLastErrno());
//            if (0 == TLSService.getInstance().getLastErrno()) {
//                String id = TLSService.getInstance().getLastUserIdentifier();
//                UserInfo.getInstance().setId(id);
//                UserInfo.getInstance().setUserSig(TLSService.getInstance().getUserSig(id));
//                navToHome();
//            } else if (resultCode == RESULT_CANCELED) {
//                finish();
//            }
//        }
//    }


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
