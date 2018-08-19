package com.xianglian.love.main.meet.model;


import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglong on 17/3/30.
 */

public class MeetInfo implements Serializable,MultiItemEntity {
    private List<MeetInfo> results;
    private String next;

    private String title;
    private String theme;
    private String content;
    private String url;
    private String city;
    private String nick_name;
    private String place_detail;
    private String meet_time;
    private String mobile;
    private String sub_describe;
    private String price;
    private String tag1;
    private String tag2;
    private String tag3;
    private int res;
    private int id;

    private int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }

    public interface ViewType {

        /**
         * 普通条目
         */
        int COMMON_INFO = 0;

        /**
         * 顶部
         */
        int HEADER = 1;

        /**
         * 普通条目
         */
        int ITEM = 2;

    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public List<MeetInfo> getResults() {
        return results;
    }

    public void setResults(List<MeetInfo> results) {
        this.results = results;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPlace_detail() {
        return place_detail;
    }

    public void setPlace_detail(String place_detail) {
        this.place_detail = place_detail;
    }

    public String getMeet_time() {
        return meet_time;
    }

    public void setMeet_time(String meet_time) {
        this.meet_time = meet_time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSub_describe() {
        return sub_describe;
    }

    public void setSub_describe(String sub_describe) {
        this.sub_describe = sub_describe;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setViewType(int viewType) {
        this.itemType = viewType;
    }
}
