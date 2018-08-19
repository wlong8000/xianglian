package com.xianglian.love.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;

import com.orhanobut.hawk.Hawk;
import com.xianglian.love.R;
import com.wl.appcore.Keys;
import com.wl.appcore.entity.UserEntity;
import com.xianglian.love.user.been.ItemInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import base.BaseApplication;

/**
 * Created by wanglong on 17/3/30.
 * 个人信息数据
 */

public class UserUtils {

    /**
     * 身高
     */
    public static List<String> getHighData() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 140; i < 200; i++) {
            data.add(String.valueOf(i));
        }
        return data;
    }

    public static List<List<String>> getSubHeight() {
        return getSubData(ItemInfo.Type.HEIGHT);
    }

    /**
     * 年龄
     */
    public static List<String> getAge() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 18; i < 60; i++) {
            data.add(String.valueOf(i));
        }
        return data;
    }

    public static List<List<String>> getSubAge() {
        return getSubData(ItemInfo.Type.AGE);
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

    /**
     * 职业
     */
    public static List<String> getProfessions() {
        return getArray(R.array.profession);
    }

    public static String getProfession(int item) {
        return getItem(getProfessions(), item);
    }

    /**
     * 收入
     */
    public static List<String> getInComingList() {
        return getArray(R.array.income);
    }

    public static String getInCome(int item) {
        return getItem(getInComingList(), item);
    }

    /**
     * 教育程度
     */
    public static List<String> getEduList() {
        return getArray(R.array.edu);
    }

    public static String getEdu(int item) {
        return getItem(getEduList(), item);
    }

    public static List<List<String>> getSubEdu() {
        return getSubData(ItemInfo.Type.EDUCATION);
    }

    /**
     * 婚姻状况
     */
    public static List<String> getMarryStates() {
        return getArray(R.array.marry_state);
    }

    public static String getMarryState(int item) {
        return getItem(getMarryStates(), item);
    }

    /**
     * 期望结婚时间
     */
    public static List<String> getHopeMarries() {
        return getArray(R.array.expect_marry_time);
    }

    public static String getHopeMarry(int item) {
        return getItem(getHopeMarries(), item);
    }

    /**
     * 民族
     */
    public static List<String> getNations() {
        return getArray(R.array.nation);
    }

    public static String getNation(int item) {
        return getItem(getNations(), item);
    }

    /**
     * 家中姊妹情况
     */
    public static List<String> getBrotherStates() {
        return getArray(R.array.brother_state);
    }

    public static String getBrotherState(int item) {
        return getItem(getBrotherStates(), item);
    }

    /**
     * 星座
     */
    public static List<String> getConstellations() {
        return getArray(R.array.constellation);
    }

    public static String getConstellation(int item) {
        return getItem(getConstellations(), item);
    }

    /**
     * 父母工作
     */
    public static List<String> getParentProfessions() {
        return getArray(R.array.parent_profession);
    }

    public static String getParentProfession(int item) {
        return getItem(getParentProfessions(), item);
    }

    @NonNull
    public static List<String> getResources(Context context, @ArrayRes int id) {
        Resources res = context.getResources();
        return new ArrayList<>(Arrays.asList(res.getStringArray(id)));
    }

    public static List<String> getArray(@ArrayRes int id) {
        return getResources(BaseApplication.baseApplication, id);
    }

    public static String getItem(List<String> list, int item) {
        if (item >= list.size() || item < 0) return null;
        return list.get(item);
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

    public static List<List<String>> getSubData(int type) {
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
                list = getEduList();
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


//    public static List<String> getBaseInfoList(UserDetailEntity entity) {
//        if (entity == null) return null;
//        List<String> list = new ArrayList<>();
//        if (!TextUtils.isEmpty(entity.getConstellation())) {
//            list.add(UserUtils.getConstellation(AppUtils.stringToInt(entity.getConstellation())));
//        }
//        if (!TextUtils.isEmpty(entity.getEducation())) {
//            list.add(UserUtils.getEdu(AppUtils.stringToInt(entity.getEducation())));
//        }
//        if (!TextUtils.isEmpty(entity.getWork_area_name())) {
//            list.add("工作地/" + entity.getWork_area_name());
//        }
//        if (!TextUtils.isEmpty(entity.getBorn_area_name())) {
//            list.add("出生地/" + entity.getBorn_area_name());
//        }
//        if (!TextUtils.isEmpty(entity.getMarriage_status())) {
//            list.add(UserUtils.getMarryState(AppUtils.stringToInt(entity.getMarriage_status())));
//        }
//        if (!TextUtils.isEmpty(entity.getNationality())) {
//            list.add(UserUtils.getNation(AppUtils.stringToInt(entity.getNationality())));
//        }
//        if (!TextUtils.isEmpty(entity.getBrother_state())) {
//            list.add(UserUtils.getBrotherState(AppUtils.stringToInt(entity.getBrother_state())));
//        }
//        if (!TextUtils.isEmpty(entity.getWeight())) {
//            list.add("体重(kg)/" + AppUtils.stringToInt2(entity.getWeight()));
//        }
//        if (!TextUtils.isEmpty(entity.getParent_work())) {
//            list.add("父母工作/" + UserUtils.getParentProfession(AppUtils.stringToInt(entity.getParent_work())));
//        }
//        return list;
//    }

    public static UserEntity getUserEntity() {
        return Hawk.get(Keys.USER_INFO);
    }

}
