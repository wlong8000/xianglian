
package com.wl.lianba.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *
 */
public class MyRecyclerView extends RecyclerView {
    private LayoutManager layout;

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (layout == null)
            return false;
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (layout == null)
            return false;
        return super.onTouchEvent(e);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        this.layout = layout;
        super.setLayoutManager(layout);
    }
}
