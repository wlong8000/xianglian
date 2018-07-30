package com.xianglian.love.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.orhanobut.hawk.Hawk;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.wl.appcore.Keys;
import com.wl.appcore.entity.UserEntity;
import com.xianglian.love.net.JsonCallBack;
import com.xianglian.love.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * 个人介绍/情感经历
 */
public class IntroduceActivity extends BaseUserInfoActivity {

    private EditText mIntroduceView;

    //个人介绍
    public static final int INTRODUCE = 0;

    //情感经历
    public static final int EXPERIENCE = 1;

    //择偶要求
    public static final int CHOOSE_FRIEND_STANDARD = 2;

    private int mType;

    private UserEntity mUserEntity;

    public static Intent getIntent(Context context, int type) {
        Intent intent = new Intent(context, IntroduceActivity.class);
        intent.putExtra(Config.INTRODUCE_KEY, type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        mIntroduceView = findViewById(R.id.et_introduce);
        mType = getIntent().getIntExtra(Config.INTRODUCE_KEY, INTRODUCE);
        mUserEntity = Hawk.get(Keys.USER_INFO);
        if (mUserEntity == null) mUserEntity = new UserEntity();
        String content = null;
        if (INTRODUCE == mType) {
            setupTitle(getString(R.string.my_introduce), getString(R.string.save));
            content = mUserEntity.getPerson_intro();
        } else if (EXPERIENCE == mType) {
            setupTitle(getString(R.string.experience_love), getString(R.string.save));
            content = mUserEntity.getRelationship_desc();
        } else if (CHOOSE_FRIEND_STANDARD == mType) {
            setupTitle(getString(R.string.condition_friend), getString(R.string.save));
            content = mUserEntity.getMate_preference();
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
        update(content);
    }

    private void update(final String input) {
        dialogShow();
        final PostRequest<UserEntity> request = OkGo.post(Config.PATH + "user_update/");
        Map<String, String> params = new HashMap<>();
        params.put("uid", getUserId(this));
        switch (mType) {
            case INTRODUCE:
                params.put("person_intro", input);
                break;
            case EXPERIENCE:
                params.put("relationship_desc", input);
                break;
            case CHOOSE_FRIEND_STANDARD:
                params.put("mate_preference", input);
                break;
        }
        request.headers("Authorization", AppUtils.getToken(this));
        request.params(params);
        request.execute(new JsonCallBack<UserEntity>(UserEntity.class) {
            @Override
            public void onSuccess(Response<UserEntity> response) {
                dialogDisMiss();
                showToast("修改成功");
                switch (mType) {
                    case INTRODUCE:
                        mUserEntity.setPerson_intro(input);
                        break;
                    case EXPERIENCE:
                        mUserEntity.setRelationship_desc(input);
                        break;
                    case CHOOSE_FRIEND_STANDARD:
                        mUserEntity.setMate_preference(input);
                        break;
                }
                Hawk.put(Keys.USER_INFO, mUserEntity);
                finish();
            }
        });
    }
}
