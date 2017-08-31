
package com.xianglian.love.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.xianglian.love.R;
import com.xianglian.love.wheel.BaseDialog;
import com.xianglian.love.wheel.adapters.AbstractWheelTextAdapter;
import com.xianglian.love.wheel.widget.WheelView;

import java.util.List;

/**
 * 一级选择
 */
public abstract class FirstChooseDialog extends BaseDialog implements View.OnClickListener {

    private WheelView mWheelView;

    private List<String> mData;

    protected FirstChooseDialog(Context context, List<String> data) {
        super(context);
        this.mData = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_data_choose);
        mWheelView = (WheelView) findViewById(R.id.wheel);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.confirm).setOnClickListener(this);

        mWheelView.setViewAdapter(new FirstAdapter(getContext(), mData));
        mWheelView.setVisibleItems(5);

        mWheelView.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0, j = mData.size(); i < j; i++) {
                    String model = mData.get(i);
                    if (model != null) {
                        mWheelView.setCurrentItem(i);
                        return;
                    }
                }
            }
        });

        resetWidth();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                cancel();
                String text = mData.get(mWheelView.getCurrentItem());
                if (!TextUtils.isEmpty(text)) {
                    onConfirm(text);
                }
                cancel();
                break;
            case R.id.cancel:
                cancel();
                break;
        }
    }

    public abstract void onConfirm(String url);

    private class FirstAdapter extends AbstractWheelTextAdapter {
        private List<String> data;

        private FirstAdapter(Context context, List<String> data) {
            super(context, R.layout.item_location_wheel);
            this.data = data;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return data.get(index) != null ? data.get(index) : "";
        }

        @Override
        public int getItemsCount() {
            return data == null ? 0 : data.size();
        }
    }
}
