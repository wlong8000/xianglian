package com.xianglian.love.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;

import com.xianglian.love.R;
import com.xianglian.love.user.been.ItemInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wanglong on 17/3/30.
 * 个人信息数据
 */

public class UserUtils {


    public static ArrayList<String> getHighData() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 140; i < 200; i++) {
            data.add(String.valueOf(i));
        }
        return data;
    }

    public static ArrayList<String> getAge() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 18; i < 60; i++) {
            data.add(String.valueOf(i));
        }
        return data;
    }

    /**
     * 体重
     */
    public static ArrayList<String> getWeight() {
        ArrayList<String> data = new ArrayList<>();
        data.add("40kg以下");
        for (int i = 40; i < 100; i++) {
            data.add(String.valueOf(i));
        }
        data.add("100kg以上");
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

    public static ArrayList<String> getEduData(Context context) {
        return getResources(context, R.array.edu);
    }

    @NonNull
    public static ArrayList<String> getResources(Context context, @ArrayRes int id) {
        Resources res =context.getResources();
        return new ArrayList<>(Arrays.asList(res.getStringArray(id)));
    }

    public static List<List<String>> getSubEdu(Context context) {
        return getSubData(context, ItemInfo.Type.EDUCATION);
    }

    public static ArrayList<String> getComingData(Context context) {
        return getResources(context, R.array.income);
    }

    /**
     * 婚姻状况
     */
    public static ArrayList<String> getMarryState(Context context) {
        return getResources(context, R.array.marry_state);
    }

    public static ArrayList<String> getProfessionData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("在校学生");
        data.add("私营业主");
        data.add("农业劳动者");
        data.add("企业职工");
        data.add("政府机关/事业单位");
        data.add("自由职业");
        return data;
    }

    /**
     * 期望结婚时间
     */
    public static ArrayList<String> getHopeMarry() {
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
    public static ArrayList<String> getNation() {
        ArrayList<String> data = new ArrayList<>();
        data.add("汉族");
        data.add("少数民族");
        return data;
    }

    /**
     * 家中排行
     */
    public static ArrayList<String> getRankings() {
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
    public static ArrayList<String> getRight() {
        ArrayList<String> data = new ArrayList<>();
        data.add("是");
        data.add("否");
        return data;
    }


}
