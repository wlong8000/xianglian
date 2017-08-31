package com.xianglian.love.main.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.main.home.been.UserEntity;
import com.xianglian.love.utils.AppUtils;

import java.util.List;

/**
 * Created by wanglong on 17/3/12.
 *
 */

public class HomeAdapter extends BaseMultiItemQuickAdapter<UserEntity, BaseViewHolder> {
    private Context mContext;
    public HomeAdapter(Context context, List<UserEntity> data) {
        super(data);
        this.mContext = context;
        addItemType(UserEntity.TYPE_NORMAL, R.layout.item_home_main);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserEntity item) {
        if (item == null) return;
        switch (helper.getItemViewType()) {
            case UserEntity.TYPE_NORMAL:
                helper.setText(R.id.tv_name, item.getNickname());

                helper.setImageResource(R.id.iv_like, item.is_like() ?
                        R.drawable.icon_follow : R.drawable.icon_un_follow);
                helper.setText(R.id.like, item.getLike() + "");
                helper.setTag(R.id.like, item.getUid());
                helper.setTag(R.id.iv_like, item.getUid() + "_iv");

                helper.getView(R.id.tv_locate).setVisibility(TextUtils.isEmpty(
                        item.getWork_area()) ? View.GONE : View.VISIBLE);
                helper.setText(R.id.tv_locate, item.getWork_area());

                setBaseInfo(helper, item);
                helper.getView(R.id.tv_user_desc).setVisibility(TextUtils.isEmpty(
                        item.getPerson_intro()) ? View.GONE : View.VISIBLE);
                helper.setText(R.id.tv_user_desc, item.getPerson_intro());
                helper.setText(R.id.tv_id, mContext.getString(R.string.id, item.getAccount()));
                helper.setImageURI(R.id.img, item.getAvatar());
                helper.setImageVisible(R.id.identity, Config.VISIBLE == item.getIdentity_verified());

                helper.addOnClickListener(R.id.heart_layout);

                break;
        }
    }

    private void setBaseInfo(BaseViewHolder helper, UserEntity item) {
        StringBuilder builder = new StringBuilder();
        if (AppUtils.stringToInt(item.getAge()) > 0) {
            builder.append(item.getAge()).append(mContext.getString(R.string.age2)).append("  ");
        }
        if (AppUtils.stringToInt(item.getHeight()) > 0) {
            builder.append(mContext.getString(R.string.height2)).append(" ").append(item.getHeight()).append("  ");
        }
        String career = AppUtils.getCareer(mContext, item.getCareer());
        if (!TextUtils.isEmpty(career)) {
            builder.append(career).append("  ");
        }
        if (!TextUtils.isEmpty(item.getIncome()) && !"0".equals(item.getIncome())) {
            builder.append(mContext.getString(R.string.income)).append(" ").
                    append(mContext.getString(R.string.money, item.getIncome()));
        }
        helper.setText(R.id.tv_user_head_info, builder.toString());
    }
}
