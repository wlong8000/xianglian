package com.xianglian.love.main.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xianglian.love.R;
import com.xianglian.love.library.tag.TagBaseAdapter;
import com.xianglian.love.library.tag.TagCloudLayout;
import com.xianglian.love.main.home.been.UserDetailEntity;
import com.xianglian.love.main.home.been.UserEntity;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.UserUtils;
import com.xianglian.love.view.AlumView;
import com.xianglian.love.view.FavoriteView;

import java.util.List;

import base.DateUtils2;


/**
 * Created by wanglong on 17/3/12.
 * 详情页
 */

public class PersonDetailAdapter extends BaseMultiItemQuickAdapter<UserDetailEntity, BaseViewHolder> {

    private Context mContext;

    public PersonDetailAdapter(Context context, List<UserDetailEntity> data) {
        super(data);
        this.mContext = context;
        addItemType(UserDetailEntity.ViewType.TOP_INFO, R.layout.item_home_detail_info);
        addItemType(UserDetailEntity.ViewType.INTRODUCE, R.layout.item_home_detail_introduce);
        addItemType(UserDetailEntity.ViewType.ALBUM, R.layout.item_home_detail_alum);
        addItemType(UserDetailEntity.ViewType.EXPERIENCE_EMOTION, R.layout.item_home_detail_introduce);
        addItemType(UserDetailEntity.ViewType.MARK, R.layout.item_home_detail_mark);
        addItemType(UserDetailEntity.ViewType.FAVORITE, R.layout.item_home_detail_fav);
        addItemType(UserDetailEntity.ViewType.BASE_INFO, R.layout.item_home_detail_base_info);
        addItemType(UserDetailEntity.ViewType.LEAVE_MESSAGE, R.layout.item_leave_message);
        addItemType(UserDetailEntity.ViewType.TITLE, R.layout.item_detail_title);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserDetailEntity result) {
        UserDetailEntity item = result.getResult();
        switch (helper.getItemViewType()) {
            case UserDetailEntity.ViewType.TOP_INFO:
                helper.setText(R.id.name, item.getNickname());
                helper.setText(R.id.tv_locate, item.getWork_area_name());
                helper.setText(R.id.tv_id, item.getAccount());
                ImageView imageView = helper.getView(R.id.ver);
                imageView.setVisibility(item.isIdentity_verified() ? View.VISIBLE : View.GONE);
//                helper.setImageVisible(R.id.ver, item.isIdentity_verified());
                setBaseInfo(helper, item);
                break;
            case UserDetailEntity.ViewType.INTRODUCE:
                helper.setText(R.id.title, mContext.getString(R.string.my_introduce));
                helper.setText(R.id.introduce, item.getPerson_intro());
                break;
            case UserDetailEntity.ViewType.EXPERIENCE_EMOTION:
                helper.setText(R.id.title, mContext.getString(R.string.experience_love));
                helper.setText(R.id.introduce, item.getRelationship_desc());
                break;
            case UserDetailEntity.ViewType.MARK:
                helper.setText(R.id.title, mContext.getString(R.string.mark));
                FavoriteView view = helper.getView(R.id.favorite_layout);
                view.initAdapter(0);
                view.setData(AppUtils.getTags(item.getTags()));
                break;
            case UserDetailEntity.ViewType.FAVORITE:
                helper.setText(R.id.title, mContext.getString(R.string.hobby));
                FavoriteView favoriteView = helper.getView(R.id.favorite_layout);
                favoriteView.initAdapter(0);
                favoriteView.setData(AppUtils.getInterests(item.getInterests()));
                break;
            case UserDetailEntity.ViewType.ALBUM:
                AlumView alumView = helper.getView(R.id.alum_layout);
                alumView.initAdapter(4);
                alumView.setData(item.getImages());
                break;
            case UserDetailEntity.ViewType.BASE_INFO:
                helper.setText(R.id.car, "有".equals(item.getHas_car()) ?
                        mContext.getString(R.string.has_car) : mContext.getString(R.string.has_no_car));
                helper.setText(R.id.house, "有".equals(item.getHas_house()) ?
                        mContext.getString(R.string.has_house) : mContext.getString(R.string.has_no_house));
                TagCloudLayout tagCloudLayout = helper.getView(R.id.tab_container);
                TagBaseAdapter mAdapter = new TagBaseAdapter(mContext, UserUtils.getBaseInfoList(item));
                tagCloudLayout.setAdapter(mAdapter);
                break;
            case UserDetailEntity.ViewType.TITLE:
                helper.setText(R.id.title, mContext.getString(R.string.message));
                break;
            case UserDetailEntity.ViewType.LEAVE_MESSAGE:
//                helper.setImageURI(R.id.head, AppUtils.parse(result.getSender_avatar()));
                ImageView head = helper.getView(R.id.head);
                head.setVisibility(item.isIdentity_verified() ? View.VISIBLE : View.GONE);
                helper.setText(R.id.name, result.getSender_name());
                helper.setText(R.id.content, result.getContent());
                helper.setText(R.id.time, result.getCreate_time());
                helper.setGone(R.id.more, result.isShow_msg_more());
                break;
        }
    }

    private void setBaseInfo(BaseViewHolder helper, UserDetailEntity item) {
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(item.getBirthday())) {
            builder.append(DateUtils2.getAgeByDateStr(item.getBirthday())).append(mContext.getString(R.string.age2)).append("  ");
        }
        if (AppUtils.stringToInt2(item.getHeight()) > 0) {
            builder.append(mContext.getString(R.string.height2)).append(" ").append(AppUtils.stringToInt2(item.getHeight())).append("  ");
        }
        String career = AppUtils.getCareer(mContext, AppUtils.stringToInt(item.getCareer()));
        if (!TextUtils.isEmpty(career)) {
            builder.append(career).append("  ");
        }
        builder.append(mContext.getString(R.string.income)).append(" ").append(UserUtils.getIncome(AppUtils.stringToInt(item.getIncome())));
        helper.setText(R.id.tv_user_head_info, builder.toString());
    }

}
