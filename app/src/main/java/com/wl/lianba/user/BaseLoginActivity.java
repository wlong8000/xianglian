package com.wl.lianba.user;

import android.os.Bundle;
import android.widget.EditText;

import com.wl.lianba.BaseActivity;
import com.wl.lianba.R;

/**
 * Created by wanglong on 17/2/25.
 * 登录相关基类
 */

public class BaseLoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected String getText(EditText editText) {
        if (editText == null) return null;
        return editText.getText().toString();
    }

    public void setupTitle(String title) {
        setupTitle(title, null);
    }

    public void setupTitle(String title, String rightText) {
        super.setupTitle(title);
        mTitleBarView.setupLeftImg(R.drawable.return_btn_style3);
        mTitleBarView.setTitle(title,R.dimen.lib_font_size7, R.color.login_title);
        mTitleBarView.setupRightText(rightText);
    }

}
