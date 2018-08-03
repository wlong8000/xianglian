package com.xianglian.love.main.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.wl.appcore.entity.UserEntity;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.UserUtils;

import java.util.List;

import base.DateUtils2;

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
        int position = helper.getAdapterPosition();
        switch (helper.getItemViewType()) {
            case UserEntity.TYPE_NORMAL:
                View view = helper.itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                layoutParams.topMargin = (position == 0) ? AppUtils.dip2px(mContext, 0) : AppUtils.dip2px(mContext, 10);
                helper.setText(R.id.tv_name, item.getUsername());
//                helper.setImageResource(R.id.iv_like, item.is_like() ?
//                        R.drawable.icon_follow : R.drawable.icon_un_follow);
//                helper.setText(R.id.like, item.getLike() + "");
//                helper.setTag(R.id.like, item.getUid());
//                helper.setTag(R.id.iv_like, item.getUid() + "_iv");

                helper.getView(R.id.tv_locate).setVisibility(TextUtils.isEmpty(
                        item.getWork_area_name()) ? View.GONE : View.VISIBLE);
                helper.setText(R.id.tv_locate, item.getWork_area_name());

                setBaseInfo(helper, item);
                helper.getView(R.id.tv_user_desc).setVisibility(TextUtils.isEmpty(
                        item.getPerson_intro()) ? View.GONE : View.VISIBLE);
                helper.setText(R.id.tv_user_desc, item.getPerson_intro());
//                helper.setText(R.id.tv_id, mContext.getString(R.string.id, String.valueOf(item.getId())));
                helper.setVisible(R.id.tv_id, false);
                helper.setVisible(R.id.tv_line, false);
                SimpleDraweeView simpleDraweeView = helper.getView(R.id.img);
                simpleDraweeView.setImageURI(item.getPic1());
                ImageView imageView = helper.getView(R.id.identity);
                imageView.setVisibility(Config.VISIBLE == item.getIdentity_verified() ? View.VISIBLE : View.GONE);
//                helper.addOnClickListener(R.id.heart_layout);
                break;
        }
    }

    private void setBaseInfo(BaseViewHolder helper, UserEntity item) {
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(item.getBirthday())) {
            builder.append(DateUtils2.getAgeByDateStr(item.getBirthday())).append(mContext.getString(R.string.age2)).append("  ");
        }
        if (AppUtils.stringToInt2(item.getHeight()) > 0) {
            builder.append(mContext.getString(R.string.height2)).append(" ").append(AppUtils.stringToInt2(item.getHeight())).append("  ");
        }
        String career = AppUtils.getCareer(mContext, item.getCareer());
        if (!TextUtils.isEmpty(career)) {
            builder.append(career).append("  ");
        }
        builder.append(mContext.getString(R.string.income)).append(" ").append(UserUtils.getInCome(item.getIncome()));
        helper.setText(R.id.tv_user_head_info, builder.toString());
    }
}
