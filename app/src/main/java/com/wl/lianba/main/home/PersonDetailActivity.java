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
import com.wl.lianba.utils.AppUtils;
import com.wl.lianba.utils.CommonLinearLayoutManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PersonDetailActivity extends BaseActivity implements View.OnClickListener{

    private RecyclerView mRecyclerView;

    private CommonLinearLayoutManager mLayoutManager;

    private PersonDetailAdapter mAdapter;

    //头图
    @InjectView(R.id.img)
    SimpleDraweeView mPicView;

    //心动数量
    @InjectView(R.id.tv_heart_count)
    TextView mHeartCountView;

    //心动数量
    @InjectView(R.id.heart)
    LinearLayout mHeartLayout;

    private String mId;

    public static Intent getIntent(Context context, String id) {
        Intent intent = new Intent(context, PersonDetailActivity.class);
        intent.putExtra(Config.EXTRA_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        StatusbarUtil.setStatusBarTranslucent(this);
        ButterKnife.inject(this);
        mId = getIntent().getStringExtra(Config.EXTRA_ID);
        setupView();
        doRequest(true);
    }

    private void setupView() {
        mHeartLayout.setOnClickListener(this);
        FrameLayout banner = (FrameLayout) findViewById(R.id.layout_banner);
        float height = getResources().getDisplayMetrics().widthPixels;
        banner.setLayoutParams(new CollapsingToolbarLayout.LayoutParams(
                CollapsingToolbarLayout.LayoutParams.MATCH_PARENT, Math
                .round(height)));
        mPicView.setAspectRatio(1);
//        mPicView.setImageURI(AppUtils.parse(mPersonInfo.getAvatar()));

        setupRecyclerView();

    }

    private void setHeader(UserDetailEntity entity) {
        if (entity == null) return;
//        mPicView.setImageURI(AppUtils.parse(entity.getAvatar()));
        mPicView.setImageURI("http://img0.imgtn.bdimg.com/it/u=4128355576,3453965016&fm=214&gp=0.jpg");
        mHeartCountView.setText(AppUtils.stringToInt(entity.getLike()) + "");
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new CommonLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new PersonDetailAdapter(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

//    private void addData() {
//        addDataByType(PersonInfo.ViewType.TOP_INFO);
//        addDataByType(PersonInfo.ViewType.INTRODUCE);
//        addDataByType(PersonInfo.ViewType.ALBUM);
//        addDataByType(PersonInfo.ViewType.BASE_INFO);
//        addDataByType(PersonInfo.ViewType.MARK);
//        addDataByType(PersonInfo.ViewType.EXPERIENCE_EMOTION);
//        addDataByType(PersonInfo.ViewType.FAVORITE);
//    }

//    private void addDataByType(int type) {
//        switch (type) {
//            case PersonInfo.ViewType.TOP_INFO: {
//                PersonInfo info = new PersonInfo();
//                info.setViewType(type);
//                mAdapter.getInfo().add(info);
//                break;
//            }
//            case PersonInfo.ViewType.INTRODUCE: {
//                PersonInfo info = new PersonInfo();
//                info.setIntroduce(mPersonInfo.getIntroduce());
//                info.setViewType(type);
//                mAdapter.getInfo().add(info);
//                break;
//            }
//            case PersonInfo.ViewType.EXPERIENCE_EMOTION: {
//                PersonInfo info = new PersonInfo();
//                info.setExperience(mPersonInfo.getExperience());
//                info.setViewType(type);
//                mAdapter.getInfo().add(info);
//                break;
//            }
//            case PersonInfo.ViewType.FAVORITE: {
//                PersonInfo info = new PersonInfo();
//                info.setViewType(type);
//                info.setHobby(mPersonInfo.getHobby());
//                mAdapter.getInfo().add(info);
//                break;
//            }
//            case PersonInfo.ViewType.MARK: {
//                PersonInfo info = new PersonInfo();
//                info.setViewType(type);
//                info.setMark(mPersonInfo.getMark());
//                mAdapter.getInfo().add(info);
//                break;
//            }
//            case PersonInfo.ViewType.ALBUM: {
//                PersonInfo info = new PersonInfo();
//                info.setViewType(type);
//                info.setAlbum(mPersonInfo.getAlbum());
//                mAdapter.getInfo().add(info);
//                break;
//            }
//            case PersonInfo.ViewType.BASE_INFO: {
//                PersonInfo info = new PersonInfo();
//                info.setViewType(type);
//                mAdapter.getInfo().add(info);
//                break;
//            }
//        }
//    }

    private void doRequest(final boolean refresh) {
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
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void doLikeRequest() {
        final String url = Config.PATH + "user/like/" + mId;
        Map<String, String> params = new HashMap<>();
        params.put("uid", mId);
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
                                String num = mHeartCountView.getText().toString();
                                if (userEntity.getCode() == 0) {
                                    mHeartCountView.setText((AppUtils.stringToInt(num) + 1) + "");
                                } else if (userEntity.getCode() ==1) {
                                    mHeartCountView.setText((AppUtils.stringToInt(num) - 1) + "");
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
                doLikeRequest();
                break;
        }
    }
}
