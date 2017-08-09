package com.wl.lianba.multirecyclerview.inter;

import android.view.View;

import com.wl.lianba.multirecyclerview.MultiRecyclerView;
import com.wl.lianba.multirecyclerview.adapter.BaseViewHolder;


/**
 * Created by jiang on 2017/2/25.
 */

public interface OtherStateBindListener {
    void onBindView(BaseViewHolder holder, MultiRecyclerView.ViewState currentState);
    boolean clickable();
    void onItemClick(View v, MultiRecyclerView.ViewState mViewState);
}
