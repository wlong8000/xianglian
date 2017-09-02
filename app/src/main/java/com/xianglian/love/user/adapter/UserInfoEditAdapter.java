package com.xianglian.love.user.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xianglian.love.R;
import com.xianglian.love.user.been.ItemInfo;

import java.util.List;


/**
 * Created by wanglong on 17/3/12.
 * 用户信息编辑页
 */

public class UserInfoEditAdapter extends BaseMultiItemQuickAdapter<ItemInfo, BaseViewHolder> {

    private Context mContext;

    public UserInfoEditAdapter(Context context, List<ItemInfo> data) {
        super(data);
        this.mContext = context;
        addItemType(ItemInfo.ViewType.AVATAR, R.layout.item_avatar);
        addItemType(ItemInfo.ViewType.PICK_SELECT, R.layout.item_user_info);
        addItemType(ItemInfo.ViewType.COMPLETE, R.layout.item_complete);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemInfo item) {
        if (item == null) return;
        switch (helper.getItemViewType()) {
            case ItemInfo.ViewType.AVATAR:
                helper.setImageURI(R.id.head_img, item.getAvatar());
                break;
            case ItemInfo.ViewType.PICK_SELECT:
                helper.setText(R.id.tv_left_text, item.getText());
                helper.setText(R.id.tv_right_text, item.getRightText());
                break;
            case ItemInfo.ViewType.COMPLETE:
                break;
        }
    }

}
