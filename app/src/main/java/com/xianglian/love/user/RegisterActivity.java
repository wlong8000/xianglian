package com.xianglian.love.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.callback.Callback;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.MyLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanglong on 17/3/2.
 * 注册
 */

public class RegisterActivity extends BaseLoginActivity implements View.OnClickListener {
    private AutoCompleteTextView mUserNameView;

    private EditText mPasswordView, mRePasswordView;

    public static final String KEY_PHONE = "phone";

    public static final String KEY_CODE = "code";

    private String mPhone;

    private String mCode;

    public static Intent getIntent(Context context, String phone, String code) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(KEY_PHONE, phone);
        intent.putExtra(KEY_CODE, code);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        setupTitle(getString(R.string.register));

        mUserNameView = (AutoCompleteTextView) findViewById(R.id.user_name);
        mPasswordView = (EditText) findViewById(R.id.password);
        mRePasswordView = (EditText) findViewById(R.id.re_password);
        findViewById(R.id.register_button).setOnClickListener(this);
        mPhone = getIntent().getStringExtra(KEY_PHONE);
        mCode = getIntent().getStringExtra(KEY_CODE);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_button:
                registerRequest(getText(mUserNameView), getText(mPasswordView), getText(mRePasswordView));
                break;
        }
    }

    private void registerRequest(String username, String pwd, String pwd2) {
        if (!AppUtils.isNetworkAvailable(getApplicationContext())) {
            toast(getString(R.string.network_disable));
            return;
        }
        if (TextUtils.isEmpty(username)) {
            toast(getString(R.string.username_no_empty));
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            toast(getString(R.string.pwd_no_empty));
            return;
        }
        if (TextUtils.isEmpty(pwd2) || !pwd.equals(pwd2)) {
            toast(getString(R.string.confirm_twice_pwd_sample));
            return;
        }
        doRegister(username, pwd);
    }

    private void doRegister(final String userName, final String passWord) {
        String url = Config.PATH + "users/";
        Map<String, String> params = new HashMap<>();
        params.put("username", userName);
        params.put("code", mCode);
        params.put("mobile", mPhone);
        params.put("password", passWord);
        OkHttpUtil.getDefault(this).doPostAsync(
                HttpInfo.Builder().setUrl(url).addParams(params).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        dialogDisMiss();
                        String result = info.getRetDetail();
                        MyLog.d("TAG", "&& doRegister fail= " + result);
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        dialogDisMiss();
                        MyLog.d("TAG", "&& doRegister success = " + info.getRetDetail());
                        Intent intent = LoginActivity.getIntent(RegisterActivity.this, userName, passWord, true);
                        if (intent != null) startActivity(intent);
                        finish();
                    }
                });
    }
}
