package com.wl.lianba.main.home.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wl.lianba.R;
import com.wl.lianba.config.Config;
import com.wl.lianba.main.home.been.UserEntity;
import com.wl.lianba.utils.AppUtils;

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
                helper.setText(R.id.like, item.getLike() + "");
                helper.setTag(R.id.like, item.getUid());
                helper.setText(R.id.tv_locate, item.getWork_area());

                setBaseInfo(helper, item);

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
        builder.append(item.getAge()).append(mContext.getString(R.string.age2)).append(" ");
        builder.append(mContext.getString(R.string.height2)).append(" ").append(item.getHeight()).append(" ");
        builder.append(AppUtils.getCareer(mContext, item.getCareer())).append(" ");
        builder.append(mContext.getString(R.string.income)).append(" ").append(item.getIncome());
        helper.setText(R.id.tv_user_head_info, builder.toString());
    }
}
