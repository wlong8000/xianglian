package com.wl.lianba.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.wl.lianba.MainActivity;
import com.wl.lianba.R;
import com.wl.lianba.config.Config;
import com.wl.lianba.utils.ACache;
import com.wl.lianba.utils.AppSharePreferences;
import com.wl.lianba.utils.AppUtils;
import com.okhttplib.callback.Callback;
import com.wl.lianba.utils.MyLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends BaseLoginActivity implements OnClickListener {

    private AutoCompleteTextView mUserNameView;

    private EditText mPasswordView;

    public static final String EXTRA_USER_NAME = "userName";

    public static final String EXTRA_PWD = "pwd";

    public static final String EXTRA_AUTO_LOGIN = "auto_Login";

    public static Intent getIntent(Context context, String userName, String pwd, boolean autoLogin) {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd)) return null;
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        intent.putExtra(EXTRA_PWD, pwd);
        intent.putExtra(EXTRA_AUTO_LOGIN, autoLogin);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupTitle(getString(R.string.login), getString(R.string.register));

        mUserNameView = (AutoCompleteTextView) findViewById(R.id.user_name);
        mPasswordView = (EditText) findViewById(R.id.password);
        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
        autoLogin();
    }

    private void autoLogin() {
        Intent intent = getIntent();
        boolean autoLogin = intent.getBooleanExtra(EXTRA_AUTO_LOGIN, false);
        if (autoLogin) {
            String userName = intent.getStringExtra(EXTRA_USER_NAME);
            String pwd = intent.getStringExtra(EXTRA_PWD);
            doLogin(userName, pwd);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                loginRequest(getText(mUserNameView), getText(mPasswordView));
                break;
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, ForgetPwdActivity.class));
                break;
        }
    }

    private void loginRequest(String username, String password) {
        if (!AppUtils.isNetworkAvailable(getApplicationContext())) {
            toast(getString(R.string.network_disable));
            return;
        }
        if (TextUtils.isEmpty(username)) {
            toast(getString(R.string.username_no_empty));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            toast(getString(R.string.pwd_no_empty));
            return;
        }
        dialogShow();
        doLogin(username, password);

    }

    private void doLogin(String userName, String passWord) {
        String url = Config.PATH + "user/login";
        Map<String, String> params = new HashMap<>();
        params.put("mobile", userName);
        params.put("password", passWord);
        OkHttpUtil.getDefault(this).doPostAsync(
                HttpInfo.Builder().setUrl(url).addHeads(getHeader()).addParams(params).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        dialogDisMiss();
                        String result = info.getRetDetail();
                        MyLog.d("TAG", "&& login fail= " + result);
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        dialogDisMiss();
                        String result = info.getRetDetail();
                        MyLog.d("TAG", "&& login success= " + result);
                        ACache.get(LoginActivity.this).put(Config.KEY_USER, result);
                        boolean isEditInfo = AppSharePreferences.getBoolValue(LoginActivity.this, AppSharePreferences.USER_INFO);
                        if (isEditInfo) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            startActivity(new Intent(LoginActivity.this, UserInfoEditActivity.class));
                        }
                        finish();
                    }
                });
    }

    @Override
    public void rightClick() {
        startActivity(new Intent(LoginActivity.this, RegisterCodeActivity.class));
    }
}

