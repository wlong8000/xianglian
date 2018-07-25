package com.wl.appcore.event;

import com.wl.appcore.entity.UserEntity;

/**
 * Created by wanglong on 2018/7/24.
 */

public class MessageEvent2 {
    private UserEntity message;
    private int type;

    public MessageEvent2(UserEntity message, int type) {
        this.message = message;
        this.type = type;
    }

    public UserEntity getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setMessage(UserEntity message) {
        this.message = message;
    }
}
