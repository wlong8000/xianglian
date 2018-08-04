package base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xianglian.love.R;

/**
 * Created by wanglong on 17/8/20.
 */

public class GuideDialog extends BaseDialog implements View.OnClickListener {
    private TextView mTextView;

    public GuideDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_guide);
        mTextView = findViewById(R.id.text);
        mTextView.setText(R.string.exit_count_dialog);
//        Button okBtn = findViewById(R.id.confirm);
//        okBtn.setText(R.string.ok);
        findViewById(R.id.cancel).setOnClickListener(this);
//        okBtn.setOnClickListener(this);

        resetWidth();
    }

    public void setTitle(String title) {
        mTextView.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                cancel();
                break;
//            case R.id.confirm:
//                cancel();
//                onConfirm(null);
//                break;
        }
    }

//    public abstract void onConfirm(String result);
}
