package com.xianglian.love.main.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.wl.appcore.utils.AppUtils2;
import com.xianglian.love.BaseActivity;
import com.xianglian.love.MainActivity;
import com.xianglian.love.R;
import com.xianglian.love.manager.UserCenter;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.view.TitleBarView;

import base.GuideDialog;
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
//        String text = AppUtils2.isCompleteData();
//        if (!TextUtils.isEmpty(text)) {
//            showToast(text);
//        }
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        baseMeFragment = BaseMeFragment.newInstance(BaseMeFragment.TYPE_FROM_LOGIN);
        ft.add(R.id.container, baseMeFragment);
        ft.commitAllowingStateLoss();

        GuideDialog dialog = new GuideDialog(this);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("1、恭喜您，注册成功，欢迎加入<享恋>大家庭\n2、本APP为免费APP，没有会员机制，不收取任何费用\n3、我们欢迎真正找对象的您，发广告，有其他目的的用户，零容忍" +
                "\n4、认真填写个人资料，否则无法查看其他人的资料\n5、填写资料大概需要3-8分钟，请耐心，您的这几分钟，可以是您步入婚礼殿堂的开始");

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
            startActivity(MainActivity.getIntent(this));
            finish();
            return;
        }
        OkDialog okDialog = new OkDialog(this) {
            @Override
            public void onConfirm(String result) {
                startActivity(MainActivity.getIntent(UserInfoEditActivity.this));
                finish();
            }
        };
        okDialog.show();
        okDialog.setTitle("如果要查看别人资料，请填写完整个人信息，当前信息填写不完整，确定退出？");
    }
}
