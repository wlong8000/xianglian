package com.wl.appcore;

/**
 * Created by wanglong on 18/7/8.
 */

public class Keys {
    /**
     * 用户信息 UserEntity
     */
    public static String USER_INFO = "h_user_info";

    /**
     * 筛选信息 List<ItemInfo>
     */
    public static String SEARCH_INFO_LIST = "h_search_info_list";

    /**
     * 性别 String
     */
    public static String SEX = "h_sex";

    /**
     * token String
     */
    public static String TOKEN = "h_token";

    public static String TYPE_FEMALE = "female";

    public static String TYPE_MALE = "male";

    /**
     * 云通信信息 UserEntity
     */
    public static String USER_TIM_SIGN = "h_user_tim_sign";

    /**
     * config信息 ConfigEntity
     */
    public static String CONFIG_INFO = "h_user_config_info";

    /**
     * 地理位置 String
     */
    public static String LOCATION = "h_location";

    public interface TimKeys {
        String NICK_NAME = "tim_nick_name_flag";
        String FACE_URL = "tim_face_url_flag";
        String USER_ID = "tim_user_id_flag";
        /**
         * 用户自己的聊天资料 UserEntity
         */
        String USER_INFO = "tim_user_info";
    }
}
