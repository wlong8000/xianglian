package com.wl.lianba.main.home.been;


/**
 * Created by wanglong on 17/3/12.
 * 相册item
 */

public class PhotoInfo {
    /**
     * 路径
     */
    private String photo_url;


    private int viewType;

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
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
