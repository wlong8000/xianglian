
package com.wl.lianba.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.wl.lianba.main.home.adapter.FavoriteAdapter;
import com.wl.lianba.utils.SpaceItemDecoration;

import java.util.List;

/**
 * 喜爱
 */
public class FavoriteView extends RecyclerView implements View.OnClickListener {

    private Context mContext;

    private FavoriteAdapter mAdapter;

    private int mSpanCount = 4;

    public FavoriteView(Context context) {
        this(context, null);
    }

    public FavoriteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setLayoutManager(new GridLayoutManager(mContext, 4));
        addItemDecoration(new SpaceItemDecoration(mContext, 10, 4));
    }

    public void initAdapter(int spanCount) {
        if (spanCount > 0)
            this.mSpanCount = spanCount;
        setLayoutManager(new GridLayoutManager(mContext, mSpanCount));
        addItemDecoration(new SpaceItemDecoration(mContext, 8, mSpanCount));
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new FavoriteAdapter(mContext, this, 0, 0);
        setAdapter(mAdapter);
    }


    public void setData(List<String> list) {
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }
}
