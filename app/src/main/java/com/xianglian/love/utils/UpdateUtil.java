package com.xianglian.love.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.orhanobut.hawk.Hawk;
import com.xianglian.love.BuildConfig;
import com.xianglian.love.DownloadDialogFragment;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.model.DownloadModel;
import com.xianglian.love.model.UpdateEntity;
import com.xianglian.love.net.JsonCallBack;

import base.BaseApplication;


/**
 * Created by wl on 2018/6/27.
 * 升级
 */

public class UpdateUtil {
    public static final int HOUR = 1000 * 60 * 60;

    private static final String TAG = "UpdateUtil";

    public static void checkVersion(final Activity context1) {
        Trace.d(TAG, "checkVersion");
        System.out.print("checkVersion");
        Trace.d(TAG, "checkVersion2");
        final int versionCode = BuildConfig.VERSION_CODE;
        System.out.print("checkVersion2" + versionCode);
        final GetRequest<UpdateEntity.DataBean> request = OkGo.get(Config.PATH + "upgrade/");
        request.execute(new JsonCallBack<UpdateEntity.DataBean>(UpdateEntity.DataBean.class) {
            @Override
            public void onSuccess(Response<UpdateEntity.DataBean> response) {
                Trace.d(TAG, "请求成功");
                System.out.println("请求成功");
                if (response == null) return;
                Trace.d(TAG, "response " + response.body());
                System.out.println("response " + response.body());
                UpdateEntity.DataBean updateEntity = response.body();
                if (updateEntity == null) {
                    return;
                }
                Trace.d(TAG, " updateEntity");
                System.out.println(" updateEntity");

                String time = Hawk.get("upgrade_time");

                boolean timeX = System.currentTimeMillis() - AppUtils.stringToInt(time) < HOUR * AppUtils.stringToInt(updateEntity.getAlert_time());
                Trace.d(TAG, " time r = " + timeX);
                System.out.println(" time r = " + timeX);
                boolean limitX = TextUtils.isEmpty(updateEntity.getOs_version_limit());
                Trace.d(TAG, " limitX r = " + limitX + ", version = " + updateEntity.getVersion() + ", version2 = " + versionCode);
                Trace.d(TAG, " updateVersion r = " + !updateVersionByOSVersion(updateEntity));
                if (System.currentTimeMillis() - AppUtils.string2Long(time) < HOUR * AppUtils.stringToInt(updateEntity.getAlert_time())) {
                    return;
                }
                if (updateEntity.getVersion() <= versionCode) return;

                if (!updateVersionByOSVersion(updateEntity)) {
                    return;
                }
                Trace.d(TAG, "该弹窗了啊 ");
                System.out.print("该弹窗了啊 ");
                Hawk.put("upgrade_time", System.currentTimeMillis() + "");
                DownloadModel model = new DownloadModel();
                model.setDesc(updateEntity.getDesc());
                Context context = BaseApplication.baseApplication;
                model.setTitle(context.getString(R.string.app_version_upgrade_title)
                        + (TextUtils.isEmpty(updateEntity.getVersion_display()) ? ""
                        : (" (" + updateEntity.getVersion_display() + ")")));
                model.setUpgrade(true);
                model.setNotificationTitle(
                        context.getString(R.string.app_version_upgrade_title));
                model.setNotificationDesc(
                        context.getString(R.string.app_version_upgrade_notif_desc));
                model.setFilename("dongqiudi_news_" + updateEntity.getVersion() + ".apk");
                model.setUrl(updateEntity.getUrl());
                Trace.d(TAG, "dataBean.getIs_mandatory()  " + updateEntity.getIs_mandatory());
                model.setIs_mandatory(updateEntity.getIs_mandatory());

                showDownloadDialog(context1, model);

            }

            @Override
            public void onError(Response<UpdateEntity.DataBean> response) {
                super.onError(response);
                Trace.d(TAG, "error response " + response.body());
                System.out.print("error response " + response.body());
            }
        });
    }

    private static void showDownloadDialog(Activity context1, DownloadModel model) {
        //清除已经存在的，同样的fragment
        Trace.d(TAG, "context1 = " + context1);
        FragmentTransaction ft = context1.getFragmentManager().beginTransaction();
        Fragment prev = context1.getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DownloadDialogFragment dialogFragment = DownloadDialogFragment.newInstance(model);
        dialogFragment.setCancelable(model.getIs_mandatory() != 1);
        dialogFragment.show(ft, "dialog");
    }

    private static boolean updateVersionByOSVersion(UpdateEntity.DataBean entity) {
        if (entity == null || TextUtils.isEmpty(entity.getOs_version_limit())) return false;
        String os_version_limit = entity.getOs_version_limit();
        Trace.d(TAG, "&&&&&&& version = " + os_version_limit + ", VERSION = " + Build.VERSION.SDK_INT);
        int os_version;
        if (os_version_limit.startsWith(">=")) {
            Trace.d(TAG, "&&&&&&& >=");
            os_version = getOSVersionCode(os_version_limit, ">=");
            if (Build.VERSION.SDK_INT >= os_version) return true;
        } else if (os_version_limit.startsWith("<")) {
            os_version = getOSVersionCode(os_version_limit, "<");
            Trace.d(TAG, "&&&&&&& < " + os_version);
            if (Build.VERSION.SDK_INT < os_version) return true;
        } else if (os_version_limit.startsWith("=")) {
            Trace.d(TAG, "&&&&&&& =");
            os_version = getOSVersionCode(os_version_limit, "=");
            if (Build.VERSION.SDK_INT == os_version) return true;
        } else if (AppUtils.stringToInt(os_version_limit) > 0) {
            Trace.d(TAG, "&&&&&&& int");
            os_version = AppUtils.stringToInt(os_version_limit);
            if (Build.VERSION.SDK_INT == os_version) return true;
        }
        return false;
    }

    private static int getOSVersionCode(String version, String key) {
        try {
            String arr[] = version.split(key);
            if (arr.length > 1) {
                return Integer.parseInt(arr[1]);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;

    }

}
