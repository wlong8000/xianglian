package com.xianglian.love.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.callback.Callback;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取验证码
 */
public class RegisterCodeActivity extends BaseLoginActivity implements View.OnClickListener {
    private AutoCompleteTextView mPhoneView;

    private EditText mSmsCodeView;

    private TextView mVerifyView;

    private static final int CYCLE = 60;

    private static final int DELAY = 1000;

    private int time = CYCLE;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mVerifyView.setText(time-- + getString(R.string.second));
            mHandler.postDelayed(runnable, DELAY);
            disableVerify();
            if (time < 0) {
                resetRunnable();
                enableVerify();
            }
        }
    };

    public void disableVerify() {
        mVerifyView.setClickable(false);
        mVerifyView.setBackgroundResource(R.color.lib_color_bg13);
    }

    public void enableVerify() {
        mVerifyView.setClickable(true);
        mVerifyView.setBackgroundResource(R.color.lib_color_bg13);
    }

    public void resetRunnable() {
        time = CYCLE;
        mVerifyView.setText(getString(R.string.get_code));
        mHandler.removeCallbacks(runnable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_code);
        setupTitle(getString(R.string.register));

        mPhoneView = (AutoCompleteTextView) findViewById(R.id.tv_phone);
        mSmsCodeView = (EditText) findViewById(R.id.sms_code);
        mVerifyView = (TextView) findViewById(R.id.password_code);
        findViewById(R.id.next_btn).setOnClickListener(this);
        mVerifyView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_btn:
                if (TextUtils.isEmpty(getText(mPhoneView))) {
                    toast(R.string.phone_null);
                    return;
                } else if (TextUtils.isEmpty(getText(mSmsCodeView))) {
                    toast(R.string.sms_code_null);
                    return;
                }
                verifyCode(getText(mPhoneView), getText(mSmsCodeView));
                break;
            case R.id.password_code:
                getSmsCode();
                mHandler.post(runnable);
                break;
        }
    }

    private void getSmsCode() {
        if (TextUtils.isEmpty(getText(mPhoneView))) {
            toast(R.string.sms_code_null);
            return;
        }
        getCode(getText(mPhoneView));
    }

    /**
     * 获取验证码
     */
    private void getCode(String phone) {
        String url = Config.PATH + "user/gen-sms-code";
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        OkHttpUtil.getDefault(this).doGetAsync(
                HttpInfo.Builder().setUrl(url).addParams(params).addHeads(getHeader()).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        dialogDisMiss();
                        mHandler.removeCallbacks(runnable);
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        dialogDisMiss();
                    }
                });
    }

    /**
     * 校验验证码
     */
    private void verifyCode(String phone, String smsCode) {
        String url = Config.PATH + "user/verify-sms-code";
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("verify_code", smsCode);
        OkHttpUtil.getDefault(this).doGetAsync(
                HttpInfo.Builder().setUrl(url).addParams(params).addHeads(getHeader()).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        dialogDisMiss();
                        toast(R.string.sms_code_err);
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        dialogDisMiss();
                        Intent intent = RegisterActivity.getIntent(RegisterCodeActivity.this, getText(mPhoneView));
                        startActivity(intent);
                    }
                });
    }

}
