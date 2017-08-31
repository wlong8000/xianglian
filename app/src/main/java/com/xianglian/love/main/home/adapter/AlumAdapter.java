
package com.xianglian.love.main.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xianglian.love.R;
import com.xianglian.love.main.home.been.PhotoInfo;
import com.xianglian.love.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 相册
 */
public class AlumAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private LayoutInflater mInflater;

    private View.OnClickListener mListener;

    private int mItemWidth, mItemHeight;

    private List<PhotoInfo> mData;

    public AlumAdapter(Context context, View.OnClickListener listener, int itemWidth, int itemHeight) {
        this.mContext = context;
        this.mListener = listener;
        mInflater = LayoutInflater.from(mContext);
        this.mItemHeight = itemHeight;
        this.mItemWidth = itemWidth;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case PhotoInfo.AlumViewType.ALUM_ADD: {
                view = mInflater.inflate(R.layout.item_alum_add, parent, false);
                AddPhotoViewHolder holder = new AddPhotoViewHolder(view);
                ViewGroup.LayoutParams params = holder.mAddPhoto.getLayoutParams();
                params.width = mItemWidth;
                params.height = mItemHeight;
                holder.mAddPhoto.setLayoutParams(params);
                view.setOnClickListener(mListener);
                return holder;
            }
            case PhotoInfo.AlumViewType.ALUM_COMMON: {
                view = mInflater.inflate(R.layout.item_alum, parent, false);
                FavoriteViewHolder holder = new FavoriteViewHolder(view);
                ViewGroup.LayoutParams params = holder.mPhotoView.getLayoutParams();
                params.width = mItemWidth;
                params.height = mItemHeight;
                holder.mPhotoView.setLayoutParams(params);
                view.setOnClickListener(mListener);
                return holder;
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case PhotoInfo.AlumViewType.ALUM_ADD: {
                break;
            }
            case PhotoInfo.AlumViewType.ALUM_COMMON: {
                PhotoInfo info = getItem(position);
                if (info == null) return;
                FavoriteViewHolder favoriteViewHolder = (FavoriteViewHolder) holder;
                favoriteViewHolder.mPhotoView.setImageURI(AppUtils.parse(info.getPhoto_url()));
                break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) == null)
            return -1;
        return getItem(position).getViewType();

    }

    public PhotoInfo getItem(int position) {
        if (position < 0 || position >= mData.size())
            return null;
        return mData.get(position);
    }

    public List<PhotoInfo> getData() {
        if (mData == null)
            mData = new ArrayList<>();
        return mData;
    }

    public void setData(List<PhotoInfo> mData) {
        this.mData = mData;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mPhotoView;

        FavoriteViewHolder(View itemView) {
            super(itemView);
            mPhotoView = (SimpleDraweeView) itemView.findViewById(R.id.photo);
        }
    }

    private static class AddPhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView mAddPhoto;

        AddPhotoViewHolder(View itemView) {
            super(itemView);
            mAddPhoto = (ImageView) itemView.findViewById(R.id.add_photo);
        }
    }
}
