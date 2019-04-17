package com.xianglian.love;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Toast;


import com.umeng.analytics.MobclickAgent;
import com.xianglian.love.view.TitleBarView;

/**
 * Created by wanglong on 17/3/10.
 */

public class BaseFragmentActivity extends FragmentActivity implements TitleBarView.OnTitleClickListener {
    public TitleBarView mTitleBarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    public void setupTitle(String title, int rightRes) {
        mTitleBarView = findViewById(R.id.title_bar_layout);
        mTitleBarView.setBackgroundColor(getResources().getColor(R.color.lib_color_font8));
        mTitleBarView.setTitle(title, R.dimen.lib_font_size2, R.color.white);
        mTitleBarView.setupRightImg(rightRes);
        mTitleBarView.setTitleClickListener(this);
    }

    public void setupTitle(String title) {
        setupTitle(title, 0);
        mTitleBarView.setupLeftImg(R.drawable.return_btn_style);
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    public void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int res) {
        Toast.makeText(this, getString(res), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void finish() {
        super.finish();
//        this.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

}
