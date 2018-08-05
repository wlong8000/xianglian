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
    private String meet_data;
    private String mobile;

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
         * 文章
         */
        int ARTICLE = 1;

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

    public String getMeet_data() {
        return meet_data;
    }

    public void setMeet_data(String meet_data) {
        this.meet_data = meet_data;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setViewType(int viewType) {
        this.itemType = viewType;
    }
}
