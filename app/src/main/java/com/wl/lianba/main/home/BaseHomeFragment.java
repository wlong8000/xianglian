package com.wl.lianba.main.home;

   import android.content.Intent;
   import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
   import android.text.TextUtils;
   import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONException;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.callback.Callback;
import com.wl.lianba.BaseFragment;
import com.wl.lianba.R;
import com.wl.lianba.config.Config;
import com.wl.lianba.main.home.adapter.HomeAdapter;
import com.wl.lianba.main.home.been.UserEntity;
import com.wl.lianba.utils.AppSharePreferences;
import com.wl.lianba.utils.CommonLinearLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wanglong on 17/3/11.
 * 首页
 */

public class BaseHomeFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mRecyclerView;

    private CommonLinearLayoutManager mLayoutManager;

    private HomeAdapter mAdapter;

    private int mSex;

    private List<UserEntity> mUserEntities = new ArrayList<>();

    public static BaseHomeFragment newInstance() {
        return new BaseHomeFragment();
    }

    private void setupView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new HomeAdapter(getContext(), mUserEntities);
        mLayoutManager = new CommonLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRequest(true);
            }
        });

        doRequest(true);
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

    private void doRequest(final boolean refresh) {
        final String url = Config.PATH + "user/persons";
        Map<String, String> params = new HashMap<>();
        OkHttpUtil.getDefault(this).doGetAsync(
                HttpInfo.Builder().setUrl(url).addHeads(getHeader()).addParams(params).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        String result = info.getRetDetail();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        mSwipeRefreshLayout.setRefreshing(false);
                        String result = info.getRetDetail();
                        if (result != null) {
                            try {
                                UserEntity userEntity = JSON.parseObject(result, UserEntity.class);
                                if (userEntity == null) return;
                                if (userEntity.getResult() == null) return;
                                List<UserEntity> userEntities = userEntity.getResult().getPerson_list();
                                dealItemData(userEntities, refresh);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void dealItemData(List<UserEntity> userEntities, boolean refresh) {
        if (userEntities == null || userEntities.size() == 0) return;
        if (refresh) mUserEntities.clear();
        for (UserEntity entity : userEntities) {
            entity.setViewType(UserEntity.TYPE_NORMAL);
        }
        mUserEntities.addAll(userEntities);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        UserEntity info = mAdapter.getItem(position);
        if (info != null) {
            Intent intent = PersonDetailActivity.getIntent(getContext(), info.getUid());
            getContext().startActivity(intent);
        }
    }
}
