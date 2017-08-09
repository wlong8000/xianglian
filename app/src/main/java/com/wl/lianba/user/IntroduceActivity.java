package com.wl.lianba.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.wl.lianba.R;
import com.wl.lianba.config.Config;
import com.wl.lianba.utils.AppUtils;
import com.wl.lianba.view.ProgressDialog;


/**
 * 个人介绍/情感经历
 */
public class IntroduceActivity extends BaseUserInfoActivity {

    private EditText mIntroduceView;

    private ProgressDialog mDialog;

    //个人介绍
    public static final int INTRODUCE = 0;

    //情感经历
    public static final int EXPERIENCE = 1;

    private int mType;

    public static Intent getIntent(Context context, int type) {
        Intent intent = new Intent(context, IntroduceActivity.class);
        intent.putExtra(Config.INTRODUCE_KEY, type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        mIntroduceView = (EditText) findViewById(R.id.et_introduce);
        mType = getIntent().getIntExtra(Config.INTRODUCE_KEY, INTRODUCE);
        String content = null;
        if (INTRODUCE == mType) {
            setupTitle(getString(R.string.my_introduce), getString(R.string.save));
            content = AppUtils.getIntroduce(this);
        } else if (EXPERIENCE == mType) {
            setupTitle(getString(R.string.experience_love), getString(R.string.save));
            content = AppUtils.getExperience(this);
        }
        if (!TextUtils.isEmpty(content)) {
            mIntroduceView.setText(content);
        }

    }

    @Override
    public void rightClick() {
        String text = mIntroduceView.getText().toString();
        if (TextUtils.isEmpty(text)) {
            showToast(R.string.introduce_null);
        } else if (text.trim().length() < 10) {
            showToast(R.string.introduce_little);
        } else {
            updateContent(text);
        }
    }

    private void updateContent(final String content) {
        if (TextUtils.isEmpty(content)) return;
        dialogShow();

        if (INTRODUCE == mType) {
            mPersonInfo.setIntroduce(content);
        } else if (EXPERIENCE == mType) {
            mPersonInfo.setExperience(content);
        }
//
//        mPersonInfo.update(AppUtils.getObjectId(this), new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                dialogDisMiss();
//                if (e == null) {
//                    showToast(R.string.save_success);
//                    ACache.get(IntroduceActivity.this).put(Config.SAVE_USER_KEY, mPersonInfo);
//                    Intent intent = new Intent();
//                    intent.putExtra(Config.INTRODUCE_RESULT_KEY, content);
//                    setResult(RESULT_OK, intent);
//                    finish();
//                } else {
//                    showToast(R.string.save_fail);
//                }
//            }
//        });
    }

    public void dialogShow() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
            mDialog.setCanceledOnTouchOutside(false);
        }

        if (!mDialog.isShowing())
            mDialog.show();
    }

    public void dialogDisMiss() {
        if (mDialog != null)
            mDialog.dismiss();
    }
}
