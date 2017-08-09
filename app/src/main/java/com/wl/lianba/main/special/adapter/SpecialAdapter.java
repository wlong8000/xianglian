package com.wl.lianba.main.special.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wl.lianba.R;
import com.wl.lianba.main.special.model.SpecialInfo;
import com.wl.lianba.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wanglong on 17/3/12.
 * 我的
 */

public class SpecialAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private List<SpecialInfo> mInfo;

    private View.OnClickListener mClickListener;

    public SpecialAdapter(Context context, View.OnClickListener clickListener) {
        this.mContext = context;
        this .mClickListener = clickListener;
    }

    public void setInfo(List<SpecialInfo> info) {
        this.mInfo = info;
    }

    public List<SpecialInfo> getInfo() {
        if (mInfo == null)
            mInfo = new ArrayList<>();
        return mInfo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        switch (viewType) {
            case SpecialInfo.ViewType.BANNER: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_special_banner, null);
                lp = new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, AppUtils.dip2px(mContext, 190));
                view.setLayoutParams(lp);
                view.setOnClickListener(mClickListener);
                return new BannerHolder(view);
            }
            case SpecialInfo.ViewType.ARTICLE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_special_article, null);
                view.setLayoutParams(lp);
                view.setOnClickListener(mClickListener);
                return new ArticleHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case SpecialInfo.ViewType.BANNER: {
                if (mInfo == null) return;
                SpecialInfo info = mInfo.get(position);
                BannerHolder avatarHolder = (BannerHolder) holder;

                break;
            }
            case SpecialInfo.ViewType.ARTICLE: {
                if (mInfo == null) return;
                SpecialInfo info = mInfo.get(position);
                ArticleHolder selectHolder = (ArticleHolder) holder;

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

    public SpecialInfo getItem(int position) {
        if (position < 0 || position >= mInfo.size())
            return null;
        return mInfo.get(position);
    }

    @Override
    public int getItemCount() {
        return mInfo != null ? mInfo.size() : 0;
    }

    /**
     * banner
     */
    private static class BannerHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mAvatar;

        BannerHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 文章
     */
    private static class ArticleHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        ImageView mFirstImage;
        TextView mContent;
        TextView mTime;
        TextView mDescribe;
        TextView mRead;
        TextView mComment;
        TextView mLike;

        ArticleHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.second_adapter_right_image);
            mFirstImage = (ImageView) itemView.findViewById(R.id.second_adapter_left_top_image);
            mContent = (TextView) itemView.findViewById(R.id.second_adapter_left_top_text);
            mTime = (TextView) itemView.findViewById(R.id.second_adapter_left_top_time);
            mDescribe = (TextView) itemView.findViewById(R.id.second_adapter_left_center_text);
            mRead = (TextView) itemView.findViewById(R.id.second_adapter_left_bottom_text);
            mComment = (TextView) itemView.findViewById(R.id.second_adapter_center_bottom_text);
            mLike = (TextView) itemView.findViewById(R.id.second_adapter_right_bottom_text);
        }
    }

}
