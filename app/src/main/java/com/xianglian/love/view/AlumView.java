
package com.xianglian.love.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.xianglian.love.ShowPicActivity;
import com.xianglian.love.main.home.adapter.AlumAdapter;
import com.xianglian.love.main.home.been.PhotoInfo;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.SpaceItemDecoration;

import java.util.List;

/**
 * 相册
 */
public class AlumView extends RecyclerView implements View.OnClickListener {

    private Context mContext;

    private AlumAdapter mAdapter;

    private int mScreenWidth;

    private int mSpanCount = 4;

    private OnItemClickListener mItemClickListener;

    public AlumView(Context context) {
        this(context, null);
    }

    public AlumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

    }

    public void initAdapter() {
        int width = (mScreenWidth - AppUtils.dip2px(mContext, 8) * (mSpanCount + 1)) / mSpanCount;
        mAdapter = new AlumAdapter(mContext, this, width, width);
        setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int position = getChildAdapterPosition(v);
        PhotoInfo info = mAdapter.getItem(position);
        if (info == null || info.getViewType() == PhotoInfo.AlumViewType.ALUM_ADD) {
            mItemClickListener.onItemClick(position, info);
            return;
        }
        if (TextUtils.isEmpty(info.getImage_url())) return;
        ShowPicActivity.showPictures(mContext, AppUtils.getUrls(mAdapter.getData()), position);
    }

    public void initAdapter(int spanCount) {
        if (spanCount > 0)
            this.mSpanCount = spanCount;
        setLayoutManager(new GridLayoutManager(mContext, mSpanCount));
        addItemDecoration(new SpaceItemDecoration(mContext, 8, mSpanCount));
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        initAdapter();
    }

    public void addData(PhotoInfo data) {
        mAdapter.getData().add(data);
    }

    public void addData(List<PhotoInfo> list) {
        mAdapter.getData().addAll(list);
    }

    public void setData(List<PhotoInfo> list) {
        mAdapter.setData(list);
    }

    public void notifyDataSetChanged () {
        mAdapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.mItemClickListener = clickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, PhotoInfo data);
    }
}
