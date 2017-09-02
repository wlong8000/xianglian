package com.xianglian.love.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONException;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.callback.Callback;
import com.xianglian.love.BaseListFragment;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.main.home.adapter.HomeAdapter;
import com.xianglian.love.main.home.been.UserDetailEntity;
import com.xianglian.love.main.home.been.UserEntity;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.utils.AppSharePreferences;
import com.xianglian.love.utils.AppUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wanglong on 17/3/11.
 * 首页
 */

public class BaseHomeFragment extends BaseListFragment implements BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.OnItemChildClickListener {

    private HomeAdapter mAdapter;

    private int mSex;

    private List<UserEntity> mUserEntities = new ArrayList<>();

    public static BaseHomeFragment newInstance() {
        return new BaseHomeFragment();
    }

    private void setupView(View view) {
        setupRecyclerView(view);
        mAdapter = new HomeAdapter(getContext(), mUserEntities);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRequest(true);
            }
        });
        mAdapter.setEmptyView(loadingView);
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

    private void doRequest(boolean refresh) {
        doRequest(refresh, null);
    }

    private void doRequest(final boolean refresh, String selection) {
        String url = getUrl(selection);
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

    @NonNull
    private String getUrl(String selection) {
        String userId = AppUtils.getUserId(getContext());
        String url;
        if (!TextUtils.isEmpty(userId) && TextUtils.isEmpty(selection)) {
            url = Config.PATH + "user/persons?uid=" + userId;
        } else if (TextUtils.isEmpty(userId) && TextUtils.isEmpty(selection)) {
            url = Config.PATH + "user/persons";
        } else if (TextUtils.isEmpty(userId) && !TextUtils.isEmpty(selection)) {
            url = Config.PATH + "user/persons?" + selection;
        } else {
            url = Config.PATH + "user/persons?uid=" + userId + "&" + selection;
        }
        return url;
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
                                    TextView likeView = (TextView) mRecyclerView.findViewWithTag(id);
                                    if (likeView != null) {
                                        int num = AppUtils.stringToInt(likeView.getText().toString()) + 1;
                                        likeView.setText(num + "");
                                    }
                                    ImageView likeIcon = (ImageView) mRecyclerView.findViewWithTag(id + "_iv");
                                    if (likeIcon != null) {
                                        likeIcon.setImageResource(R.drawable.icon_follow);
                                    }
                                }

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
            Intent intent = PersonDetailActivity.getIntent(getContext(), info);
            getContext().startActivity(intent);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        UserEntity info = mAdapter.getItem(position);
        switch (view.getId()) {
            case R.id.heart_layout:
                if (info != null)
                    doLikeRequest(info.getUid());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ItemInfo itemInfo = data.getParcelableExtra(Config.EXTRA_SEARCH_ENTITY);
        if (itemInfo != null) {
            doRequest(true, itemInfo.toString());
        }
    }
}
