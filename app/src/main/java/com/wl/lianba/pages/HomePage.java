package com.wl.lianba.pages;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wl.lianba.R;
import com.wl.lianba.bottomttabs.BaseMainPage;
import com.wl.lianba.multirecyclerview.MultiRecyclerView;
import com.wl.lianba.multirecyclerview.OtherStateBindImpl;
import com.wl.lianba.multirecyclerview.adapter.BaseAdapter;
import com.wl.lianba.multirecyclerview.adapter.BaseViewHolder;
import com.wl.lianba.multirecyclerview.inter.OnLoadMoreListener;
import com.wl.lianba.pagemanager.PageManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/2/10 0010.
 * 首页
 */

public class HomePage extends BaseMainPage {
    private PageManager mPageManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MultiRecyclerView mRecyclerView;
    private Activity mContext;
    private Handler mHandler = new Handler();

    public HomePage(Activity mainActivity) {
        super(mainActivity);
        this.mContext = mainActivity;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.tab_home_layout;
    }

    @Override
    public void onResume() {
        Log.d("tag", "======onResume====");
    }

    @Override
    public void onPause() {
        Log.d("tag", "======onPause====");
    }

    @Override
    protected void initDataReally() {
        Log.d("tag", "=======initDataReally====");
//        setupView(getRootView());
        if (mPageManager == null) {
            mPageManager = PageManager.init(getRootView().findViewById(R.id.refresh), true, new Runnable() {
                @Override
                public void run() {
                    mPageManager.showLoading();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPageManager.showError("实在不行了");
                        }
                    }, 3000);
                }
            });
        }
        mPageManager.showLoading();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPageManager.showError("hshhshsh");
            }
        }, 3000);
    }

    private void setupView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mRecyclerView = (MultiRecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setOtherStateBindListener(new OtherStateBindImpl() {
            @Override
            public void onBindView(BaseViewHolder holder, MultiRecyclerView.ViewState currentState) {
                switch (currentState) {
                    case EMPTY:
                        TextView tv = holder.getView(R.id.empty);
                        tv.setText("custom empty text");
                        break;
                    case ERROR:
                        TextView tv2 = holder.getView(R.id.error);
                        tv2.setText("custom error text");
                        break;
                    case LOADING:
                        TextView tv3 = holder.getView(R.id.loading);
                        tv3.setText("custom loading text");
                        break;
                }
            }

            @Override
            public boolean clickable() {
                return true;
            }

            @Override
            public void onItemClick(View v, MultiRecyclerView.ViewState mViewState) {
                Toast.makeText(mContext, "you clicked: " + mViewState.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.clear();
                        initData();
                        p = 0;
                        mSwipeRefreshLayout.setRefreshing(false);
                        mRecyclerView.setViewState(MultiRecyclerView.ViewState.CONTENT);

                    }
                }, 2000);
            }
        });
        initRecyclerView();
        mRecyclerView.setViewState(MultiRecyclerView.ViewState.LOADING);
        mRecyclerView.setLoadMoreEnabled(true);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                mRecyclerView.setViewState(MultiRecyclerView.ViewState.CONTENT);
            }
        }, 5000);
    }

    private void initRecyclerView() {
        mRecyclerView.config(new GridLayoutManager(mContext, 3),
                new BaseAdapter() {
                    @Override
                    public void onBindView(BaseViewHolder holder, int position) {
                        TextView textView = holder.getView(R.id.number);
                        textView.setText(mDatas.get(position));
                    }

                    @Override
                    public int getLayoutID(int position) {
                        return R.layout.item;
                    }

                    @Override
                    public boolean clickable() {
                        return true;
                    }

                    @Override
                    public void onItemClick(View v, int position) {
                        super.onItemClick(v, position);
                        Toast.makeText(mContext, "you clicked:" + position, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public int getItemCount() {
                        return mDatas.size();
                    }
                });


        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        mRecyclerView.setViewState(MultiRecyclerView.ViewState.CONTENT);
                        mRecyclerView.loadMoreComplete();
                        if (p == 5) {
                            mRecyclerView.setLoadMoreEnabled(false);
                        }
                    }
                }, 2000);

            }
        });

    }


    @Override
    public void onTabShow() {
        Log.d("tag", "======onTabShow====");
    }

    @Override
    public void onTabHide() {

    }

    private List<String> mDatas = new ArrayList<>();
    int p = 0;

    public void initData() {
        p++;
        for (int i = 0; i < 30; i++) {
            mDatas.add("number: " + i);
        }
    }
}
