package com.xianglian.love.user;

import android.os.Bundle;

import com.xianglian.love.BaseActivity;
import com.xianglian.love.main.home.been.PersonInfo;


/**
 * Created by wanglong on 17/3/13.
 * 用户信息基类
 */

public class BaseUserInfoActivity extends BaseActivity {
    protected PersonInfo mPersonInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setupTitle(int res) {
        super.setupTitle(getString(res));
    }
}
