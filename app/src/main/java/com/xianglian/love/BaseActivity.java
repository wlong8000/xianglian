package com.xianglian.love;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.xianglian.love.utils.StatusbarUtil;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.MyLog;
import com.xianglian.love.view.ProgressDialog;
import com.xianglian.love.view.TitleBarView;

import java.util.Map;

public class BaseActivity extends AppCompatActivity implements TitleBarView.OnTitleClickListener {
    public static String TAG = "meetUp";

    private ProgressDialog mDialog;

    public TitleBarView mTitleBarView;

    protected String tag = this.getClass().getSimpleName();

    protected boolean debug = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        if (needTitleTransparent()) {
            AppUtils.setImmersionType(this);
        }
    }

    public boolean needTitleTransparent() {
        return true;
    }

    public void log(Object o) {
        if (debug && MyLog.DEBUG)
            Log.e(MyLog.TAG, tag + " : " + o.toString());
    }

    public void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int res) {
        Toast.makeText(this, getString(res), Toast.LENGTH_SHORT).show();
    }

    public void setupTitle(String title) {
        mTitleBarView = findViewById(R.id.title_bar_layout);
        mTitleBarView.setTitleClickListener(this);
        mTitleBarView.setTitle(title);
        if (mTitleBarView != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTitleBarView.setPadding(0, StatusbarUtil.getStatusBarHeight(this), 0, 0);
        }
    }

    public void setupTitle(String title, String rightText) {
        setupCommonTitle(title);
        mTitleBarView.setupRightText(rightText);
    }

    public void setupCommonTitle(String title) {
        setupTitle(title, 0, getResources().getColor(R.color.lib_color_font8));
    }

    public void setupTitle(String title, int rightRes, int backgroundRes) {
        setupTitle(title);
        if (backgroundRes != 0) {
            mTitleBarView.setBackgroundColor(backgroundRes);
        }
        mTitleBarView.setupLeftImg(R.drawable.return_btn_style);
        mTitleBarView.setTitle(title, R.dimen.lib_font_size2, R.color.white);
        mTitleBarView.setupRightImg(rightRes);
        mTitleBarView.setTitleClickListener(this);
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void toast(int res) {
        toast(getResources().getString(res));
    }

    private DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {

        }
    };

    public TextView getTextView(int id) {
        return (TextView) findViewById(id);
    }

    public void dialogShow() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
            mDialog.setOnCancelListener(onCancelListener);
            mDialog.setCanceledOnTouchOutside(false);
        }

        if (!mDialog.isShowing())
            mDialog.show();
    }

    public void dialogDisMiss() {
        if (mDialog != null)
            mDialog.dismiss();
    }

    public boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    public Map<String, String> getHeader() {
        return AppUtils.getOAuthMap(this);
    }

    public String getUserId(Context context) {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
