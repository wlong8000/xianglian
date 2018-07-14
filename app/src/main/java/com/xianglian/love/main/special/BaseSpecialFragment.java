package com.xianglian.love.main.special;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xianglian.love.BaseListFragment;
import com.xianglian.love.R;
import com.xianglian.love.main.special.adapter.SpecialAdapter;
import com.xianglian.love.main.special.model.SpecialInfo;
import com.xianglian.love.utils.CommonLinearLayoutManager;

/**
 * Created by wanglong on 17/3/11.
 * 专刊
 */

public class BaseSpecialFragment extends BaseListFragment {

    private RecyclerView mRecyclerView;

    private CommonLinearLayoutManager mLayoutManager;

    private SpecialAdapter mAdapter;

    private SpecialInfo mEntity;

    private View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = mRecyclerView.getChildAdapterPosition(view);
            mEntity = mAdapter.getItem(position);

        }
    };


    public static BaseSpecialFragment newInstance() {
        BaseSpecialFragment fragment = new BaseSpecialFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.special_fragment_layout, null);
        setupRecyclerView(view);
        return view;
    }

    public void setupRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new CommonLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SpecialAdapter(getContext(), itemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        addData();
    }

    @Override
    public void onRefresh2(boolean refresh) {

    }

    private void addData() {
        addDataByType(SpecialInfo.ViewType.BANNER);
        addDataByType(SpecialInfo.ViewType.ARTICLE);
        mAdapter.notifyDataSetChanged();
    }

    private void addDataByType(int type) {
        switch (type) {
            case SpecialInfo.ViewType.BANNER: {
                SpecialInfo info = new SpecialInfo();
                info.setViewType(type);
                mAdapter.getInfo().add(info);
                break;
            }
            case SpecialInfo.ViewType.ARTICLE: {
                SpecialInfo info = new SpecialInfo();
                info.setViewType(type);
                //测试用
                mAdapter.getInfo().add(info);
                mAdapter.getInfo().add(info);
                mAdapter.getInfo().add(info);
                mAdapter.getInfo().add(info);
                mAdapter.getInfo().add(info);
                break;
            }
        }

    }
}
