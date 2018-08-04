package com.xianglian.love.main.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.wl.appcore.utils.AppUtils2;
import com.xianglian.love.BaseActivity;
import com.xianglian.love.R;
import com.xianglian.love.manager.UserCenter;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.view.TitleBarView;

import base.OkDialog;

public class UserInfoEditActivity extends BaseActivity {

    FragmentManager fragmentManager;

    BaseMeFragment baseMeFragment;

    public static Intent getIntent(Context context) {
        return new Intent(context, UserInfoEditActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_edit);
        setupCommonTitle("填写个人资料");
        String text = AppUtils2.isCompleteData();
        if (!TextUtils.isEmpty(text)) {
            showToast(text);
        }
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        baseMeFragment = BaseMeFragment.newInstance(BaseMeFragment.TYPE_FROM_LOGIN);
        ft.add(R.id.container, baseMeFragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void leftClick() {
        showDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (baseMeFragment != null) {
            baseMeFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    private void showDialog() {
        String text = AppUtils2.isCompleteData();
        if (TextUtils.isEmpty(text)) {
            finish();
            return;
        }
        OkDialog okDialog = new OkDialog(this) {
            @Override
            public void onConfirm(String result) {
                finish();
            }
        };
        okDialog.show();
        okDialog.setTitle("如果要查看别人资料，请填写完整个人信息，当前信息填写不完整，确定退出？");
    }
}
