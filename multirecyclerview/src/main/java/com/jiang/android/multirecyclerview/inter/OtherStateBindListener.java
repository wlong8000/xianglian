package com.jiang.android.multirecyclerview.inter;

import android.view.View;

import com.jiang.android.multirecyclerview.MultiRecyclerView;
import com.jiang.android.multirecyclerview.adapter.BaseViewHolder;

/**
 * Created by jiang on 2017/2/25.
 */

public interface OtherStateBindListener {
    void onBindView(BaseViewHolder holder, MultiRecyclerView.ViewState currentState);
    boolean clickable();
    void onItemClick(View v, MultiRecyclerView.ViewState mViewState);
}
