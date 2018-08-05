package com.xianglian.love.main.meet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xianglian.love.R;
import com.xianglian.love.main.meet.model.MeetInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wanglong on 17/3/12.
 * 待回复、已同意、已拒绝、先聊聊
 */

public class TalkMeetAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private List<MeetInfo> mInfo;

    private View.OnClickListener mClickListener;

    public TalkMeetAdapter(Context context, View.OnClickListener clickListener) {
        this.mContext = context;
        this .mClickListener = clickListener;
    }

    public void setInfo(List<MeetInfo> info) {
        this.mInfo = info;
    }

    public List<MeetInfo> getInfo() {
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
            case MeetInfo.ViewType.COMMON_INFO: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_meet_talk, null);
                view.setLayoutParams(lp);
                view.setOnClickListener(mClickListener);
                return new SelectHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case MeetInfo.ViewType.COMMON_INFO: {
                if (mInfo == null) return;
                MeetInfo info = mInfo.get(position);
//                AvatarHolder avatarHolder = (AvatarHolder) holder;
//                avatarHolder.mAvatar.setImageURI(info.getAvatar());
                break;
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) == null)
            return -1;
        return getItem(position).getItemType();

    }

    public MeetInfo getItem(int position) {
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
