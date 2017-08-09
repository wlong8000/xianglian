
package com.wl.lianba.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;

/**
 * Created by hujinghui on 15/2/3.
 */
public class FlingLeftViewPager extends ViewPager {

    private OnPageChangeListener onPageChangeListener;
    private OnFlingLeftViewPagerListener onFlingLeftViewPagerListener;
    private boolean invalid;
    private int mOutLimitX;
    private float mDown = -1;
    private float mMove = 0;
    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            if (onPageChangeListener != null)
                onPageChangeListener.onPageSelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case 0:
                    if (!invalid && mOutLimitX<Math.abs(mMove)&&mMove<0 && onFlingLeftViewPagerListener != null
                            && getCurrentItem() == 0) {
                        onFlingLeftViewPagerListener.onFlingLeft();
                    }
                    break;
                case 1:
                    invalid = false;
                    break;
                case 2:
                    invalid = true;
                    break;
            }
            if (onPageChangeListener != null)
                onPageChangeListener.onPageScrollStateChanged(state);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (onPageChangeListener != null)
                onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    };

    public FlingLeftViewPager(Context context) {
        super(context);
        init();
    }

    public FlingLeftViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        WindowManager windowManager = (WindowManager)getContext().getSystemService(
                Context.WINDOW_SERVICE);
        int outSize = windowManager.getDefaultDisplay().getWidth();
        mOutLimitX = outSize / 3;
        super.setOnPageChangeListener(mOnPageChangeListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP: {
                mMove = mDown-ev.getX();
                mDown=-1;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if(mDown==-1){
                    mDown = ev.getX();
                }
                break;
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.onPageChangeListener = listener;
    }

    public void setOnFlingLeftViewPagerListener(OnFlingLeftViewPagerListener listener) {
        onFlingLeftViewPagerListener = listener;
    }

    public interface OnFlingLeftViewPagerListener {
        void onFlingLeft();
    }
}
