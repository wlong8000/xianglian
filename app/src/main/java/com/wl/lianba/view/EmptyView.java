
package com.wl.lianba.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wl.lianba.R;


public class EmptyView extends LinearLayout {
    // private ProgressWheel mProgressBar;
    private TextView mTextView;

    private ImageView mProgressBar;

    private ImageView mIconImage;

    private int bgRes;

    private View bottomView;

    private LinearLayout empty_layout;

    private ViewStub newtwork;

    private boolean isInflate;

    public EmptyView(Context context) {
        super(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        mTextView = (TextView)findViewById(R.id.view_list_empty_text);
        mProgressBar = (ImageView)findViewById(R.id.view_list_empty_progress);
        mIconImage = (ImageView)findViewById(R.id.icon);
        bottomView = findViewById(R.id.bottom_view);
        empty_layout = (LinearLayout)findViewById(R.id.empty_layout);
        newtwork = (ViewStub)findViewById(R.id.newtwork);
        // mProgressBar.setText("loading");
        // mProgressBar.spin();
        super.onFinishInflate();
    }

    public void onLoading() {
        if (mIconImage.getVisibility() != View.VISIBLE)
            mIconImage.setVisibility(View.VISIBLE);
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);
        if (mTextView != null)
            mTextView.setVisibility(View.GONE);
        if (bgRes != 0)
            setBackgroundResource(bgRes);
        else
            setBackgroundColor(getResources().getColor(R.color.lib_color_bg2));
    }

    public void onLoading(String desc) {
        if (mIconImage.getVisibility() != View.GONE)
            mIconImage.setVisibility(View.GONE);
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);
        if (mTextView != null) {
            if (mTextView.getVisibility() != VISIBLE)
                mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(desc);
        }

        if (bgRes != 0)
            setBackgroundResource(bgRes);
        else
            setBackgroundColor(getResources().getColor(R.color.lib_color_bg2));
    }

    public void onEmpty() {
        show(true);
        if (mIconImage.getVisibility() != View.GONE)
            mIconImage.setVisibility(View.GONE);
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.GONE);
        if (mTextView != null) {
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(getContext().getString(R.string.no_data));
        }
        if (bgRes != 0)
            setBackgroundResource(bgRes);
        else
            setBackgroundColor(getResources().getColor(R.color.lib_color_bg2));
    }

    public void onEmptyWithBackgroud(int res) {
        if (mIconImage.getVisibility() != View.GONE)
            mIconImage.setVisibility(View.GONE);
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.GONE);
        if (mTextView != null) {
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText("");
            mTextView.setBackgroundResource(res);
        }
        if (bgRes != 0)
            setBackgroundResource(bgRes);
        else
            setBackgroundColor(getResources().getColor(R.color.lib_color_bg2));
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void showBottom(boolean show) {
        // bottomView.setVisibility(show ? VISIBLE : GONE);
    }

    public void onEmpty(String content) {
        onEmpty();
        if (mTextView != null) {
            mTextView.setText(content);
        }
        if (bgRes != 0)
            setBackgroundResource(bgRes);
        else
            setBackgroundColor(getResources().getColor(R.color.lib_color_bg2));
    }

    public void onFailed(String requestContent) {
        if (mIconImage.getVisibility() != View.GONE)
            mIconImage.setVisibility(View.GONE);
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.GONE);
        if (mTextView != null) {
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(requestContent);
        }
    }

    public void setEmptyBackground(int res) {
        this.bgRes = res;
    }

    /*
     * public void onFailed(){ onFailed("请求数据失败，请稍后再试！"); }
     */

    public void show(boolean show) {
        if (newtwork != null)
            newtwork.setVisibility(GONE);
        empty_layout.setVisibility(VISIBLE);
        setVisibility(show ? VISIBLE : GONE);
        if (bgRes != 0) {
            if (show)
                setBackgroundResource(bgRes);
            else
                setBackgroundColor(getResources().getColor(R.color.lib_color_bg2));
        }
    }

    public void showLoading(boolean show) {
        setVisibility(show ? VISIBLE : GONE);
        if (show) {
            setBackgroundResource(bgRes);
            empty_layout.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.GONE);
            mIconImage.setVisibility(View.VISIBLE);
        } else
            setBackgroundColor(getResources().getColor(R.color.lib_color_bg2));
    }

    public void showNetworkNotGoodStatus(boolean show) {
        show(true);
        if (show) {
            if (!isInflate) {
                newtwork.inflate();
                isInflate = true;
            }
            newtwork.setVisibility(View.VISIBLE);
            empty_layout.setVisibility(View.GONE);
        } else {
            newtwork.setVisibility(View.GONE);
            empty_layout.setVisibility(View.VISIBLE);
            if (mIconImage.getVisibility() != View.VISIBLE)
                mIconImage.setVisibility(View.VISIBLE);
            if (mProgressBar != null)
                mProgressBar.setVisibility(View.VISIBLE);
            if (mTextView != null)
                mTextView.setVisibility(View.GONE);
            if (bgRes != 0)
                setBackgroundResource(bgRes);
        }
    }

//    public void showNetworkNotGoodStatus(String message, int resDrawable) {
//        show(true);
//        if (!isInflate) {
//            newtwork.inflate();
//            isInflate = true;
//        }
//
//        newtwork.setVisibility(View.VISIBLE);
//        empty_layout.setVisibility(View.GONE);
//        TextView tv = (TextView)findViewById(R.id.message);
//        tv.setText(message);
//        ImageView img = (ImageView)findViewById(R.id.image);
//        img.setImageResource(resDrawable);
//    }

    /**
     * 一张图片,一行文字,空白页面
     * 
     * @param
     * @param
     */
//    public void showNothingData(String message, int resDrawable) {
//        showNetworkNotGoodStatus(message, resDrawable);
//        findViewById(R.id.refresh).setVisibility(View.GONE);
//    }

    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }
}
