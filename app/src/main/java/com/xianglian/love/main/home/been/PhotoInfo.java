package com.xianglian.love.main.home.been;


/**
 * Created by wanglong on 17/3/12.
 * 相册item
 */

public class PhotoInfo {
    /**
     * 路径
     */
    private String image_url;


    private int viewType;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    /**
     * 相册type
     */
    public interface AlumViewType {
        int ALUM_ADD = 1;
        int ALUM_COMMON = 0;
    }

}
