package com.wl.lianba.config;

import android.os.Environment;

/**
 * Created by Administrator on 2017/3/9.
 */
public class Config {
    public static final String DEFAULT_MD5 = "sdfasdf!@##$%DSfg$%^(DFG>:<LKP:<?<:K";
    /**
     * 缓存路径
     */
    public static final String CACHE_PATH = Environment.getExternalStorageDirectory().getPath() + "/lianba";
    /**
     * 用户信息KEY
     */
    public static final String USER_INFO_KEY = "user_info_key";

    public static final String PATH = "http://123.206.174.249/";

    /**
     * banner
     */
    public static final int PADDING_SIZE = 5;
    public static final int TIME = 2000;
    public static final int DURATION = 800;
    public static final boolean IS_AUTO_PLAY = true;
    public static final boolean IS_SCROLL = true;
    /**
     * indicator style
     */
    public static final int NOT_INDICATOR = 0;
    public static final int CIRCLE_INDICATOR = 1;
    public static final int NUM_INDICATOR = 2;
    public static final int NUM_INDICATOR_TITLE = 3;
    public static final int CIRCLE_INDICATOR_TITLE = 4;
    public static final int CIRCLE_INDICATOR_TITLE_INSIDE = 5;
    /**
     * indicator gravity
     */
    public static final int LEFT = 5;
    public static final int CENTER = 6;
    public static final int RIGHT = 7;
    /**
     * title style
     */
    public static final int TITLE_BACKGROUND = -1;
    public static final int TITLE_HEIGHT = -1;
    public static final int TITLE_TEXT_COLOR = -1;
    public static final int TITLE_TEXT_SIZE = -1;

    public static final String INTRODUCE_RESULT_KEY = "introduce_result_key";

    public static final String INTRODUCE_KEY = "introduce_key";

    public static final String MARK_KEY = "mark_key";

    public static final String SAVE_USER_KEY = "save_user_key";

    public static final String USER_ID_KEY = "user_id_key";

    public static final String KEY_USER = "key_user";

    public static final int VISIBLE = 1;

}
