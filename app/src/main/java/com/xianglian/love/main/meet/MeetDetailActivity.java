package com.xianglian.love.main.meet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.xianglian.love.BaseListActivity;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.main.meet.adapter.Meet2Adapter;
import com.xianglian.love.main.meet.model.MeetInfo;
import com.xianglian.love.net.JsonCallBack;
import com.xianglian.love.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class MeetDetailActivity extends BaseListActivity {
    private Meet2Adapter mAdapter;

    private int mId;

    private List<MeetInfo> meetInfoList = new ArrayList<>();

    public static Intent getIntent(Context context, int id) {
        Intent intent = new Intent(context, MeetDetailActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_detail);
        setupCommonTitle("活动详情");
        mId = getIntent().getIntExtra("id", 0);
        setupRecyclerView();
    }

    public void setupRecyclerView() {
        super.setupRecyclerView();
        mAdapter = new Meet2Adapter(meetInfoList);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh(true);
    }

    @Override
    public void onRefresh(final boolean refresh) {
        final String url = Config.PATH + "meet_action/" + mId;
        final GetRequest<MeetInfo> request = OkGo.get(url);
        request.headers("Authorization", AppUtils.getToken(this));

        request.execute(new JsonCallBack<MeetInfo>(MeetInfo.class) {
            @Override
            public void onSuccess(Response<MeetInfo> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setEnabled(false);
                if (response != null && response.body() != null) {
                    MeetInfo meetInfo = response.body();
                    if (meetInfo == null) return;
                    if (refresh) meetInfoList.clear();
                    addData(meetInfo);
                }
            }

            @Override
            public void onError(Response<MeetInfo> response) {
                super.onError(response);
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setEmptyView(errorView);
            }
        });
    }

    private void addData(MeetInfo meetInfo) {
        addDataByType(MeetInfo.ViewType.HEADER, meetInfo);
        addDataByType(MeetInfo.ViewType.ITEM, meetInfo);
    }

    private void addDataByType(int type, MeetInfo entity) {
        MeetInfo meetInfo;
        switch (type) {
            case MeetInfo.ViewType.HEADER:
                meetInfo = new MeetInfo();
                meetInfo.setViewType(type);
                meetInfo.setUrl(entity.getUrl());
                meetInfo.setTheme(entity.getTheme());
                meetInfoList.add(meetInfo);
                break;
            case MeetInfo.ViewType.ITEM:
                addItem(entity);
                break;
        }
    }

    private void addItem(MeetInfo entity) {
        if (!TextUtils.isEmpty(entity.getMeet_time())) {
            MeetInfo meetInfo = new MeetInfo();
            meetInfo.setViewType(MeetInfo.ViewType.ITEM);
            meetInfo.setRes(R.drawable.icon_u_name);
            meetInfo.setContent(entity.getMeet_time());
            meetInfoList.add(meetInfo);
        }

        if (!TextUtils.isEmpty(entity.getPlace_detail())) {
            MeetInfo meetInfo = new MeetInfo();
            meetInfo.setViewType(MeetInfo.ViewType.ITEM);
            meetInfo.setRes(R.drawable.icon_u_name);
            meetInfo.setContent(entity.getPlace_detail());
            meetInfoList.add(meetInfo);
        }

        if (!TextUtils.isEmpty(entity.getPrice())) {
            MeetInfo meetInfo = new MeetInfo();
            meetInfo.setViewType(MeetInfo.ViewType.ITEM);
            meetInfo.setRes(R.drawable.icon_u_name);
            meetInfo.setContent(entity.getPrice());
            meetInfoList.add(meetInfo);
        }

        if (!TextUtils.isEmpty(entity.getMobile())) {
            MeetInfo meetInfo = new MeetInfo();
            meetInfo.setViewType(MeetInfo.ViewType.ITEM);
            meetInfo.setRes(R.drawable.icon_u_name);
            meetInfo.setContent(entity.getMobile());
            meetInfoList.add(meetInfo);
        }
    }
}
