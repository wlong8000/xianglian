package com.xianglian.love.main.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.xianglian.love.BaseActivity;
import com.xianglian.love.R;

public class UserInfoEditActivity extends BaseActivity {

    FragmentManager fragmentManager;

    BaseMeFragment baseMeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_edit);
        setupCommonTitle("填写个人资料");
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        baseMeFragment = BaseMeFragment.newInstance(BaseMeFragment.TYPE_FROM_LOGIN);
        ft.add(R.id.container, baseMeFragment);
        ft.commitAllowingStateLoss();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (baseMeFragment != null) {
            baseMeFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

}
