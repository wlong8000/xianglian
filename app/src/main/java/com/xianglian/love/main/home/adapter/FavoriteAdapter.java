
package com.xianglian.love.main.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xianglian.love.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 爱好
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private Context mContext;

    private LayoutInflater mInflater;

    private View.OnClickListener mListener;

    private int mItemWidth, mItemHeight;

    private List<String> mData;

    public FavoriteAdapter(Context context, View.OnClickListener listener, int itemWidth, int itemHeight) {
        this.mContext = context;
        this.mListener = listener;
        mInflater = LayoutInflater.from(mContext);
        this.mItemHeight = itemHeight;
        this.mItemWidth = itemWidth;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_fav_mark, parent, false);
        FavoriteViewHolder holder = new FavoriteViewHolder(view);
        view.setOnClickListener(mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        String mark = getItem(position);
        if (TextUtils.isEmpty(mark)) return;
        holder.hobbyView.setText(mark);
    }

    public String getItem(int position) {
        if (position < 0 || position >= mData.size())
            return null;
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public List<String> getData() {
        if (mData == null)
            mData = new ArrayList<>();
        return mData;
    }

    public void setData(List<String> mData) {
        this.mData = mData;
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView hobbyView;
        FavoriteViewHolder(View itemView) {
            super(itemView);
            hobbyView = (TextView) itemView.findViewById(R.id.hobby);
        }
    }
}
