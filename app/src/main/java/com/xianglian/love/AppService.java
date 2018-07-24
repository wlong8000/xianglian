package com.xianglian.love;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.orhanobut.hawk.Hawk;
import com.xianglian.love.config.Config;
import com.xianglian.love.config.Keys;
import com.xianglian.love.main.home.been.UserEntity;
import com.xianglian.love.net.JsonCallBack;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.Trace;

/**
 * Created by wanglong on 17/4/2.
 */

public class AppService extends IntentService {
    private final String TAG = "AppService";

    private final static String ACTION_SAVE_USER = "com.wl.lianba.service.action.SAVE_USER";
    private final static String ACTION_UPDATE_TIM_SIGN = "com.wl.lianba.service.action.ACTION_UPDATE_TIM_SIGN";

    public AppService() {
        super("HttpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.e(TAG, intent.getAction());
            if (ACTION_UPDATE_TIM_SIGN.equals(intent.getAction())) {
                String username = intent.getStringExtra(Keys.USER_TIM_SIGN);
                Trace.d(TAG, "username = " + username);
                doTimSignRequest(username);
            } else if (ACTION_SAVE_USER.equals(intent.getAction())) {
                doUserInfoRequest();
            }
        }
    }

    public static void startSaveUser(Context context) {
        Intent service = new Intent(context, AppService.class);
        service.setAction(AppService.ACTION_SAVE_USER);
        context.startService(service);
    }

    public static void startUpdateTimSign(Context context, String username) {
        Intent service = new Intent(context, AppService.class);
        service.setAction(AppService.ACTION_UPDATE_TIM_SIGN);
        service.putExtra(Keys.USER_TIM_SIGN, username);
        context.startService(service);
    }

    private void doTimSignRequest(final String username) {
        if (TextUtils.isEmpty(username)) return;
        GetRequest<UserEntity> request = OkGo.get(Config.PATH + "user_tim_sign/");
        request.params("username", username);
        request.headers("Authorization", AppUtils.getToken(this));
        request.execute(new JsonCallBack<UserEntity>(UserEntity.class) {
            @Override
            public void onSuccess(Response<UserEntity> response) {
                UserEntity entity = response.body();
                Trace.d(TAG, "onSuccess entity " + entity);
                if (entity == null) {
                    return;
                }
                Trace.d(TAG, "sign = " + entity.getUser_sign());
                Hawk.put(Keys.USER_TIM_SIGN, entity);
            }

            @Override
            public void onError(Response<UserEntity> response) {
                super.onError(response);
                Trace.d(TAG, "onError = ");
            }
        });
    }

    private void doUserInfoRequest() {
        final GetRequest<UserEntity> request = OkGo.get(Config.PATH + "user_info");
        request.headers("Authorization", AppUtils.getToken(this));
        request.execute(new JsonCallBack<UserEntity>(UserEntity.class) {
            @Override
            public void onSuccess(Response<UserEntity> response) {
                if (response != null && response.body() != null) {
                    UserEntity entity = response.body();
                    Hawk.put(Keys.USER_INFO, entity.getResults().get(0));
                } else {
                    Hawk.put(Keys.USER_INFO, null);
                }
            }

            @Override
            public void onError(Response<UserEntity> response) {
                super.onError(response);
            }
        });
    }

}
