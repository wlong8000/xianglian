package com.xianglian.love.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.xianglian.love.BaseActivity;
import com.xianglian.love.MainActivity;
import com.xianglian.love.R;
import com.xianglian.love.config.Keys;
import com.xianglian.love.main.home.been.PersonInfo;

/**
 * 性别选择
 */
public class SelectSexActivity extends BaseActivity implements View.OnClickListener {

    public static Intent getIntent(Context context) {
        return new Intent(context, SelectSexActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sex);
        setupCommonTitle(getString(R.string.selected_sex));
        mTitleBarView.setupLeftImg(0);

        initView();
    }

    private void initView() {
        findViewById(R.id.select_sex_of_men).setOnClickListener(this);
        findViewById(R.id.select_sex_of_women).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_sex_of_men: {
                intoPersonInfo(PersonInfo.Sex.MAN);
                break;
            }
            case R.id.select_sex_of_women: {
                intoPersonInfo(PersonInfo.Sex.WOMAN);
                break;
            }

        }
    }

    private void intoPersonInfo(String sex) {
        Hawk.put(Keys.SEX, sex);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
