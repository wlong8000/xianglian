package com.wl.appchat;

import android.app.ActivityManager;
import android.content.Context;

import com.huawei.android.pushagent.PushManager;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.wl.appchat.utils.PushUtil;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * Created by wanglong on 2018/7/25.
 */

public class TimHelper {
    private static TimHelper ourInstance = new TimHelper();

    public static TimHelper getInstance() {
        return ourInstance;
    }

    public void initMessage() {
        //初始化程序后台后消息推送
        PushUtil.getInstance();
        //初始化消息监听
        MessageEvent.getInstance();
        String deviceMan = android.os.Build.MANUFACTURER;
        //注册小米和华为推送
        if (deviceMan.equals("Xiaomi") && shouldMiInit()) {
            MiPushClient.registerPush(MyApplication.getContext(), "2882303761517480335", "5411748055335");
        } else if (deviceMan.equals("HUAWEI")) {
            PushManager.requestToken(MyApplication.getContext());
        }
    }

    /**
     * 判断小米推送是否已经初始化
     */
    private boolean shouldMiInit() {
        ActivityManager am = ((ActivityManager) MyApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE));
        if (am == null) return false;
        List<ActivityManager.RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
        String mainProcessName = MyApplication.getContext().getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfoList) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
