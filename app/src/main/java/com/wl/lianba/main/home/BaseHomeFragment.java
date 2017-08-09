package com.wl.lianba.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wl.lianba.BaseFragment;
import com.wl.lianba.R;
import com.wl.lianba.main.home.adapter.HomeAdapter;
import com.wl.lianba.main.home.been.PersonInfo;
import com.wl.lianba.multirecyclerview.MultiRecyclerView;
import com.wl.lianba.multirecyclerview.inter.OnLoadMoreListener;
import com.wl.lianba.user.SelectSexActivity;
import com.wl.lianba.utils.AppSharePreferences;
import com.wl.lianba.utils.CommonLinearLayoutManager;
import com.wl.lianba.view.EmptyView;

import java.util.List;


/**
 * Created by wanglong on 17/3/11.
 * 首页
 */

public class BaseHomeFragment extends BaseFragment {
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private MultiRecyclerView mRecyclerView;

    private CommonLinearLayoutManager mLayoutManager;

    private HomeAdapter mAdapter;

    private int mSex;

    private EmptyView mEmptyView;

    public static BaseHomeFragment newInstance() {
        BaseHomeFragment fragment = new BaseHomeFragment();
        return fragment;
    }

    private void setupView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mRecyclerView = (MultiRecyclerView) view.findViewById(R.id.recycler_view);
        mEmptyView = (EmptyView) view.findViewById(R.id.view_list_empty_layout);
        mLayoutManager = new CommonLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new HomeAdapter(getContext());
        mRecyclerView.config(mLayoutManager, mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                request();
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                mRecyclerView.loadMoreComplete();
            }
        });

        request();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSex = AppSharePreferences.getIntValue(getContext(), AppSharePreferences.SEX);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, null);
        setupView(view);
        return view;
    }

    private void request() {
        if (mSex < 0) {
            startActivity(new Intent(getActivity(), SelectSexActivity.class));
            getActivity().finish();
            return;
        }
//        BmobQuery<PersonInfo> query = new BmobQuery<>();
//
//        query.setLimit(20);
//        query.findObjects(new FindListener<PersonInfo>() {
//            @Override
//            public void done(List<PersonInfo> list, BmobException e) {
//                hideLoading();
//                if (list == null) {
//                    mEmptyView.onEmpty();
//                } else {
//                    mAdapter.setInfo(list);
//                    mRecyclerView.setViewState(MultiRecyclerView.ViewState.CONTENT);
//                }
//            }
//        });
    }

    private void hideLoading() {
        mEmptyView.show(false);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
