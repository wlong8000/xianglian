
package com.wl.lianba.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wl.lianba.R;

import java.util.ArrayList;
import java.util.List;


public class TabItemLayout extends LinearLayout implements View.OnClickListener {
    private LinearLayout container;

    private List<TabItemView> views = new ArrayList<TabItemView>();

    private TabItemLayoutListener listener;

    public TabItemLayout(Context context) {
        super(context);
    }

    public TabItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        container = (LinearLayout)findViewById(R.id.container);
        super.onFinishInflate();
    }

    public void setupData(ArrayList<String> tabs, int selectedIndex) {
        setupData(tabs, selectedIndex, false);
    }

    public void setupData(ArrayList<String> tabs, int selectedIndex, boolean circleTitle) {
        container.removeAllViews();
        views.clear();
        int i = 0;
        LayoutParams params;
        if (circleTitle) {
            params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 0;
        } else {
            params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
        }
        for (String tab : tabs) {
            TabItemView view = (TabItemView)LayoutInflater.from(getContext()).inflate(
                    R.layout.view_tab_item, null);
            view.setTabName(tab);
            view.setSelected(i == selectedIndex);
            view.setTag(i++);
            view.setOnClickListener(this);
            views.add(view);
            container.addView(view, params);
        }
    }

    public void setItemSelected(int position) {
        for (int i = 0, j = views.size(); i < j; i++) {
            views.get(i).setSelected(i == position);
        }
    }

    public TabItemView getTabItem(int position) {
        if (position >= 0 && position < views.size()) {
            return views.get(position);
        }
        return null;
    }

    public void updateTabNames(ArrayList<String> tabs) {
        if (tabs == null || views == null && tabs.size() != views.size())
            return;
        for (int i = 0, j = views.size(); i < j; i++) {
            views.get(i).setTabName(tabs.get(i));
        }
    }
    public void updateTabNames(ArrayList<String> tabs,ArrayList<String> count) {
        if (tabs == null ||count==null|| views == null && tabs.size() != views.size()&&count.size() != views.size())
            return;
        for (int i = 0, j = views.size(); i < j; i++) {
            views.get(i).setTabName(tabs.get(i));
            views.get(i).setTabCount(count.get(i));
        }
    }

    @Override
    public void onClick(View view) {
        try {
            if (listener != null)
                listener.onItemClicked((Integer)view.getTag());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTabItemLayoutListener(TabItemLayoutListener listener) {
        this.listener = listener;
    }

    public interface TabItemLayoutListener {
        void onItemClicked(int position);
    }
}
