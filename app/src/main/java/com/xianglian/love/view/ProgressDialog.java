
package com.xianglian.love.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.xianglian.love.R;

/**
 * Created by hujinghui on 14-10-13.
 */
public class ProgressDialog extends Dialog {


    ProgressImageView mProgressView;

    public ProgressDialog(Context context) {
        super(context, R.style.DialogStyle);
    }

    public ProgressDialog(Context context, boolean cancelable) {
        super(context, R.style.DialogStyle);
        setCancelable(cancelable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        mProgressView = findViewById(R.id.view_list_empty_progress);

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mProgressView != null)
            mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mProgressView != null)
            mProgressView.setVisibility(View.GONE);
    }
}
