package com.wl.appcore.event;

import com.wl.appcore.entity.UserEntity;

/**
 * Created by wanglong on 2018/7/24.
 */

public class MessageEvent2 {
    private UserEntity message;

    public MessageEvent2(UserEntity message) {
        this.message = message;
    }

    public UserEntity getMessage() {
        return message;
    }

    public void setMessage(UserEntity message) {
        this.message = message;
    }
}
