package base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.wl.lianba.R;

/**
 * Created by wanglong on 17/8/20.
 */

public abstract class EditDialog extends BaseDialog implements View.OnClickListener {
    private EditText mEditView;

    public EditDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit);
        mEditView = (EditText) findViewById(R.id.edit);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.confirm).setOnClickListener(this);

        resetWidth();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                cancel();
                break;
            case R.id.confirm:
                cancel();
                onConfirm(mEditView.getText().toString());
                break;
        }
    }

    public abstract void onConfirm(String editText);
}
