package com.xianglian.love.model;

import java.io.Serializable;

/**
 * Created by wanglong on 2018/7/24.
 */

public class ConfigEntity implements Serializable {
    private String default_splash_img;
    private String splash_time;
    private String is_jump;

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
}
