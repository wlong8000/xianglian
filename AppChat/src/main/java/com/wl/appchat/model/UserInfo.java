package com.wl.appchat.model;

/**
 * 用户数据
 */
public class UserInfo {

    private static UserInfo ourInstance = new UserInfo();
    private String id;
    private String userSig;

    public static UserInfo getInstance() {
        return ourInstance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserSig() {
        return userSig;
    }

    public void setUserSig(String userSig) {
        this.userSig = userSig;
    }

}