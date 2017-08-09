package com.wl.lianba.user.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class CommonObjectAdapter extends BaseAdapter {

	private List<Object> data;

	private CommonAdapterCallBack commonAdapterCallBack;

	public CommonObjectAdapter(List<Object> data) {

		this.data = data;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}

	@Override
	public int getCount() {
		return data==null?0:data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	public void addItem(final Object o) {
		this.data.add(o);
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
		return commonAdapterCallBack.getItemView(this.data, position, convertView, parent);
	}

	/**  */
	public interface CommonAdapterCallBack {
		/***/
		View getItemView(List<Object> data, final int position, View convertView, ViewGroup parent);
	}

	public CommonAdapterCallBack getCommonAdapterCallBack() {
		return commonAdapterCallBack;
	}

	public void setCommonAdapterCallBack(CommonAdapterCallBack commonAdapterCallBack) {
		this.commonAdapterCallBack = commonAdapterCallBack;
	}

	public void updateListView(List<Object> data) {
		this.data = data;
		notifyDataSetChanged();
	}

}
