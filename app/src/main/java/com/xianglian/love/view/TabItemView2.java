
package com.xianglian.love.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xianglian.love.R;


public class TabItemView2 extends FrameLayout {
    private final String tag = "TabItemView";

    TextView mTabNum;

    TextView mTabName;

    TextView mTabCount;

    ImageView mTabLine;
    private String itemName;
    private String itemCount;
    private boolean selected;

    public TabItemView2(Context context) {
        super(context);
        init(null, 0);
    }

    public TabItemView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TabItemView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
//        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TabItemView2,
//                defStyle, 0);
//        if (a.hasValue(R.styleable.TabItemView2_name))
//            itemName = a.getString(R.styleable.TabItemView2_name);
//        selected = a.getBoolean(R.styleable.TabItemView2_selected, false);
//        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        mTabNum = findViewById(R.id.tab_num);
        mTabCount = findViewById(R.id.tab_count);
        mTabLine = findViewById(R.id.tab_line);
        mTabName = findViewById(R.id.tab_name);
        if (!TextUtils.isEmpty(itemName))
            mTabName.setText(itemName);
        if (!TextUtils.isEmpty(itemCount))
            mTabCount.setText(itemCount);

        mTabLine.setVisibility(selected ? VISIBLE : INVISIBLE);
        super.onFinishInflate();
    }

    public void setTabName(String name) {
        itemName = name;
        if (mTabName != null)
            mTabName.setText(name);
    }
    public void setTabCount(String count) {
        itemCount = count;
        if (mTabCount != null){
            mTabCount.setText(count);
            mTabCount.setVisibility(View.VISIBLE);
        }

    }

    public void setTabNum(String num){
        if (mTabNum != null)
            mTabNum.setText(num);
    }

    public TextView getTabNumView(){
        return mTabNum;
    }

    public TextView getTextView() {
        return mTabName;
    }

    public void setTabName(int res) {
        setTabName(getResources().getString(res));
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (mTabLine != null)
            mTabLine.setVisibility(selected ? VISIBLE : INVISIBLE);
        if (mTabName != null)
            mTabName.setSelected(selected);
        if (mTabCount != null)
            mTabCount.setSelected(selected);
    }
}
