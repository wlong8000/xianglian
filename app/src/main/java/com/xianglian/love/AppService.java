package com.xianglian.love;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.orhanobut.hawk.Hawk;
import com.wl.appcore.event.MessageEvent2;
import com.wl.appcore.manager.UserCenter;
import com.xianglian.love.config.Config;
import com.wl.appcore.Keys;
import com.wl.appcore.entity.UserEntity;
import com.xianglian.love.model.ConfigEntity;
import com.xianglian.love.net.JsonCallBack;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.Trace;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wanglong on 17/4/2.
 */

public class AppService extends IntentService {
    private final String TAG = "AppService";

    private final static String ACTION_SAVE_USER = "com.wl.lianba.service.action.SAVE_USER";
    private final static String ACTION_UPDATE_TIM_SIGN = "com.wl.lianba.service.action.ACTION_UPDATE_TIM_SIGN";
    private final static String ACTION_CONFIG_INFO = "com.wl.lianba.service.action.ACTION_CONFIG_INFO";
    private final static String ACTION_DEVICE_INFO = "com.wl.lianba.service.action.ACTION_DEVICE_INFO";
    private final static String KEY_SEND_BROADCAST = "send_broadcast";

    public AppService() {
        super("HttpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.e(TAG, intent.getAction());
            if (ACTION_UPDATE_TIM_SIGN.equals(intent.getAction())) {
                String username = intent.getStringExtra(Keys.USER_TIM_SIGN);
                boolean send = intent.getBooleanExtra(KEY_SEND_BROADCAST, false);
                Trace.d(TAG, "username = " + username);
                doTimSignRequest(username, send);
            } else if (ACTION_SAVE_USER.equals(intent.getAction())) {
                boolean send = intent.getBooleanExtra(KEY_SEND_BROADCAST, false);
                doUserInfoRequest(send);
            } else if (ACTION_CONFIG_INFO.equals(intent.getAction())) {
                doConfigRequest();
            } else if (ACTION_DEVICE_INFO.equals(intent.getAction())) {
                doDeviceInfo();
            }
        }
    }

    public static void startSaveUser(Context context, boolean sendBroadcast) {
        Intent service = new Intent(context, AppService.class);
        service.setAction(AppService.ACTION_SAVE_USER);
        service.putExtra(KEY_SEND_BROADCAST, sendBroadcast);
        context.startService(service);
    }

    public static void startConfigInfo(Context context) {
        Intent service = new Intent(context, AppService.class);
        service.setAction(AppService.ACTION_CONFIG_INFO);
        context.startService(service);
    }

    public static void startDeviceInfo(Context context) {
        Intent service = new Intent(context, AppService.class);
        service.setAction(AppService.ACTION_DEVICE_INFO);
        context.startService(service);
    }

    public static void startUpdateTimSign(Context context, String username) {
        startUpdateTimSign(context, username, false);
    }

    public static void startUpdateTimSign(Context context, String username, boolean sendBroadcast) {
        Intent service = new Intent(context, AppService.class);
        service.setAction(AppService.ACTION_UPDATE_TIM_SIGN);
        service.putExtra(Keys.USER_TIM_SIGN, username);
        service.putExtra(KEY_SEND_BROADCAST, sendBroadcast);
        context.startService(service);
    }

    /**
     * Tim相关
     */
    private void doTimSignRequest(final String username, final boolean send) {
        if (TextUtils.isEmpty(username)) return;
        GetRequest<UserEntity> request = OkGo.get(Config.PATH + "user_tim_sign/");
        request.params("username", username);
        request.headers("Authorization", AppUtils.getToken(this));
        request.execute(new JsonCallBack<UserEntity>(UserEntity.class) {
            @Override
            public void onSuccess(Response<UserEntity> response) {
                UserEntity entity = response.body();
                Trace.d(TAG, "onSuccess entity " + entity);
                if (entity == null || TextUtils.isEmpty(entity.getUser_sign())) {
                    return;
                }
                Trace.d(TAG, "sign = " + entity.getUser_sign());
                Hawk.put(Keys.USER_TIM_SIGN, entity);
                if (send)
                    EventBus.getDefault().post(new MessageEvent2(entity, 0));
            }

            @Override
            public void onError(Response<UserEntity> response) {
                super.onError(response);
                Trace.d(TAG, "onError = ");
                if (send)
                    EventBus.getDefault().post(new MessageEvent2(null, 0));
            }
        });
    }

    /**
     * 用户信息
     */
    private void doUserInfoRequest(final boolean send) {
        final GetRequest<UserEntity> request = OkGo.get(Config.PATH + "user_info");
        request.headers("Authorization", AppUtils.getToken(this));
        request.execute(new JsonCallBack<UserEntity>(UserEntity.class) {
            @Override
            public void onSuccess(Response<UserEntity> response) {
                if (response != null
                        && response.body() != null
                        && response.body().getResults() != null
                        && !response.body().getResults().isEmpty()) {
                    UserEntity entity = response.body().getResults().get(0);
                    if ("1".equals(entity.getBlack_user())) {
                        if (send) {
                            EventBus.getDefault().post(new MessageEvent2(null, 3));
                        } else {
                            AppUtils.showToast(getString(R.string.action_forbade));
                        }
                        UserCenter.getDefault().notifyLogout();
                        return;
                    }
                    UserCenter.getDefault().notifyLoginOk(entity);
                    if (send)
                        EventBus.getDefault().post(new MessageEvent2(entity, 1));
                } else {
                    Hawk.put(Keys.USER_INFO, null);
                    if (send)
                        EventBus.getDefault().post(new MessageEvent2(null, 1));
                }
            }

            @Override
            public void onError(Response<UserEntity> response) {
                super.onError(response);
                if (send)
                    EventBus.getDefault().post(new MessageEvent2(null, 1));
            }
        });
    }

    private void doConfigRequest() {
        final GetRequest<ConfigEntity> request = OkGo.get(Config.PATH + "config/");
        request.headers("Authorization", AppUtils.getToken(this));
        request.execute(new JsonCallBack<ConfigEntity>(ConfigEntity.class) {
            @Override
            public void onSuccess(Response<ConfigEntity> response) {
                if (response != null && response.body() != null) {
                    ConfigEntity entity = response.body();
                    Trace.d(TAG, "doConfigRequest = " + response.body());
                    Hawk.put(Keys.CONFIG_INFO, entity);
                } else {
                    Hawk.put(Keys.CONFIG_INFO, null);
                }
            }

            @Override
            public void onError(Response<ConfigEntity> response) {
                super.onError(response);
            }
        });
    }

    private void doDeviceInfo() {
        OkGo.<String>post(Config.PATH + "user_device/")
                .params("device_id", AppUtils.getUniquePsuedoID())
                .headers(AppUtils.getHeaders(this))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Trace.d(TAG, "doDeviceInfo success");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Trace.d(TAG, "doDeviceInfo onError");
                    }
                });
    }

}
