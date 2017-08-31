package com.xianglian.love.user;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.callback.Callback;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.utils.MyLog;
import com.xianglian.love.utils.UserUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResetPwdActivity extends BaseLoginActivity implements View.OnClickListener{

    private EditText mPasswordView;

    private EditText mNewPasswordView;

    private EditText mRePasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        setupTitle(getString(R.string.reset_pwd));

        mPasswordView = (EditText) findViewById(R.id.password_old);
        mRePasswordView = (EditText) findViewById(R.id.re_password);
        mNewPasswordView = (EditText) findViewById(R.id.password_new);

        findViewById(R.id.reset_pwd_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reset_pwd_btn:
                resetPwd(getText(mPasswordView), getText(mNewPasswordView), getText(mRePasswordView));
                break;
        }
    }

    private void resetPwd(String oldPwd, String newPwd, String rePwd) {
        if (isEmpty(oldPwd) || isEmpty(newPwd)) {
            toast(R.string.pwd_no_empty);
            return;
        }
        if (isEmpty(rePwd) || !newPwd.equals(rePwd)) {
            toast(getString(R.string.confirm_twice_pwd_sample));
            return;
        }

        String url = Config.PATH + "user/reset/password";
        Map<String, String> params = new HashMap<>();
        params.put("mobile", UserUtils.getPhone(this));
        params.put("old_pwd", oldPwd);
        params.put("password", newPwd);
        params.put("password1", rePwd);
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
                        finish();
                    }
                });

    }
}
