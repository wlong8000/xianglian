package com.wl.lianba.main.meet.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
 * 约见
 */

public class MeetAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private List<ItemInfo> mInfo;

    private View.OnClickListener mClickListener;

    private int mDrawableSize;

    public MeetAdapter(Context context, View.OnClickListener clickListener) {
        this.mContext = context;
        this .mClickListener = clickListener;
        mDrawableSize = AppUtils.dip2px(mContext, 15);
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
            case ItemInfo.ViewType.PICK_SELECT: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_meet_info, null);
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
            case ItemInfo.ViewType.PICK_SELECT: {
                if (mInfo == null) return;
                ItemInfo info = mInfo.get(position);
                SelectHolder selectHolder = (SelectHolder) holder;
                selectHolder.mLeftView.setText(info.getText());
                setLeftIcon(selectHolder.mLeftView, info.getType(), selectHolder.mDividerView);
                break;
            }
        }

    }

    private void setLeftIcon(TextView textView, int type, View divider) {
        divider.setVisibility(View.GONE);
        switch (type) {
            case ItemInfo.MeetType.AT_ME: {
                setLeftDrawable(textView, R.drawable.icon_meet_at_me);
                break;
            }
            case ItemInfo.MeetType.ME_AT: {
                setLeftDrawable(textView, R.drawable.icon_meet_me_at);
                break;
            }

            case ItemInfo.MeetType.LAST_COME: {
                setLeftDrawable(textView, R.drawable.icon_meet_last_come);
                break;
            }

            case ItemInfo.MeetType.HEART_RECORD: {
                setLeftDrawable(textView, R.drawable.icon_meet_heart_record);
                break;
            }

            case ItemInfo.MeetType.HEART_ADDRESS_BOOK: {
                setLeftDrawable(textView, R.drawable.icon_meet_heart_addressbook);
                break;
            }
        }
    }

    private void setLeftDrawable(TextView textView, int res) {
        Drawable draw = mContext.getResources().getDrawable(res);
        draw.setBounds(0, 0, mDrawableSize, mDrawableSize);
        textView.setCompoundDrawables(draw, null, null, null);
        textView.setCompoundDrawablePadding(10);
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

}
