package com.xianglian.love.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONException;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.xianglian.love.MainActivity;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.user.been.OwnerEntity;
import com.xianglian.love.utils.ACache;
import com.xianglian.love.utils.AppUtils;
import com.okhttplib.callback.Callback;

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

    public static Intent getIntent(Context context) {
        return new Intent(context, LoginActivity.class);
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
//        doLogin(username, password);
        doToken(username, password);

    }

    private void doToken(String userName, String passWord) {
        String url = Config.PATH + "login/";
        Map<String, String> params = new HashMap<>();
        params.put("username", userName);
        params.put("password", passWord);
        OkHttpUtil.getDefault(this).doPostAsync(
                HttpInfo.Builder().setUrl(url).addHeads(getHeader()).addParams(params).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        dialogDisMiss();
                        String result = info.getRetDetail();
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        dialogDisMiss();
                        String result = info.getRetDetail();
                        if (result == null) return;
                        OwnerEntity ownerEntity = null;
                        try {
                            ownerEntity = JSON.parseObject(result, OwnerEntity.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (ownerEntity == null) return;
                        int code = ownerEntity.getCode();
                        if (code == Config.FAIL) {
                            toast(TextUtils.isEmpty(ownerEntity.getMsg()) ? getString(R.string.request_fail) : ownerEntity.getMsg());
                            return;
                        }
                        ACache.get(LoginActivity.this).put(Config.KEY_USER, result);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                });
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
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        dialogDisMiss();
                        String result = info.getRetDetail();
                        if (result == null) return;
                        OwnerEntity ownerEntity = null;
                        try {
                            ownerEntity = JSON.parseObject(result, OwnerEntity.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (ownerEntity == null) return;
                        int code = ownerEntity.getCode();
                        if (code == Config.FAIL) {
                            toast(TextUtils.isEmpty(ownerEntity.getMsg()) ? getString(R.string.request_fail) : ownerEntity.getMsg());
                            return;
                        }
                        ACache.get(LoginActivity.this).put(Config.KEY_USER, result);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }

    @Override
    public void rightClick() {
        startActivity(new Intent(LoginActivity.this, RegisterCodeActivity.class));
    }
}

