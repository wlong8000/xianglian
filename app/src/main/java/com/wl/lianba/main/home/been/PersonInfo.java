package com.wl.lianba.main.home.been;

import com.wl.lianba.user.been.VerifyCard;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wanglong on 17/3/12.
 * 对应首页-home model
 */

public class PersonInfo {
    /**
     * 详情页 分类
     */
    public interface ViewType {
        /**
         * 头部信息
         */
        int TOP_INFO = 0;

        /**
         * 个人简介
         */
        int INTRODUCE = 1;

        /**
         * 情感经历
         */
        int EXPERIENCE_EMOTION = 2;

        /**
         * 兴趣爱好
         */
        int FAVORITE = 3;

        /**
         * 个人标签
         */
        int MARK = 4;

        /**
         * 相册
         */
        int ALBUM = 5;

        /**
         * 基本资料
         */
        int BASE_INFO = 6;
    }

    public interface Sex {
        int MAN = 1;
        int WOMAN = 0;
    }


    /**
     * 用户昵称
     */
    private String nick_name;

    /**
     * 0、未认证
     * 1、身份证认证
     * 2、已付费
     */
    private Integer user_state;

    /**
     * 身高
     */
    private int height;

    /**
     * 职业
     */
    private String jobs;

    /**
     * 年收入
     */
    private String income;

    /**
     * 个人介绍
     */
    private String introduce;

    /**
     * 性别
     * 1：男，0：女
     */
    private int sex;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 目前居住地
     */
    private String apartment;

    /**
     * 籍贯
     */
    private String native_place;

    /**
     * 体重
     */
    private int weight;

    /**
     * 婚姻状态
     */
    private String marryState;

    /**
     * 学历
     */
    private String education;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 情感经历
     */
    private String experience;

    /**
     * 相册
     */
    private List<PhotoInfo> album;

    /**
     * 个人标签
     */
    private ArrayList<String> mark;

    /**
     * 兴趣爱好
     */
    private ArrayList<String> hobby;


    private VerifyCard verify_card;

    private int viewType;


    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public Integer getUser_state() {
        return user_state;
    }

    public void setUser_state(Integer user_state) {
        this.user_state = user_state;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getNative_place() {
        return native_place;
    }

    public void setNative_place(String native_place) {
        this.native_place = native_place;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<PhotoInfo> getAlbum() {
        return album;
    }

    public void setAlbum(List<PhotoInfo> album) {
        this.album = album;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public ArrayList<String> getHobby() {
        return hobby;
    }

    public void setHobby(ArrayList<String> hobby) {
        this.hobby = hobby;
    }

    public ArrayList<String> getMark() {
        return mark;
    }

    public void setMark(ArrayList<String> mark) {
        this.mark = mark;
    }

    public String getMarryState() {
        return marryState;
    }

    public void setMarryState(String marryState) {
        this.marryState = marryState;
    }

    public VerifyCard getVerify_card() {
        return verify_card;
    }

    public void setVerify_card(VerifyCard verify_card) {
        this.verify_card = verify_card;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

}
