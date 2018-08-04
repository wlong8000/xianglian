package com.xianglian.love.model;

import java.io.Serializable;

/**
 * Created by wanglong on 2018/7/24.
 */

public class ConfigEntity implements Serializable {
    private String default_splash_img;
    private String splash_time;
    private String is_jump;
    private String contacts_wx;
    private String server_path;
    private String image_path;

    public String getDefault_splash_img() {
        return default_splash_img;
    }

    public void setDefault_splash_img(String default_splash_img) {
        this.default_splash_img = default_splash_img;
    }

    public String getSplash_time() {
        return splash_time;
    }

    public void setSplash_time(String splash_time) {
        this.splash_time = splash_time;
    }

    public String getIs_jump() {
        return is_jump;
    }

    public void setIs_jump(String is_jump) {
        this.is_jump = is_jump;
    }

    public String getContacts_wx() {
        return contacts_wx;
    }

    public void setContacts_wx(String contacts_wx) {
        this.contacts_wx = contacts_wx;
    }

    public String getServer_path() {
        return server_path;
    }

    public void setServer_path(String server_path) {
        this.server_path = server_path;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
