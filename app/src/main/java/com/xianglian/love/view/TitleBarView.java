package com.xianglian.love.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xianglian.love.R;

/**
 * Created by wanglong on 17/3/2.
 * title封装
 */

public class TitleBarView extends FrameLayout {
    private Context mContext;

    private LinearLayout mLeftLayout;

    private LinearLayout mRightLayout;

    private TextView mTitle;

    private TextView mSubTitle;

    private LayoutInflater layoutInflater;

    private View mBottomLine;

    private ImageView mTitleImage;

    private OnTitleClickListener mOnTitleClickListener;


    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitle = findViewById(R.id.view_titlebar_title);
        mSubTitle = findViewById(R.id.view_titlebar_sub_title);
        mLeftLayout = findViewById(R.id.view_titlebar_left_layout);
        mRightLayout = findViewById(R.id.view_titlebar_right_layout);
        mTitleImage = findViewById(R.id.view_titlebar_title_img);
        mBottomLine = findViewById(R.id.bottom_line);
        layoutInflater = LayoutInflater.from(getContext());
    }

    public void setupLeftImg(int leftRes) {
        if (leftRes != 0) {
            mLeftLayout.removeAllViews();
            ImageView view = (ImageView) layoutInflater.inflate(
                    R.layout.view_titlebar_right_img_btn, null);
            view.setImageResource(leftRes);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnTitleClickListener.leftClick();
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            mLeftLayout.addView(view, params);
        }
    }

    public void setupRightImg(int rightRes) {
        if (rightRes != 0) {
            mRightLayout.removeAllViews();
            ImageView view = (ImageView) layoutInflater.inflate(
                    R.layout.view_titlebar_right_img_btn, null);
            view.setImageResource(rightRes);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnTitleClickListener.rightClick();
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            mRightLayout.addView(view, params);
        }
    }

    public void setupRightText(String text) {
        if (!TextUtils.isEmpty(text)) {
            TextView btn = (TextView) layoutInflater
                    .inflate(R.layout.view_title_bar_right_text_btn, null);
            btn.setText(text);
            btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnTitleClickListener.rightClick();
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            mRightLayout.addView(btn, params);
        }
    }

    public void setRightLayoutVisible(int visible) {
        mRightLayout.setVisibility(visible);
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
            mTitle.setVisibility(View.VISIBLE);
            mTitleImage.setVisibility(View.INVISIBLE);
        } else {
            mTitle.setVisibility(View.GONE);
            mTitleImage.setVisibility(View.VISIBLE);
        }

        mSubTitle.setVisibility(View.GONE);
    }

    public void setTitle(String title, int textSize, int textColor) {
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
            mTitle.setVisibility(View.VISIBLE);
            mTitleImage.setVisibility(View.INVISIBLE);
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(textSize));
            mTitle.setTextColor(getResources().getColor(textColor));
        } else {
            mTitle.setVisibility(View.GONE);
            mTitleImage.setVisibility(View.VISIBLE);
        }

        mSubTitle.setVisibility(View.GONE);
    }

    public interface OnTitleClickListener {
        void leftClick();

        void rightClick();
    }

    public void setTitleClickListener(OnTitleClickListener onTitleClickListener) {
        mOnTitleClickListener = onTitleClickListener;
    }

}
