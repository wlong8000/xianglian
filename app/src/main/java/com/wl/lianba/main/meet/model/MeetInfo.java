package com.wl.lianba.main.meet.model;


/**
 * Created by wanglong on 17/3/30.
 */

public class MeetInfo {

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

    private int viewType;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

}
