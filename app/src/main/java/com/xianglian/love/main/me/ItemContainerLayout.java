package com.xianglian.love.main.me;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xianglian.love.R;
import com.xianglian.love.main.home.been.UserDetailEntity;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglong on 2018/8/4.
 */

public class ItemContainerLayout extends LinearLayout {
    private UserDetailEntity mDetailEntity;

    private List<ItemInfo> detailEntities = new ArrayList<>();

    public ItemContainerLayout(Context context) {
        super(context);
    }

    public ItemContainerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemContainerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ItemContainerLayout setDetailEntity(UserDetailEntity detailEntity) {
        this.mDetailEntity = detailEntity;
        return this;
    }

    public ItemContainerLayout addItems() {
        detailEntities.clear();
        ItemInfo itemInfo;
        if (!TextUtils.isEmpty(mDetailEntity.getConstellation())) {
            itemInfo = new ItemInfo();
            itemInfo.setText("星座");
            itemInfo.setRightText(UserUtils.getConstellation(AppUtils.stringToInt(mDetailEntity.getConstellation())));
            detailEntities.add(itemInfo);

        }
        if (!TextUtils.isEmpty(mDetailEntity.getEducation())) {
            itemInfo = new ItemInfo();
            itemInfo.setText("教育程度");
            itemInfo.setRightText(UserUtils.getEdu(AppUtils.stringToInt(mDetailEntity.getEducation())));
            detailEntities.add(itemInfo);
        }
        if (!TextUtils.isEmpty(mDetailEntity.getWork_area_name())) {
            itemInfo = new ItemInfo();
            itemInfo.setText("工作地");
            itemInfo.setRightText(mDetailEntity.getWork_area_name());
            detailEntities.add(itemInfo);
        }
        if (!TextUtils.isEmpty(mDetailEntity.getBorn_area_name())) {
            itemInfo = new ItemInfo();
            itemInfo.setText("出生地(籍贯)");
            itemInfo.setRightText(mDetailEntity.getBorn_area_name());
            detailEntities.add(itemInfo);
        }
        if (!TextUtils.isEmpty(mDetailEntity.getMarriage_status())) {
            itemInfo = new ItemInfo();
            itemInfo.setText("婚姻状态");
            itemInfo.setRightText(UserUtils.getMarryState(AppUtils.stringToInt(mDetailEntity.getMarriage_status())));
            detailEntities.add(itemInfo);
        }
        if (!TextUtils.isEmpty(mDetailEntity.getNationality())) {
            itemInfo = new ItemInfo();
            itemInfo.setText("民族");
            itemInfo.setRightText(UserUtils.getNation(AppUtils.stringToInt(mDetailEntity.getNationality())));
            detailEntities.add(itemInfo);
        }
        if (!TextUtils.isEmpty(mDetailEntity.getBrother_state())) {
            itemInfo = new ItemInfo();
            itemInfo.setText("姊妹情况");
            itemInfo.setRightText(UserUtils.getBrotherState(AppUtils.stringToInt(mDetailEntity.getBrother_state())));
            detailEntities.add(itemInfo);
        }
        if (!TextUtils.isEmpty(mDetailEntity.getWeight())) {
            itemInfo = new ItemInfo();
            itemInfo.setText("体重");
            itemInfo.setRightText(AppUtils.stringToInt2(mDetailEntity.getWeight()) + "(kg)");
            detailEntities.add(itemInfo);
        }
        if (!TextUtils.isEmpty(mDetailEntity.getParent_work())) {
            itemInfo = new ItemInfo();
            itemInfo.setText("父母工作");
            itemInfo.setRightText(UserUtils.getParentProfession(AppUtils.stringToInt(mDetailEntity.getParent_work())));
            detailEntities.add(itemInfo);
        }
        return this;
    }

    public void build() {
        for (int i = 0, j = detailEntities.size(); i < j; i++) {
            View view = View.inflate(getContext(), R.layout.item_intro, null);
            setupView(detailEntities.get(i), view);
            addView(view);
        }
    }

    private void setupView(ItemInfo itemInfo, View view) {
        TextView leftText = view.findViewById(R.id.left_text);
        TextView rightText = view.findViewById(R.id.right_text);
        leftText.setText(itemInfo.getText());
        rightText.setText(itemInfo.getRightText());
    }

}
