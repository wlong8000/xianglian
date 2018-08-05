package com.xianglian.love.main.meet;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.wl.appcore.utils.AppUtils2;
import com.xianglian.love.BaseListFragment;
import com.xianglian.love.MainActivity;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.loadmore.CustomLoadMoreView;
import com.xianglian.love.main.meet.adapter.Meet2Adapter;
import com.xianglian.love.main.meet.model.MeetInfo;
import com.xianglian.love.net.JsonCallBack;
import com.xianglian.love.user.LoginActivity;
import com.xianglian.love.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglong on 17/3/11.
 * 聚会
 */

public class BaseMeetFragment extends BaseListFragment implements BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.OnItemChildClickListener {

    private Meet2Adapter mAdapter;

    private List<MeetInfo> meetInfoList = new ArrayList<>();

    public static BaseMeetFragment newInstance() {
        return new BaseMeetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment, null);
        setupRecyclerView(view);
        return view;
    }

    public void setupRecyclerView(View view) {
        super.setupRecyclerView(view);
        mAdapter = new Meet2Adapter(meetInfoList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                onRefresh2(false);
            }
        }, mRecyclerView);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefresh2(true);
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh2(true);
    }

    @Override
    public void onRefresh2(final boolean refresh) {
        String url;
        if (!refresh && TextUtils.isEmpty(mNextUrl)) {
            return;
        }
        if (!refresh && !TextUtils.isEmpty(mNextUrl)) {
            url = mNextUrl;
        } else {
            url = Config.PATH + "meet_action/";
        }
        GetRequest<MeetInfo> request = OkGo.get(url);
        request.headers("Authorization", AppUtils.getToken(getContext()));
        request.execute(new JsonCallBack<MeetInfo>(MeetInfo.class) {
            @Override
            public void onSuccess(Response<MeetInfo> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                MeetInfo entity = response.body();
                if (entity == null || entity.getResults() == null) {
                    mAdapter.setNewData(null);
                    mAdapter.setEmptyView(emptyView);
                    return;
                }
                mNextUrl = entity.getNext();
                if (refresh) {
                    mAdapter.setNewData(entity.getResults());
                } else {
                    mAdapter.addData(entity.getResults());
                }
                if (TextUtils.isEmpty(mNextUrl)) {
                    mAdapter.loadMoreEnd(refresh);
                } else {
                    mAdapter.loadMoreComplete();
                }
            }

            @Override
            public void onError(Response<MeetInfo> response) {
                super.onError(response);
                mSwipeRefreshLayout.setRefreshing(false);
//                showToast(getResources().getString(R.string.please_check_network));
//                addData(null);
            }
        });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (!AppUtils.isLogin(getContext())) {
            startActivity(LoginActivity.getIntent(getContext()));
            return;
        } else if (!TextUtils.isEmpty(AppUtils2.isCompleteData())) {
//            startActivity(MainActivity.getIntent(getContext(), MainActivity.TAB_MY));
            if (getActivity() != null && getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).showToast(AppUtils2.isCompleteData());
            }
            return;
        }
    }
}
