package com.xianglian.love.model;

import java.util.List;

/**
 * Created by txl on 2018/6/27.
 */

public class UpdateEntity {

    /**
     * errno : 0
     * errmsg : SUCCESS!
     * data : [{"id":"1","type":"Android懂球帝升级提醒","title":"懂球帝6.0.7了！","desc":"1，体验优化，修复部分bug。","version":"159 6.0.","os_version_limit":"","url":"https://dqdfiles.b0.upaiyun.com/app/apk/dongqiudi-6.0.7.apk","alert":"1","update":"1","redirect":"0","is_mandatory":"0","alert_num":"3","alert_time":"72","button":"更新"}]
     */

    private int errno;
    private String errmsg;
    private List<DataBean> data;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * type : Android懂球帝升级提醒
         * title : 懂球帝6.0.7了！
         * desc : 1，体验优化，修复部分bug。
         * version : 159 6.0.
         * os_version_limit :
         * url : https://dqdfiles.b0.upaiyun.com/app/apk/dongqiudi-6.0.7.apk
         * alert : 1
         * update : 1
         * redirect : 0
         * is_mandatory : 0
         * alert_num : 3
         * alert_time : 72
         * button : 更新
         */

        private String id;
        private String type;
        private String title;
        private String desc;
        private int version;
        private String os_version_limit;
        private String url;
        private String alert;
        private int update;
        private int redirect;
        private int is_mandatory;
        private int alert_num;
        private String alert_time;
        private String button;
        private int version_mandatory;
        private String version_display;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getOs_version_limit() {
            return os_version_limit;
        }

        public void setOs_version_limit(String os_version_limit) {
            this.os_version_limit = os_version_limit;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAlert() {
            return alert;
        }

        public void setAlert(String alert) {
            this.alert = alert;
        }

        public int getUpdate() {
            return update;
        }

        public void setUpdate(int update) {
            this.update = update;
        }

        public int getRedirect() {
            return redirect;
        }

        public void setRedirect(int redirect) {
            this.redirect = redirect;
        }

        public int getIs_mandatory() {
            return is_mandatory;
        }

        public void setIs_mandatory(int is_mandatory) {
            this.is_mandatory = is_mandatory;
        }

        public int getAlert_num() {
            return alert_num;
        }

        public void setAlert_num(int alert_num) {
            this.alert_num = alert_num;
        }

        public String getAlert_time() {
            return alert_time;
        }

        public void setAlert_time(String alert_time) {
            this.alert_time = alert_time;
        }

        public String getButton() {
            return button;
        }

        public void setButton(String button) {
            this.button = button;
        }

        public int getVersion_mandatory() {
            return version_mandatory;
        }

        public void setVersion_mandatory(int version_mandatory) {
            this.version_mandatory = version_mandatory;
        }

        public String getVersion_display() {
            return version_display;
        }

        public void setVersion_display(String version_display) {
            this.version_display = version_display;
        }
    }
}
