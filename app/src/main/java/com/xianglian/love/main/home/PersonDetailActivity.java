package com.xianglian.love.main.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONException;
import com.facebook.drawee.view.SimpleDraweeView;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.callback.Callback;
import com.xianglian.love.BaseActivity;
import com.xianglian.love.R;
import com.xianglian.love.utils.StatusbarUtil;
import com.xianglian.love.config.Config;
import com.xianglian.love.main.home.been.UserDetailEntity;
import com.xianglian.love.main.home.been.UserEntity;
import com.xianglian.love.utils.AppUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import base.EditDialog;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class PersonDetailActivity extends BaseActivity implements View.OnClickListener {

    //头图
    @InjectView(R.id.img)
    SimpleDraweeView mPicView;

    //交换QQ
    @InjectView(R.id.change_qq)
    TextView mChangeQQView;

    //交换微信
    @InjectView(R.id.change_wx)
    TextView mChangeWXView;

    //留言
    @InjectView(R.id.leave_message)
    TextView mLeaveMessageView;

    //心动数量
    @InjectView(R.id.heart)
    LinearLayout mHeartLayout;

    //心动数量
    @InjectView(R.id.tv_heart_count)
    TextView mHeartCountView;

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;

    private String mId;

    private UserEntity mEntity;

    private MyBaseAdapter mBaseAdapter;

    public static Intent getIntent(Context context, String id) {
        Intent intent = new Intent(context, PersonDetailActivity.class);
        intent.putExtra(Config.EXTRA_ID, id);
        return intent;
    }

    public static Intent getIntent(Context context, UserEntity entity) {
        Intent intent = new Intent(context, PersonDetailActivity.class);
        intent.putExtra(Config.EXTRA_ENTITY, entity);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        StatusbarUtil.setStatusBarTranslucent(this);
        ButterKnife.inject(this);
        mEntity = getIntent().getParcelableExtra(Config.EXTRA_ENTITY);
        if (mEntity == null) {
            mId = getIntent().getStringExtra(Config.EXTRA_ID);
        } else {
            mId = mEntity.getUid();
        }
        setupView();
        if (mEntity != null) {
            setHeader(mEntity);
        }
    }

    private void setupView() {
        mHeartLayout.setOnClickListener(this);
        mChangeQQView.setOnClickListener(this);
        mChangeWXView.setOnClickListener(this);
        mLeaveMessageView.setOnClickListener(this);
        mPicView.setAspectRatio(2);

        mViewPager.setOffscreenPageLimit(1);
        mBaseAdapter = new MyBaseAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mBaseAdapter);

    }

    private void setHeader(UserDetailEntity entity) {
        if (entity == null) return;
        mPicView.setImageURI(entity.getAvatar());
        mHeartCountView.setText(TextUtils.isEmpty(entity.getLike()) ? "" : entity.getLike());
    }

    private void setHeader(UserEntity entity) {
        if (entity == null) return;
        mPicView.setImageURI(entity.getPic1());
        mHeartCountView.setText(TextUtils.isEmpty(entity.getLike()) ? "" : entity.getLike());
    }

//    private void doLikeRequest(final String id) {
//        final String url = Config.PATH + "user/like/" + id;
//        Map<String, String> params = new HashMap<>();
//        params.put("uid", id);
//        OkHttpUtil.getDefault(this).doPostAsync(
//                HttpInfo.Builder().setUrl(url).addHeads(getHeader()).addParams(params).build(),
//                new Callback() {
//                    @Override
//                    public void onFailure(HttpInfo info) throws IOException {
//                        toast(getString(R.string.request_fail));
//                    }
//
//                    @Override
//                    public void onSuccess(HttpInfo info) throws IOException {
//                        String result = info.getRetDetail();
//                        if (result != null) {
//                            try {
//                                UserDetailEntity userEntity = JSON.parseObject(result, UserDetailEntity.class);
//                                if (userEntity == null) return;
//                                if (userEntity.getCode() == Config.FAIL) {
//                                    toast(TextUtils.isEmpty(userEntity.getMsg()) ?
//                                            getString(R.string.request_fail) : userEntity.getMsg());
//                                } else {
//                                    int num = AppUtils.stringToInt(mHeartCountView.getText().toString()) + 1;
//                                    mHeartCountView.setText(num + "");
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//    }

//    private void doLeaveMsgRequest(String msg) {
//        final String url = Config.PATH + "user/leave/message/" + mId;
//        Map<String, String> params = new HashMap<>();
//        params.put("uid", mId);
//        params.put("message", msg);
//        OkHttpUtil.getDefault(this).doPostAsync(
//                HttpInfo.Builder().setUrl(url).addHeads(getHeader()).addParams(params).build(),
//                new Callback() {
//                    @Override
//                    public void onFailure(HttpInfo info) throws IOException {
//                        String result = info.getRetDetail();
//
//                    }
//
//                    @Override
//                    public void onSuccess(HttpInfo info) throws IOException {
//                        String result = info.getRetDetail();
//                        if (result != null) {
//                            try {
//                                UserDetailEntity userEntity = JSON.parseObject(result, UserDetailEntity.class);
//                                if (userEntity == null) return;
//                                if (!TextUtils.isEmpty(userEntity.getMsg())) {
//                                    showToast(userEntity.getMsg());
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.heart:
//                doLikeRequest(mId);
                break;
            case R.id.change_qq:
                break;
            case R.id.change_wx:
                break;
            case R.id.leave_message:
//                showEditDialog();
                break;
        }
    }

//    private void showEditDialog() {
//        EditDialog dialog = new EditDialog(this) {
//            @Override
//            public void onConfirm(String text) {
//                doLeaveMsgRequest(text);
//            }
//        };
//        dialog.show();
//    }

    class MyBaseAdapter extends FragmentStatePagerAdapter {
        Fragment[] fragments = new Fragment[] {
                PersonDetailFragment.newInstance(mId)
        };

        PersonDetailFragment getPersionDetailFragment() {
            return (PersonDetailFragment) fragments[0];
        }

        MyBaseAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
