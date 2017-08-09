package com.wl.lianba.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wanglong on 16/10/10.
 * 设置RecyclerView->item间距(CustomRankingsView在用)
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

	private int mSpace;

	private int mSpanCount;

	/**
	 * @param context
	 * @param space 传入的值，其单位视为dp
	 * @param spanCount 单行显示的数量
	 */
	public SpaceItemDecoration(Context context, int space, int spanCount) {
		this.mSpace = AppUtils.dip2px(context, space);
		this.mSpanCount = spanCount;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		int pos = parent.getChildAdapterPosition(view);
		int totalChildCount = 0;
		if(parent.getAdapter() != null)
			totalChildCount = parent.getAdapter().getItemCount();
		outRect.left = mSpace;
		outRect.top = mSpace;
		//每行的最后一个设置10dp padding
		if ((pos + 1) % mSpanCount == 0) {
			outRect.right = mSpace;
		} else {
			outRect.right = 0;
		}

//		if (totalChildCount - pos <= mSpanCount) {
//			outRect.bottom = mSpace;
//		} else
//			outRect.bottom = 0;
	}
}
