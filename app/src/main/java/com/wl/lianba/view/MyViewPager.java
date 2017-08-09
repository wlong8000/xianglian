
package com.wl.lianba.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

public class MyViewPager extends ViewPager {
    private int scrollViewX;

    private OnViewPagerTouchUpListener listener;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (listener != null)
                listener.onTouchUp();
        }
        try {
            return super.onTouchEvent(event);
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            return super.onInterceptTouchEvent(event);
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof HorizontalScrollView) {
            if (v.getScrollX() == 0) {
                return super.canScroll(v, checkV, dx, x, y);
            } else if (v.getScrollX() - scrollViewX < 2 && v.getScrollX() >= scrollViewX) {
                return super.canScroll(v, checkV, dx, x, y);
            } else {
                scrollViewX = v.getScrollX();
                return true;
            }
        }
        return super.canScroll(v, checkV, dx, x, y);
    }

    public void setOnViewPagerTouchUpListener(OnViewPagerTouchUpListener listener) {
        this.listener = listener;
    }

    public interface OnViewPagerTouchUpListener {
        void onTouchUp();
    }

}
