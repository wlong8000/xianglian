package com.wl.lianba;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by wanglong on 17/4/2.
 */

public class AppService extends IntentService {
    private final String TAG = "AppService";

    private final static String ACTION_SAVE_USER = "com.wl.lianba.service.action.SAVE_USER";
    public AppService() {
        super("HttpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.e(TAG, intent.getAction());
            if (ACTION_SAVE_USER.equals(intent.getAction())) {

            }
        }
    }

    public static void startSaveUser(Context context) {
        Intent service = new Intent(context, AppService.class);
        service.setAction(AppService.ACTION_SAVE_USER);
        context.startService(service);
    }

}
