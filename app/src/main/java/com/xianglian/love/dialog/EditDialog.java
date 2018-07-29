
package com.xianglian.love.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.xianglian.love.R;
import com.xianglian.love.wheel.BaseDialog;

/**
 * 目前用到 修改昵称
 */
public abstract class EditDialog extends BaseDialog implements View.OnClickListener {
    private EditText mEditView;

    protected EditDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_data_edit);
        mEditView = findViewById(R.id.edit);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.confirm).setOnClickListener(this);

        resetWidth();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                cancel();
                String text = mEditView.getText().toString();
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

    public abstract void onConfirm(String editText);
}
