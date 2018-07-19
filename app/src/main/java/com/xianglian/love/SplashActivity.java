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
import com.google.firebase.iid.FirebaseInstanceId;
import com.huawei.android.pushagent.PushManager;
import com.tencent.TIMCallBack;
import com.tencent.TIMLogLevel;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushToken;
import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.presentation.presenter.SplashPresenter;
import com.tencent.qcloud.presentation.viewfeatures.SplashView;
import com.tencent.qcloud.tlslibrary.service.TLSService;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.wl.appchat.model.UserInfo;
import com.wl.appchat.utils.PushUtil;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.Trace;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 启动页面
 */
public class SplashActivity extends BaseActivity implements SplashView, TIMCallBack {
    private final int REQUEST_PHONE_PERMISSIONS = 0;

    private int LOGIN_RESULT_CODE = 100;

    private SplashPresenter mPresenter;

    @InjectView(R.id.base_splash_image)
    public SimpleDraweeView mSplashImg;

    @InjectView(R.id.ad_skip)
    public View mSkipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clearNotification();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
//        AppService.startSaveUser(this);
        setupView();
        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.READ_PHONE_STATE);
            if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
        mSplashImg.setImageURI(AppUtils.parse(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1531750251795&di=04b6ef1b0d2991ce27fb1b69692dffa5&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fphotoblog%2F1501%2F29%2Fc11%2F2508688_1422519702855_mthumb.jpg"));
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
        mPresenter = new SplashPresenter(this);
        mPresenter.start();
    }


    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void navToHome() {
        //登录之前要初始化群和好友关系链缓存
        FriendshipEvent.getInstance().init();
        GroupEvent.getInstance().init();
//        LoginBusiness.loginIm(UserInfo.getInstance().getId(), UserInfo.getInstance().getUserSig(), this);
        LoginBusiness.loginIm("root2",
                "eJxFkFFPgzAUhf8Lz0buLRTExIeNNYY4QjadPjZIC6tDWks3Hcb-LhIWX78vJ-ee8*09rR*vS2OU4KXjgRXerQfe1YTll1FW8rJ20o4YKaUE4GJP0vZKd6MggBRJAPAvlZCdU7WaglZrR2bRq2YkOdul2WZFd6vtUCMM9r7Ct-XG-1x2IROtgea5HxqV5XkRt7JNiyZrikO8aLF*eSA*AvVZsnhlLDnp6AOi-uyO-j5Yir3YpsDuLsfEgU-d-r4PARDDIE5m6dS7nFoFmERxmNzMvKwqfewcd2cjpzF*fgEZHlZ4", this);

//        gotoMainActivity();
    }

    private void gotoMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void navToLogin() {

    }

    @Override
    public boolean isUserLogin() {
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
        PushUtil.getInstance();
        //初始化消息监听
        MessageEvent.getInstance();
        String deviceMan = android.os.Build.MANUFACTURER;
        //注册小米和华为推送
        if (deviceMan.equals("Xiaomi") && shouldMiInit()) {
            MiPushClient.registerPush(getApplicationContext(), "2882303761517480335", "5411748055335");
        } else if (deviceMan.equals("HUAWEI")) {
            PushManager.requestToken(this);
        }

        String refreshedToken = null;
//        FirebaseInstanceId firebaseInstanceId = FirebaseInstanceId.getInstance();
//        if (firebaseInstanceId != null) refreshedToken = firebaseInstanceId.getToken();
//        Log.d(TAG, "refreshed token: " + refreshedToken);

        if (!TextUtils.isEmpty(refreshedToken)) {
            TIMOfflinePushToken param = new TIMOfflinePushToken();
            param.setToken(refreshedToken);
            param.setBussid(169);
            TIMManager.getInstance().setOfflinePushToken(param);
        }

        Log.d(TAG, "imsdk env " + TIMManager.getInstance().getEnv());
        gotoMainActivity();
    }

    /**
     * 判断小米推送是否已经初始化
     */
    private boolean shouldMiInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        if (am == null) return false;
        List<ActivityManager.RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfoList) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult code:" + requestCode);
        if (LOGIN_RESULT_CODE == requestCode) {
            Log.d(TAG, "login error no " + TLSService.getInstance().getLastErrno());
            if (0 == TLSService.getInstance().getLastErrno()) {
                String id = TLSService.getInstance().getLastUserIdentifier();
                UserInfo.getInstance().setId(id);
                UserInfo.getInstance().setUserSig(TLSService.getInstance().getUserSig(id));
                navToHome();
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
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
