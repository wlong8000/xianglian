package com.xianglian.love.main.meet.adapter;

import android.text.TextUtils;
import android.widget.TextView;

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
        addItemType(MeetInfo.ViewType.HEADER, R.layout.item_meet_detail_header);
        addItemType(MeetInfo.ViewType.ITEM, R.layout.item_meet_detail_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeetInfo item) {
        if (item == null) return;
        switch (helper.getItemViewType()) {
            case MeetInfo.ViewType.COMMON_INFO: {
                SimpleDraweeView imageView = helper.getView(R.id.cover_img);
                imageView.setImageURI(AppUtils.parse(item.getUrl()));
                helper.setText(R.id.title, item.getTitle());
                helper.setText(R.id.sub_content, item.getSub_describe());
                helper.setText(R.id.price, item.getPrice());
                helper.setVisible(R.id.tag_layout, false);
                helper.setVisible(R.id.tag1, false);
                helper.setVisible(R.id.tag2, false);
                helper.setVisible(R.id.tag3, false);
                if (!TextUtils.isEmpty(item.getTag1())) {
                    helper.setVisible(R.id.tag_layout, true);
                    helper.setVisible(R.id.tag1, true);
                    helper.setText(R.id.tag1, item.getTag1());
                }
                if (!TextUtils.isEmpty(item.getTag2())) {
                    helper.setVisible(R.id.tag_layout, true);
                    helper.setVisible(R.id.tag2, true);
                    helper.setText(R.id.tag2, item.getTag2());
                }
                if (!TextUtils.isEmpty(item.getTag3())) {
                    helper.setVisible(R.id.tag_layout, true);
                    helper.setVisible(R.id.tag3, true);
                    helper.setText(R.id.tag3, item.getTag3());
                }
            }
            break;
            case MeetInfo.ViewType.HEADER: {
                SimpleDraweeView imageView = helper.getView(R.id.img);
                imageView.setImageURI(AppUtils.parse(item.getUrl()));
                helper.setText(R.id.theme, item.getTheme());
            }
            break;
            case MeetInfo.ViewType.ITEM: {
                TextView textView = helper.getView(R.id.detail);
                AppUtils.drawableLeft(textView, item.getRes());
                helper.setText(R.id.detail, item.getContent());
            }
            break;
        }
    }


}
