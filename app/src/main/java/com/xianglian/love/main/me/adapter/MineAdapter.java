package com.xianglian.love.main.me.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xianglian.love.R;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.user.been.OwnerEntity;
import com.xianglian.love.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wanglong on 17/3/12.
 * 我的
 */

public class MineAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private List<ItemInfo> mInfo;

    private View.OnClickListener mClickListener;

    private int mDrawableSize;

    public MineAdapter(Context context, View.OnClickListener clickListener) {
        this.mContext = context;
        this .mClickListener = clickListener;
        mDrawableSize = AppUtils.dip2px(mContext, 15);
    }

    public void setInfo(List<ItemInfo> info) {
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
                view = LayoutInflater.from(mContext).inflate(R.layout.item_update_avatar, null);
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
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ItemInfo.ViewType.AVATAR: {
                if (mInfo == null) return;
                AvatarHolder avatarHolder = (AvatarHolder) holder;
//                OwnerEntity p = AppUtils.getOwnerInfo(mContext);
//                if (p == null) return;
//                avatarHolder.mAvatar.setImageURI(AppUtils.parse(p.getAvatar()));
//                avatarHolder.mNickNameView.setText(TextUtils.isEmpty(p.getNick_name()) ? "" : p.getNick_name());
                break;
            }
            case ItemInfo.ViewType.PICK_SELECT: {
                if (mInfo == null) return;
                ItemInfo info = mInfo.get(position);
                SelectHolder selectHolder = (SelectHolder) holder;
                selectHolder.mLeftView.setText(info.getText());
                dealItemUI(selectHolder.mLeftView, info);
                selectHolder.mDividerView.setVisibility(info.isShowLine() ? View.VISIBLE : View.GONE);
                break;
            }
        }

    }

    private void dealItemUI(TextView textView, ItemInfo info) {
        if (info == null) return;
        int type = info.getType();
        switch (type) {
            case ItemInfo.SettingType.MY_ALBUM: {
                setLeftDrawable(textView, R.drawable.icon_setting_ablum);
                break;
            }
            case ItemInfo.SettingType.INTRODUCE: {
                setLeftDrawable(textView, R.drawable.icon_setting_introduce);
                break;
            }

            case ItemInfo.SettingType.MY_INFO: {
                setLeftDrawable(textView, R.drawable.icon_setting_my_info);
                break;
            }

            case ItemInfo.SettingType.IDENTITY: {
                setLeftDrawable(textView, R.drawable.icon_setting_identity);
                break;
            }

            case ItemInfo.SettingType.HOUSE: {
                setLeftDrawable(textView, R.drawable.icon_setting_house);
                break;
            }
            case ItemInfo.SettingType.CAR: {
                setLeftDrawable(textView, R.drawable.icon_setting_car);
                break;
            }
            case ItemInfo.SettingType.EXPERIENCE_LOVE: {
                setLeftDrawable(textView, R.drawable.icon_setting_experience);
                break;
            }
            case ItemInfo.SettingType.MARK: {
                setLeftDrawable(textView, R.drawable.icon_setting_mark);
                break;
            }
            case ItemInfo.SettingType.HOBBY: {
                setLeftDrawable(textView, R.drawable.icon_setting_hoby);
                break;
            }
            case ItemInfo.SettingType.SETTING: {
                setLeftDrawable(textView, R.drawable.icon_setting);
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

        SelectHolder(View itemView) {
            super(itemView);
            mLeftView = (TextView) itemView.findViewById(R.id.tv_left_text);
            mRightView = (TextView) itemView.findViewById(R.id.tv_right_text);
            mDividerView = itemView.findViewById(R.id.divider);
        }
    }

    private static class AvatarHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mAvatar;
        TextView mNickNameView;
        TextView mIdView;

        AvatarHolder(View itemView) {
            super(itemView);
            mAvatar = (SimpleDraweeView) itemView.findViewById(R.id.head_img);
            mNickNameView = (TextView) itemView.findViewById(R.id.tv_nick_name);
            mIdView = (TextView) itemView.findViewById(R.id.tv_id);
        }
    }

}
