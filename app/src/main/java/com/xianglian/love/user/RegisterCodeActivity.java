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

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;

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
                Intent intent = RegisterActivity.getIntent(RegisterCodeActivity.this,
                        getText(mPhoneView), getText(mSmsCodeView));
                startActivity(intent);
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
        String url = Config.PATH + "code/";

        HttpParams params = new HttpParams();
        params.put("mobile", phone);

        PostRequest<String> request = OkGo.post(url);
        request.params(params);
        request.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                dialogDisMiss();
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                dialogDisMiss();
                mHandler.removeCallbacks(runnable);
            }
        });


//        OkHttpUtil.getDefault(this).doPostAsync(
//                HttpInfo.Builder().setUrl(url).addParams(params).addHeads(getHeader()).build(),
//                new Callback() {
//                    @Override
//                    public void onFailure(HttpInfo info) throws IOException {
//                        dialogDisMiss();
//                        mHandler.removeCallbacks(runnable);
//                    }
//
//                    @Override
//                    public void onSuccess(HttpInfo info) throws IOException {
//                        dialogDisMiss();
//                    }
//                });
    }
}
