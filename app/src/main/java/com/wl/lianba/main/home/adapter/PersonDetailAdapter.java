package com.wl.lianba.main.home.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wl.lianba.R;
import com.wl.lianba.library.tag.TagBaseAdapter;
import com.wl.lianba.library.tag.TagCloudLayout;
import com.wl.lianba.main.home.been.UserDetailEntity;
import com.wl.lianba.utils.AppUtils;
import com.wl.lianba.view.AlumView;
import com.wl.lianba.view.FavoriteView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wanglong on 17/3/12.
 * 详情页
 */

public class PersonDetailAdapter extends BaseMultiItemQuickAdapter<UserDetailEntity, BaseViewHolder> {

    private Context mContext;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
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
                helper.setImageVisible(R.id.ver, item.isIdentity_verified());
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
                alumView.setData(item.getAlbums());
                break;
            case UserDetailEntity.ViewType.BASE_INFO:
                TagCloudLayout tagCloudLayout = helper.getView(R.id.tab_container);
                List<String> mList = new ArrayList<>();
                mList.add("出生日期 / 1989-11-15");
                mList.add("星座 / 金牛座");
                mList.add("身高 / 158");
                mList.add("年收入 / 10w-20w");
                mList.add("工作生活在 / 廊坊");
                TagBaseAdapter mAdapter = new TagBaseAdapter(mContext, mList);
                tagCloudLayout.setAdapter(mAdapter);
                break;
            case UserDetailEntity.ViewType.TITLE:
                break;
            case UserDetailEntity.ViewType.LEAVE_MESSAGE:
                break;
        }
    }

    private void setBaseInfo(BaseViewHolder helper, UserDetailEntity item) {
        StringBuilder builder = new StringBuilder();
        builder.append(item.getAge()).append(mContext.getString(R.string.age2)).append(" ");
        builder.append(mContext.getString(R.string.height2)).append(" ").append(item.getHeight()).append(" ");
        builder.append(item.getCareer()).append(" ");
        builder.append(mContext.getString(R.string.income)).append(" ").append(item.getIncome());
        helper.setText(R.id.tv_user_head_info, builder.toString());
    }

}
