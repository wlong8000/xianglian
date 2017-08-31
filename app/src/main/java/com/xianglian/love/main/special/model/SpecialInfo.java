package com.xianglian.love.main.special.model;


/**
 * Created by wanglong on 17/3/29.
 */

public class SpecialInfo {

    public interface ViewType {
        /**
         * banner
         */
        int BANNER = 0;

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
