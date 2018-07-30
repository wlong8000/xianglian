package com.xianglian.love.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.orhanobut.hawk.Hawk;
import com.tencent.TIMCallBack;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.wl.appchat.TimHelper;
import com.wl.appcore.entity.UserEntity;
import com.wl.appcore.event.MessageEvent2;
import com.xianglian.love.AppService;
import com.xianglian.love.MainActivity;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.wl.appcore.Keys;
import com.xianglian.love.net.JsonCallBack;
import com.xianglian.love.utils.AppUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends BaseLoginActivity implements OnClickListener, TIMCallBack {

    private AutoCompleteTextView mUserNameView;

    private EditText mPasswordView;

    public static final String EXTRA_USER_NAME = "userName";

    public static final String EXTRA_PWD = "pwd";

    public static final String EXTRA_AUTO_LOGIN = "auto_Login";

    private UserEntity mEntity;

    private String mToken;

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
        EventBus.getDefault().register(this);
        setupTitle(getString(R.string.login), getString(R.string.register));

        mUserNameView = findViewById(R.id.user_name);
        mPasswordView = findViewById(R.id.password);
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
            loginRequest(userName, pwd);
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
        String url = Config.PATH + "login/";
        HttpParams params = new HttpParams();
        params.put("username", userName);
        params.put("password", passWord);

        PostRequest<UserEntity> request = OkGo.post(url);
        request.params(params);
        request.headers("Authorization", AppUtils.getToken(this));
        request.execute(new JsonCallBack<UserEntity>(UserEntity.class) {
            @Override
            public void onSuccess(Response<UserEntity> response) {
                UserEntity entity = response.body();
                if (entity == null) return;
                Hawk.put(Keys.TOKEN, entity.getToken());
                mToken = entity.getToken();
                AppService.startSaveUser(LoginActivity.this, true);
            }

            @Override
            public void onError(Response<UserEntity> response) {
                super.onError(response);
                dialogDisMiss();
            }
        });
    }

    @Override
    public void rightClick() {
        startActivity(new Intent(LoginActivity.this, RegisterCodeActivity.class));
    }

    @Override
    public void onError(int i, String s) {

    }

    @Override
    public void onSuccess() {
        dialogDisMiss();
        TimHelper.getInstance().initMessage();
        Hawk.put(Keys.TOKEN, mToken);
        Hawk.put(Keys.USER_INFO, mEntity);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMessage2(MessageEvent2 messageEvent) {
        UserEntity entity = messageEvent.getMessage();
        if (messageEvent.getType() == 0) {
            LoginBusiness.loginIm(entity.getUsername(), entity.getUser_sign(), this);
        } else if (messageEvent.getType() == 1) {
            String username = entity.getId() + "-" + entity.getUsername();
            mEntity = entity;
            AppService.startUpdateTimSign(LoginActivity.this, username, true);
        } else if (messageEvent.getType() == 3) {
            dialogDisMiss();
            showToast(getString(R.string.action_forbade));
        }
    }
}

