package com.xianglian.love.manager;

import com.orhanobut.hawk.Hawk;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.wl.appchat.model.FriendshipInfo;
import com.wl.appchat.model.GroupInfo;
import com.wl.appchat.model.UserInfo;
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
        Hawk.put(Keys.TOKEN, null);
        Hawk.put(Keys.USER_TIM_SIGN, null);
        Hawk.put(Keys.SEARCH_INFO_LIST, null);
        logout();
    }

    private void setCurrentUser(UserEntity user) {

    }

    private void logout() {
        TlsBusiness.logout(UserInfo.getInstance().getId());
        UserInfo.getInstance().setId(null);
        MessageEvent.getInstance().clear();
        FriendshipInfo.getInstance().clear();
        GroupInfo.getInstance().clear();
    }
}
