package com.xianglian.love.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.xianglian.love.R;

public class ForgetPwdActivity extends BaseLoginActivity implements View.OnClickListener{
    private AutoCompleteTextView mUserNameView;

    private EditText mCodeView;

    private EditText mResetPwd;

    private TextView mVerifyView;

    private static final int CYCLE = 60;

    private static final int DELAY = 1000;

    private int time = CYCLE;

    private boolean isCodeSendSuccess;

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
        setContentView(R.layout.activity_forget_pwd);
        setupTitle(getString(R.string.forgot_pwd));

        mUserNameView = (AutoCompleteTextView) findViewById(R.id.tv_phone);
        mCodeView = (EditText) findViewById(R.id.sms_code);
        mVerifyView = (TextView) findViewById(R.id.password_code);
        mResetPwd = (EditText) findViewById(R.id.password_new);
        findViewById(R.id.reset_pwd_btn).setOnClickListener(this);
        mVerifyView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reset_pwd_btn:
                if (TextUtils.isEmpty(getText(mUserNameView))){
                    toast(R.string.phone_null);
                    return;
                }
                if (TextUtils.isEmpty(getText(mCodeView))){
                    toast(R.string.sms_code_null);
                    return;
                }
                if (TextUtils.isEmpty(getText(mResetPwd))) {
                    toast(R.string.pwd_no_empty);
                    return;
                }
                if (!isCodeSendSuccess) {
                    toast(R.string.sms_code_err);
                    return;
                }
                resetPwd();
                break;
            case R.id.password_code:
                getSmsCode();
                mHandler.post(runnable);
                break;
        }
    }

    private void resetPwd() {
        //2、重置的是绑定了该手机号的账户的密码
//        addSubscription(BmobUser.resetPasswordBySMSCode(getText(mCodeView), getText(mResetPwd), new UpdateListener() {
//
//            @Override
//            public void done(BmobException e) {
//                if(e==null){
//                    toast("密码重置成功");
//                    startActivity(new Intent(ForgetPwdActivity.this, MainActivity.class));
//                    finish();
//                }else{
//                    toast("错误码："+e.getErrorCode()+",错误原因："+e.getLocalizedMessage());
//                }
//            }
//        }));
    }

    private void getSmsCode() {
        if (TextUtils.isEmpty(getText(mUserNameView))){
            toast(R.string.sms_code_null);
            return;
        }
//        BmobSMS.requestSMSCode(getText(mUserNameView), "情爱滴", new QueryListener<Integer>() {
//            @Override
//            public void done(Integer smsId, BmobException e) {
//                if (e == null) {//验证码发送成功
//                    toast("验证码发送成功，短信id：" + smsId);//用于查询本次短信发送详情
//                    isCodeSendSuccess = true;
//                } else {
//                    toast("errorCode = " + e.getErrorCode() + ",errorMsg = " + e.getLocalizedMessage());
//                    isCodeSendSuccess = false;
//                }
//            }
//        });
    }
}
