package com.xianglian.love.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.orhanobut.hawk.Hawk;
import com.xianglian.love.R;
import com.xianglian.love.config.Keys;
import com.xianglian.love.main.home.been.UserDetailEntity;
import com.wl.appcore.entity.UserEntity;
import com.xianglian.love.user.been.ItemInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wanglong on 17/3/30.
 * 个人信息数据
 */

public class UserUtils {


    public static List<String> getHighData() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 140; i < 200; i++) {
            data.add(String.valueOf(i));
        }
        return data;
    }

    public static List<String> getAge() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 18; i < 60; i++) {
            data.add(String.valueOf(i));
        }
        return data;
    }

    /**
     * 体重
     */
    public static List<String> getWeight() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 35; i < 150; i++) {
            data.add(String.valueOf(i));
        }
        return data;
    }

    static List<List<String>> getSubData(Context context, int type) {
        List<List<String>> subData = new ArrayList<>();

        List<String> list = null;
        switch (type) {
            case ItemInfo.Type.AGE:
                list = getAge();
                break;
            case ItemInfo.Type.HEIGHT:
                list = getHighData();
                break;
            case ItemInfo.Type.EDUCATION:
                list = getEduData(context);
                break;
        }
        if (list == null) return null;
        int count = list.size();
        for (int i = 0; i < count; i++) {
            List<String> temp = new ArrayList<>();
            for (int q = i; q < count; q++) {
                temp.add(list.get(q));
            }
            subData.add(temp);
        }
        return subData;
    }

    public static List<List<String>> getSubAge(Context context) {
        return getSubData(context, ItemInfo.Type.AGE);
    }

    public static List<List<String>> getSubHeight(Context context) {
        return getSubData(context, ItemInfo.Type.HEIGHT);
    }

    public static List<String> getEduData(Context context) {
        return getResources(context, R.array.edu);
    }

    @NonNull
    public static List<String> getResources(Context context, @ArrayRes int id) {
        Resources res = context.getResources();
        return new ArrayList<>(Arrays.asList(res.getStringArray(id)));
    }

    public static List<List<String>> getSubEdu(Context context) {
        return getSubData(context, ItemInfo.Type.EDUCATION);
    }

    public static List<String> getComingData(Context context) {
        return getResources(context, R.array.income);
    }

    /**
     * 婚姻状况
     */
    public static List<String> getMarryState(Context context) {
        return getResources(context, R.array.marry_state);
    }

    public static String getMarry(int marry) {
        switch (marry) {
            case 1:
                return "未婚";
            case 2:
                return "离异";
            case 3:
                return "丧偶";
            default:
                return "未婚";
        }
    }

    public static String getCareer(int career) {
        switch (career) {
            case 1:
                return "在校学生";
            case 2:
                return "私营业主";
            case 3:
                return "农业劳动者";
            case 4:
                return "企业职工";
            case 5:
                return "政府机关/事业单位";
            default:
                return "自由职业";
        }
    }

    public static List<String> getProfessionData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("在校学生");
        data.add("私营业主");
        data.add("农业劳动者");
        data.add("企业职工");
        data.add("政府机关/事业单位");
        data.add("自由职业");
        return data;
    }

    public static String getIncome(int inCome) {
        switch (inCome) {
            case 1:
                return "5K以下";
            case 2:
                return "5K~8K";
            case 3:
                return "8K~12K";
            case 4:
                return "12K~20K";
            case 5:
                return "20K-30K";
            default:
                return "30K以上";
        }
    }

    public static String getEdu(int edu) {
        switch (edu) {
            case 1:
                return "高中/技校";
            case 2:
                return "专科";
            case 3:
                return "本科";
            case 4:
                return "研究生";
            case 5:
                return "海龟";
            default:
                return "高中/技校";
        }
    }

    /**
     * 期望结婚时间
     */
    public static List<String> getHopeMarry() {
        ArrayList<String> data = new ArrayList<>();
        data.add("3个月内");
        data.add("6个月内");
        data.add("1年内");
        data.add("2年内");
        data.add("2年以上");
        return data;
    }

    /**
     * 民族
     */
    public static List<String> getNation() {
        ArrayList<String> data = new ArrayList<>();
        data.add("汉族");
        data.add("少数民族");
        return data;
    }

    /**
     * 家中排行
     */
    public static List<String> getRankings() {
        ArrayList<String> data = new ArrayList<>();
        data.add("老大");
        data.add("老二");
        data.add("老三");
        data.add("老四");
        return data;
    }

    /**
     * 是否
     */
    public static List<String> getRight() {
        ArrayList<String> data = new ArrayList<>();
        data.add("是");
        data.add("否");
        return data;
    }


    public static List<String> getBaseInfoList(UserDetailEntity entity) {
        if (entity == null) return null;
        List<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(entity.getConstellation())) {
            list.add("星座/" + entity.getConstellation());
        }
        if (!TextUtils.isEmpty(entity.getWork_area_name())) {
            list.add("工作地/" + entity.getWork_area_name());
        }
        if (!TextUtils.isEmpty(entity.getBorn_area_name())) {
            list.add("出生地/" + entity.getBorn_area_name());
        }
        return list;
    }

    public static UserEntity getUserEntity() {
        return Hawk.get(Keys.USER_INFO);
    }

}
