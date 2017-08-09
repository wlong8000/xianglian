package com.wl.lianba.main.home;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wl.lianba.BaseActivity;
import com.wl.lianba.R;
import com.wl.lianba.bottomttabs.StatusbarUtil;
import com.wl.lianba.config.Config;
import com.wl.lianba.main.home.adapter.PersonDetailAdapter;
import com.wl.lianba.main.home.been.PersonInfo;
import com.wl.lianba.multirecyclerview.MultiRecyclerView;
import com.wl.lianba.utils.AppUtils;
import com.wl.lianba.utils.CommonLinearLayoutManager;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PersonDetailActivity extends BaseActivity {
    private PersonInfo mPersonInfo;

    private MultiRecyclerView mRecyclerView;

    private CommonLinearLayoutManager mLayoutManager;

    private PersonDetailAdapter mAdapter;

    //头图
    @InjectView(R.id.img)
    SimpleDraweeView mPicView;

    //心动数量
    @InjectView(R.id.tv_heart_count)
    TextView mHeartCountView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        StatusbarUtil.setStatusBarTranslucent(this);
        ButterKnife.inject(this);
        mPersonInfo = (PersonInfo) getIntent().getSerializableExtra(Config.USER_INFO_KEY);
        setupView();

        addData();
    }

    private void setupView() {
        FrameLayout banner = (FrameLayout) findViewById(R.id.layout_banner);
        float height = getResources().getDisplayMetrics().widthPixels;
        banner.setLayoutParams(new CollapsingToolbarLayout.LayoutParams(
                CollapsingToolbarLayout.LayoutParams.MATCH_PARENT, Math
                .round(height)));
        mPicView.setAspectRatio(1);
        mPicView.setImageURI(AppUtils.parse(mPersonInfo.getAvatar()));

        setupRecyclerView();

    }

    private void setupRecyclerView() {
        mRecyclerView = (MultiRecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new CommonLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new PersonDetailAdapter(this);
        mRecyclerView.config(mLayoutManager, mAdapter);

    }

    private void addData() {
        addDataByType(PersonInfo.ViewType.TOP_INFO);
        addDataByType(PersonInfo.ViewType.INTRODUCE);
        addDataByType(PersonInfo.ViewType.ALBUM);
        addDataByType(PersonInfo.ViewType.BASE_INFO);
        addDataByType(PersonInfo.ViewType.MARK);
        addDataByType(PersonInfo.ViewType.EXPERIENCE_EMOTION);
        addDataByType(PersonInfo.ViewType.FAVORITE);
    }

    private void addDataByType(int type) {
        switch (type) {
            case PersonInfo.ViewType.TOP_INFO: {
                PersonInfo info = new PersonInfo();
                info.setViewType(type);
                mAdapter.getInfo().add(info);
                break;
            }
            case PersonInfo.ViewType.INTRODUCE: {
                PersonInfo info = new PersonInfo();
                info.setIntroduce(mPersonInfo.getIntroduce());
                info.setViewType(type);
                mAdapter.getInfo().add(info);
                break;
            }
            case PersonInfo.ViewType.EXPERIENCE_EMOTION: {
                PersonInfo info = new PersonInfo();
                info.setExperience(mPersonInfo.getExperience());
                info.setViewType(type);
                mAdapter.getInfo().add(info);
                break;
            }
            case PersonInfo.ViewType.FAVORITE: {
                PersonInfo info = new PersonInfo();
                info.setViewType(type);
                info.setHobby(mPersonInfo.getHobby());
                mAdapter.getInfo().add(info);
                break;
            }
            case PersonInfo.ViewType.MARK: {
                PersonInfo info = new PersonInfo();
                info.setViewType(type);
                info.setMark(mPersonInfo.getMark());
                mAdapter.getInfo().add(info);
                break;
            }
            case PersonInfo.ViewType.ALBUM: {
                PersonInfo info = new PersonInfo();
                info.setViewType(type);
                info.setAlbum(mPersonInfo.getAlbum());
                mAdapter.getInfo().add(info);
                break;
            }
            case PersonInfo.ViewType.BASE_INFO: {
                PersonInfo info = new PersonInfo();
                info.setViewType(type);
                mAdapter.getInfo().add(info);
                break;
            }
        }
        mRecyclerView.setViewState(MultiRecyclerView.ViewState.CONTENT);
    }
}
