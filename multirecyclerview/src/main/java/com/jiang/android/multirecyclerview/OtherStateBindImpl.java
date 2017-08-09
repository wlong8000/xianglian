package com.jiang.android.multirecyclerview;

import android.view.View;

import com.jiang.android.multirecyclerview.inter.OtherStateBindListener;

/**
 * Created by jiang on 2017/2/25.
 */

public abstract class OtherStateBindImpl implements OtherStateBindListener {
    @Override
    public boolean clickable() {
        return false;
    }

    @Override
    public void onItemClick(View v, MultiRecyclerView.ViewState mViewState) {

    }
}
