package com.wl.lianba.user;

import android.os.Bundle;

import com.wl.lianba.BaseActivity;
import com.wl.lianba.main.home.been.PersonInfo;
import com.wl.lianba.utils.AppUtils;


/**
 * Created by wanglong on 17/3/13.
 * 用户信息基类
 */

public class BaseUserInfoActivity extends BaseActivity {
    protected PersonInfo mPersonInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (mPersonInfo == null) {
//            mPersonInfo = AppUtils.getPersonInfo(this);
//            if (mPersonInfo.getOwner() == null) {
//                MyUser user = BmobUser.getCurrentUser(MyUser.class);
//                mPersonInfo.setOwner(user);
//            }
//        }

    }

    public void setupTitle(int res) {
        super.setupTitle(getString(res));
    }
}
