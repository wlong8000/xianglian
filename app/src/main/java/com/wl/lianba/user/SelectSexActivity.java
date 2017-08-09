package com.wl.lianba.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wl.lianba.BaseActivity;
import com.wl.lianba.MainActivity;
import com.wl.lianba.R;
import com.wl.lianba.main.home.been.PersonInfo;
import com.wl.lianba.utils.AppSharePreferences;

/**
 * 性别选择
 */
public class SelectSexActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sex);
        setupCommonTitle(getString(R.string.selected_sex));

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

    private void intoPersonInfo(int sex) {
        Intent intent = new Intent(this, MainActivity.class);
        AppSharePreferences.saveIntValue(this, AppSharePreferences.SEX, sex);
        startActivity(intent);
        finish();
    }
}
