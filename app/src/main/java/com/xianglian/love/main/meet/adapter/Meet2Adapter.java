package com.xianglian.love.main.meet.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xianglian.love.R;
import com.xianglian.love.main.meet.model.MeetInfo;
import com.xianglian.love.utils.AppUtils;

import java.util.List;


/**
 * Created by wanglong on 17/3/12.
 * 聚会
 */

public class Meet2Adapter extends BaseMultiItemQuickAdapter<MeetInfo, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public Meet2Adapter(List<MeetInfo> data) {
        super(data);
        addItemType(MeetInfo.ViewType.COMMON_INFO, R.layout.item_meet_cover);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeetInfo item) {
        if (item == null) return;
        switch (helper.getItemViewType()) {
            case MeetInfo.ViewType.COMMON_INFO:
                SimpleDraweeView imageView = helper.getView(R.id.cover_img);
                imageView.setImageURI(AppUtils.parse(item.getUrl()));
                break;
        }
    }

}
