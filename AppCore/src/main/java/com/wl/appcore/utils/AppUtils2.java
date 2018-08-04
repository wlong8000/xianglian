package com.wl.appcore.utils;

import android.content.Context;
import android.text.TextUtils;

import com.orhanobut.hawk.Hawk;
import com.wl.appcore.Keys;
import com.wl.appcore.entity.UserEntity;

/**
 * Created by wanglong on 2018/8/2.
 */

public class AppUtils2 {

    /**
     * 判断用户是否登录
     */
    public static boolean isLogin(Context context) {
        return !TextUtils.isEmpty(getToken());
    }

    public static String isCompleteData() {
        return isCompleteData(-1);
    }

    /**
     * 资料是否填写完整
     */
    public static String isCompleteData(int type) {
        UserEntity userEntity = Hawk.get(Keys.USER_INFO);
        if (userEntity == null) {
            return "请完善个人资料";
        }
        if (type == -1) {
            if (TextUtils.isEmpty(userEntity.getPic1())) {
                return "请上传头像";
            }

//        if (!userEntity.isHas_album()) {
//            return "请上传至少1张照片";
//        }

            if (TextUtils.isEmpty(userEntity.getMate_preference())) {
                return "请填填写择偶标准";
            }
        }

        if (TextUtils.isEmpty(userEntity.getBirthday())) {
            return "请完善个人基本资料";
//            return "请填写出生日期";
        }
        if (TextUtils.isEmpty(userEntity.getBorn_area_name())) {
            return "请完善个人基本资料";
//            return "请填写出生地";
        }

        if (TextUtils.isEmpty(userEntity.getHeight())/* || "0".equals(userEntity.getHeight())*/) {
            return "请完善个人基本资料";
//            return "请填写身高";
        }
        if (TextUtils.isEmpty(userEntity.getEducation())/* || "0".equals(userEntity.getEducation())*/) {
            return "请完善个人基本资料";
//            return "请填写学历";
        }
        if (TextUtils.isEmpty(userEntity.getCareer())/*  || "0".equals(userEntity.getCareer())*/) {
            return "请完善个人基本资料";
//            return "请填写出工作";
        }
        if (TextUtils.isEmpty(userEntity.getIncome())/* || "0".equals(userEntity.getIncome())*/) {
            return "请完善个人基本资料";
//            return "请填写出收入";
        }

        if (TextUtils.isEmpty(userEntity.getConstellation())/* || "0".equals(userEntity.getConstellation())*/) {
            return "请完善个人基本资料";
//            return "请填写星座";
        }
        if (TextUtils.isEmpty(userEntity.getMarriage_status())/* || "0".equals(userEntity.getMarriage_status())*/) {
            return "请完善个人基本资料";
//            return "请填写婚姻状况";
        }
        if (TextUtils.isEmpty(userEntity.getExpect_marry_time())/* || "0".equals(userEntity.getExpect_marry_time())*/) {
            return "请完善个人基本资料";
//            return "请填写期望结婚时间";
        }
        return null;
    }

    public static String getToken() {
        return Hawk.get(Keys.TOKEN);
    }
}
