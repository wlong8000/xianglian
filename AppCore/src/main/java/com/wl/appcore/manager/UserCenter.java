package com.wl.appcore.manager;

import com.orhanobut.hawk.Hawk;
import com.wl.appcore.Keys;
import com.wl.appcore.entity.UserEntity;

/**
 * Created by wanglong on 2018/7/31.
 */

public class UserCenter {
    private static final class Holder {
        private static final UserCenter instance = new UserCenter();
    }

    public static UserCenter getDefault() {
        return Holder.instance;
    }

    public void notifyLoginOk(UserEntity user) {
        Hawk.put(Keys.USER_INFO, user);
        Hawk.put(Keys.SEX, user.getGender());
    }

    public void notifyLogout() {
        Hawk.put(Keys.USER_INFO, null);
//        Hawk.put(Keys.SEX, null);
        Hawk.put(Keys.TOKEN, null);
    }

    private void setCurrentUser(UserEntity user) {

    }
}
