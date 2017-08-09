package com.wl.lianba.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wl.lianba.R;
import com.wl.lianba.user.been.ItemInfo;
import com.wl.lianba.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wanglong on 17/3/12.
 * 用户信息编辑页
 */

public class UserInfoEditAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private List<ItemInfo> mInfo;

    private View.OnClickListener mClickListener;

    public UserInfoEditAdapter(Context context, View.OnClickListener clickListener) {
        this.mContext = context;
        this .mClickListener = clickListener;
    }

    public void setPersons(List<ItemInfo> info) {
        this.mInfo = info;
    }

    public List<ItemInfo> getInfo() {
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
            case ItemInfo.ViewType.AVATAR: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_avatar, null);
                view.setLayoutParams(lp);
                view.setOnClickListener(mClickListener);
                return new AvatarHolder(view);
            }
            case ItemInfo.ViewType.PICK_SELECT: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_user_info, null);
                view.setLayoutParams(lp);
                view.setOnClickListener(mClickListener);
                return new SelectHolder(view);
            }
            case ItemInfo.ViewType.COMPLETE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_complete, null);
                lp.leftMargin = AppUtils.dip2px(mContext, 10);
                lp.rightMargin = AppUtils.dip2px(mContext, 10);
                lp.topMargin = AppUtils.dip2px(mContext, 20);
                lp.bottomMargin = AppUtils.dip2px(mContext, 15);
                view.setLayoutParams(lp);
                view.setOnClickListener(mClickListener);
                return new CompleteViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ItemInfo.ViewType.AVATAR: {
                if (mInfo == null) return;
                ItemInfo info = mInfo.get(position);
                AvatarHolder avatarHolder = (AvatarHolder) holder;
                avatarHolder.mAvatar.setImageURI(info.getAvatar());
                break;
            }
            case ItemInfo.ViewType.PICK_SELECT: {
                if (mInfo == null) return;
                ItemInfo info = mInfo.get(position);
                SelectHolder selectHolder = (SelectHolder) holder;
                selectHolder.mLeftView.setText(info.getText());
                selectHolder.mRightView.setText(info.getRightText());
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

    public ItemInfo getItem(int position) {
        if (position < 0 || position >= mInfo.size())
            return null;
        return mInfo.get(position);
    }

    @Override
    public int getItemCount() {
        return mInfo != null ? mInfo.size() : 0;
    }

    private static class SelectHolder extends RecyclerView.ViewHolder {
        TextView mLeftView, mRightView;
        View mDividerView;

        public SelectHolder(View itemView) {
            super(itemView);
            mLeftView = (TextView) itemView.findViewById(R.id.tv_left_text);
            mRightView = (TextView) itemView.findViewById(R.id.tv_right_text);
            mDividerView = itemView.findViewById(R.id.divider);
        }
    }

    private static class AvatarHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mAvatar;

        AvatarHolder(View itemView) {
            super(itemView);
            mAvatar = (SimpleDraweeView) itemView.findViewById(R.id.head_img);
        }
    }

    private static class CompleteViewHolder extends RecyclerView.ViewHolder {

        public CompleteViewHolder(View itemView) {
            super(itemView);
        }
    }

}
