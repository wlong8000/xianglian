package com.wl.lianba.main.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONException;
import com.facebook.drawee.view.SimpleDraweeView;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.callback.Callback;
import com.wl.lianba.BaseActivity;
import com.wl.lianba.R;
import com.wl.lianba.bottomttabs.StatusbarUtil;
import com.wl.lianba.config.Config;
import com.wl.lianba.main.home.adapter.PersonDetailAdapter;
import com.wl.lianba.main.home.been.UserDetailEntity;
import com.wl.lianba.main.home.been.UserEntity;
import com.wl.lianba.utils.AppUtils;
import com.wl.lianba.utils.CommonLinearLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.EditDialog;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class PersonDetailActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;

    private CommonLinearLayoutManager mLayoutManager;

    private PersonDetailAdapter mAdapter;

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

    private String mId;

    private UserEntity mEntity;

    private List<UserDetailEntity> mUserDetailEntities = new ArrayList<>();

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
    protected void onCreate(Bundle savedInstanceState) {
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
        doRequest();
    }

    private void setupView() {
        mHeartLayout.setOnClickListener(this);
        mChangeQQView.setOnClickListener(this);
        mChangeWXView.setOnClickListener(this);
        mLeaveMessageView.setOnClickListener(this);
        FrameLayout banner = (FrameLayout) findViewById(R.id.layout_banner);
        float height = getResources().getDisplayMetrics().widthPixels;
        banner.setLayoutParams(new CollapsingToolbarLayout.LayoutParams(
                CollapsingToolbarLayout.LayoutParams.MATCH_PARENT, Math
                .round(height)));
        mPicView.setAspectRatio(1);

        setupRecyclerView();

    }

    private void setHeader(UserDetailEntity entity) {
        if (entity == null) return;
        mPicView.setImageURI(entity.getAvatar());
        mHeartCountView.setText(AppUtils.stringToInt(entity.getLike()) + "");
    }

    private void setHeader(UserEntity entity) {
        if (entity == null) return;
        mPicView.setImageURI(entity.getAvatar());
        mHeartCountView.setText(entity.getLike() + "");
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new CommonLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new PersonDetailAdapter(this, mUserDetailEntities);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void addData(UserDetailEntity entity) {
        addDataByType(UserDetailEntity.ViewType.TOP_INFO, entity);
        addDataByType(UserDetailEntity.ViewType.INTRODUCE, entity);
        addDataByType(UserDetailEntity.ViewType.ALBUM, entity);
        addDataByType(UserDetailEntity.ViewType.BASE_INFO, entity);
        addDataByType(UserDetailEntity.ViewType.MARK, entity);
        addDataByType(UserDetailEntity.ViewType.EXPERIENCE_EMOTION, entity);
        addDataByType(UserDetailEntity.ViewType.FAVORITE, entity);
        addDataByType(UserDetailEntity.ViewType.TITLE, entity);
        addDataByType(UserDetailEntity.ViewType.LEAVE_MESSAGE, entity);
        mAdapter.notifyDataSetChanged();
    }

    private void addDataByType(int type, UserDetailEntity entity) {
        UserDetailEntity info = new UserDetailEntity();
        info.setViewType(type);
        info.setResult(entity);
        mUserDetailEntities.add(info);
    }

    private void doRequest() {
        final String url = Config.PATH + "user/persons/" + mId;
        OkHttpUtil.getDefault(this).doGetAsync(
                HttpInfo.Builder().setUrl(url).addHeads(getHeader()).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        String result = info.getRetDetail();

                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        String result = info.getRetDetail();
                        if (result != null) {
                            try {
                                UserDetailEntity userEntity = JSON.parseObject(result, UserDetailEntity.class);
                                if (userEntity == null || userEntity.getResult() == null) return;
                                UserDetailEntity userDetailEntity = userEntity.getResult();
                                if (userDetailEntity == null) return;
                                setHeader(userDetailEntity);
                                addData(userDetailEntity);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void doLikeRequest(final String id) {
        final String url = Config.PATH + "user/like/" + id;
        Map<String, String> params = new HashMap<>();
        params.put("uid", id);
        OkHttpUtil.getDefault(this).doPostAsync(
                HttpInfo.Builder().setUrl(url).addHeads(getHeader()).addParams(params).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        toast(getString(R.string.request_fail));
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        String result = info.getRetDetail();
                        if (result != null) {
                            try {
                                UserDetailEntity userEntity = JSON.parseObject(result, UserDetailEntity.class);
                                if (userEntity == null) return;
                                if (userEntity.getCode() == Config.FAIL) {
                                    toast(TextUtils.isEmpty(userEntity.getMsg()) ?
                                            getString(R.string.request_fail) : userEntity.getMsg());
                                } else {
                                    int num = AppUtils.stringToInt(mHeartCountView.getText().toString()) + 1;
                                    mHeartCountView.setText(num + "");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void doLeaveMsgRequest(String msg) {
        final String url = Config.PATH + "user/leave/message/" + mId;
        Map<String, String> params = new HashMap<>();
        params.put("uid", mId);
        params.put("message", msg);
        OkHttpUtil.getDefault(this).doPostAsync(
                HttpInfo.Builder().setUrl(url).addHeads(getHeader()).addParams(params).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        String result = info.getRetDetail();

                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        String result = info.getRetDetail();
                        if (result != null) {
                            try {
                                UserDetailEntity userEntity = JSON.parseObject(result, UserDetailEntity.class);
                                if (userEntity == null) return;
                                if (!TextUtils.isEmpty(userEntity.getMsg())) {
                                    showToast(userEntity.getMsg());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.heart:
                doLikeRequest(mId);
                break;
            case R.id.change_qq:
                break;
            case R.id.change_wx:
                break;
            case R.id.leave_message:
                showEditDialog();
                break;
        }
    }

    private void showEditDialog() {
        EditDialog dialog = new EditDialog(this) {
            @Override
            public void onConfirm(String text) {
                doLeaveMsgRequest(text);
            }
        };
        dialog.show();
    }
}
