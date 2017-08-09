package com.wl.lianba.main.home.been;


/**
 * Created by wanglong on 17/3/12.
 * 相册item
 */

public class PhotoInfo {
    /**
     * 路径
     */
    private String path;


    private int viewType;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        int ALUM_ADD = 0;
        int ALUM_COMMON = 1;
    }

}
