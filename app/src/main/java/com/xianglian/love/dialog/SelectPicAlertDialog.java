
package com.xianglian.love.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.xianglian.love.R;
import com.xianglian.love.wheel.BaseDialog;


/**
 * 选择图片
 */
public abstract class SelectPicAlertDialog extends BaseDialog implements View.OnClickListener {

    private TextView mTakePhotoView;

    private TextView mGalleyView;

    private TextView mCancelView;

    private String mTitle;

    private Window window = this.getWindow();

    protected SelectPicAlertDialog(Context context, String title) {
        super(context);
        this.mTitle = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_selectpic);
        findView();
        initData();
        initEvent();
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        int width = getContext().getResources().getDisplayMetrics().widthPixels * 2 / 3;
        if (width > 800)
            width = 800;
        lp.width = width;
        dialogWindow.setAttributes(lp);
        resetWidth();
    }

    private void initEvent() {
        mTakePhotoView.setOnClickListener(this);
        mGalleyView.setOnClickListener(this);
        mCancelView.setOnClickListener(this);
        setCanceledOnTouchOutside(true);
    }

    private void initData() {
    }

    private void findView() {
        mTakePhotoView = (TextView)findViewById(R.id.takephoto);
        mGalleyView = (TextView)findViewById(R.id.usenativegalley);
        mCancelView = (TextView)findViewById(R.id.cancelphoto);
    }

    /**
     * 设置对话框位置
     * 
     * @param x
     * @param y
     */
    private void setDialogPosition(int x, int y) {

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.x = x;
        params.y = y;
        window.setAttributes(params);
    }

    public abstract void takePhoto();

    public abstract void useNativeGalley();

    public void alertDismiss() {
        this.dismiss();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.takephoto) {
            takePhoto();
        } else if (v.getId() == R.id.usenativegalley) {
            useNativeGalley();
        } else if (v.getId() == R.id.cancelphoto) {
            alertDismiss();
        }
        cancel();
    }

}
