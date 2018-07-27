package com.xianglian.love;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.CommonLinearLayoutManager;

import java.util.Map;

/**
 * Created by wanglong on 17/3/11.
 */

public abstract class BaseListActivity extends BaseActivity {
    public View emptyView;

    public View errorView;

    public View loadingView;

    public SwipeRefreshLayout mSwipeRefreshLayout;

    public RecyclerView mRecyclerView;

    public CommonLinearLayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void toast(int res) {
        toast(getResources().getString(res));
    }

    public Map<String, String> getHeader() {
        return AppUtils.getOAuthMap(this);
    }

    public void setupRecyclerView() {
        try {
            mSwipeRefreshLayout = findViewById(R.id.refresh);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mRecyclerView = findViewById(R.id.recycler_view);
            mLayoutManager = new CommonLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);

            emptyView = getLayoutInflater().inflate(R.layout.empty,
                    (ViewGroup) mRecyclerView.getParent(), false);
            errorView = getLayoutInflater().inflate(R.layout.error,
                    (ViewGroup) mRecyclerView.getParent(), false);
            errorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRefresh(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public abstract void onRefresh(boolean refresh);
}
