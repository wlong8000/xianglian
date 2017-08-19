package com.wl.lianba.view;

import android.content.Context;
import android.util.AttributeSet;

import com.wl.lianba.utils.AppUtils;

import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;

/**
 * Created by wanglong on 17/3/12.
 */

public class MyPagerBottomTabLayout extends PagerBottomTabLayout {
    private Context mContext;

    public MyPagerBottomTabLayout(Context context) {
        super(context);
        this.mContext = context;
    }

    public MyPagerBottomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public MyPagerBottomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), AppUtils.dip2px(mContext, 50));
    }
}
