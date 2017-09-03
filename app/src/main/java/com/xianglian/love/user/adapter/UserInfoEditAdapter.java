package com.xianglian.love.user.adapter;

import android.content.Context;
import android.text.TextUtils;

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
        addItemType(ItemInfo.ViewType.TOAST, R.layout.item_user_toast);
        addItemType(ItemInfo.ViewType.TOAST2, R.layout.item_user_toast2);
        addItemType(ItemInfo.ViewType.TOAST2, R.layout.item_user_toast2);
        addItemType(ItemInfo.ViewType.PHONE, R.layout.item_user_phone);
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
            case ItemInfo.ViewType.PHONE:
                helper.setTag(R.id.et_text, item.phoneType);
                if (item.contacts != null && TextUtils.isEmpty(item.contacts.getContent())) {
                    helper.setText(R.id.et_text, item.contacts.getContent());
                } else {
                    helper.setText(R.id.et_text, "");
                }

                switch (item.phoneType) {
                    case ItemInfo.PhoneType.QQ:
                        helper.setImageResource(R.id.iv_left, R.drawable.icon_car);
                        helper.setText(R.id.tv_text, mContext.getString(R.string.wei_xin));
                        break;
                    case ItemInfo.PhoneType.WEI_XIN:
                        helper.setImageResource(R.id.iv_left, R.drawable.icon_house);
                        helper.setText(R.id.tv_text, mContext.getString(R.string.qq));
                        break;
                }
                break;
        }
    }

}
