package com.xianglian.love.user.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class CommonObjectAdapter extends BaseAdapter {

    private List<Object> mData;

    private CommonAdapterCallBack mCommonAdapterCallBack;

    public CommonObjectAdapter(List<Object> data) {
        this.mData = data;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return super.getItemViewType(position);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mData.get(position);
    }

    public void addItem(final Object o) {
        this.mData.add(o);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        return mCommonAdapterCallBack.getItemView(this.mData, position, convertView, parent);
    }

    /**  */
    public interface CommonAdapterCallBack {
        /***/
        View getItemView(List<Object> data, final int position, View convertView, ViewGroup parent);
    }

    public CommonAdapterCallBack getCommonAdapterCallBack() {
        return mCommonAdapterCallBack;
    }

    public void setCommonAdapterCallBack(CommonAdapterCallBack commonAdapterCallBack) {
        this.mCommonAdapterCallBack = commonAdapterCallBack;
    }

    public void updateListView(List<Object> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

}
