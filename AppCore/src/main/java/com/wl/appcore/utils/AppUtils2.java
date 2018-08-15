package com.wl.appcore.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
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

    public static Uri parse(String url) {
        if (TextUtils.isEmpty(url))
            return Uri.parse("");
        return Uri.parse(url);
    }

    public static void loadLocalImage(Context context, SimpleDraweeView img, @DrawableRes int res) {
        Uri uri = Uri.parse("res://" + context.getPackageName() + "/" + res);
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                .build();
        img.setController(draweeController);
    }

    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return !TextUtils.isEmpty(mobileNums) && mobileNums.matches(telRegex);
    }
}
