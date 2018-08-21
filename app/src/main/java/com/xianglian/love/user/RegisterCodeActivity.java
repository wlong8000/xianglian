package com.xianglian.love.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.wl.appcore.utils.AppUtils2;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

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

    private Handler mHandler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

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
        if (AppUtils.isLogin(this)) {
            finish();
            return;
        }
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
                finish();
                break;
            case R.id.password_code:
                getSmsCode();
                break;
        }
    }

    private void getSmsCode() {
        if (TextUtils.isEmpty(getText(mPhoneView))) {
            toast(R.string.phone_null);
            return;
        }
        if (!AppUtils2.isMobileNO(getText(mPhoneView))) {
            toast(R.string.phone_error);
            return;
        }
        getCode(getText(mPhoneView));
    }

    /**
     * 获取验证码
     */
    private void getCode(final String phone) {
        mHandler.post(runnable);

        String url = Config.PATH + "code/";
        HttpParams params = new HttpParams();
        params.put("mobile", phone);

        PostRequest<String> request = OkGo.post(url);
        request.params(params);
        request.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                dialogDisMiss();
                String result = response.body();
                try {
                    JSONObject object = new JSONObject(result);
                    int code = -1;
                    if (object.has("sms_code")) {
                        showToast(object.getString("sms_code"));
                        return;
                    }
                    if (object.has("code")) {
                        code = object.getInt("code");
                    } else if (object.has("mobile")) {
                        object = object.getJSONObject("mobile");
                        code = object.getInt("code");
                    }
                    if (code != 0) {
                        showToast(object.getString("msg"));
                        mHandler.removeCallbacks(runnable);
                        resetRunnable();
                        enableVerify();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                dialogDisMiss();
                mHandler.removeCallbacks(runnable);
                resetRunnable();
                enableVerify();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        mHandler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runnable);
    }
}
