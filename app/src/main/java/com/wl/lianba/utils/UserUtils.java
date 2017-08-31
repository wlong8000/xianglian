package com.wl.lianba.utils;

import android.content.Context;
import android.content.res.Resources;

import com.wl.lianba.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wanglong on 17/3/30.
 */

public class UserUtils {
    public static ArrayList<String> getHighData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("140cm以下");
        for (int i = 140; i < 200; i++) {
            data.add(String.valueOf(i));
        }
        data.add("200cm以上");
        return data;
    }


    public static ArrayList<String> getEduData(Context context) {
        Resources res =context.getResources();
        return new ArrayList<>(Arrays.asList(res.getStringArray(R.array.edu)));
    }

    public static ArrayList<String> getComingData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("3000以下");
        data.add("3000~5000");
        data.add("5000~8000");
        data.add("8000~10000");
        data.add("10000~20000");
        data.add("20000以上");
        return data;
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
     * 婚姻状况
     */
    public static ArrayList<String> getMarryState() {
        ArrayList<String> data = new ArrayList<>();
        data.add("未婚");
        data.add("离异");
        data.add("丧偶");
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

    public static String getPhone(Context context) {
        return "";
    }


}
