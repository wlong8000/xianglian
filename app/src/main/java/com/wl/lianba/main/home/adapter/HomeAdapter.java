package com.wl.lianba.main.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wl.lianba.R;
import com.wl.lianba.config.Config;
import com.wl.lianba.main.home.PersonDetailActivity;
import com.wl.lianba.main.home.been.PersonInfo;
import com.wl.lianba.multirecyclerview.adapter.BaseAdapter;
import com.wl.lianba.multirecyclerview.adapter.BaseViewHolder;
import com.wl.lianba.user.LoginActivity;
import com.wl.lianba.user.UserInfoEditActivity;
import com.wl.lianba.utils.AppSharePreferences;
import com.wl.lianba.utils.AppUtils;

import java.util.List;

/**
 * Created by wanglong on 17/3/12.
 *
 */

public class HomeAdapter extends BaseAdapter {
    private SimpleDraweeView mImageView;
    private TextView mName;

    private Context mContext;
    private List<PersonInfo> mPersons;

    public HomeAdapter(Context context) {
        this.mContext = context;
    }

    public void setInfo(List<PersonInfo> personInfo) {
        this.mPersons = personInfo;
    }

    @Override
    public void onBindView(BaseViewHolder holder, int position) {
        if (mPersons == null || mPersons.size() == 0) return;
        PersonInfo info = mPersons.get(position);
        if (info == null) return;
        mImageView = holder.getView(R.id.img);
        mName = holder.getView(R.id.tv_name);
        mImageView.setAspectRatio(1);

        Uri uri = AppUtils.parse(info.getAvatar());
        mImageView.setImageURI(uri);

        mName.setText(info.getNick_name());
    }

    @Override
    public int getLayoutID(int position) {
        return R.layout.item_home_main;
    }

    @Override
    public boolean clickable() {
        return true;
    }

    @Override
    public int getItemCount() {
        return mPersons != null ? mPersons.size() : 0;
    }

    @Override
    public void onItemClick(View v, int position) {
        super.onItemClick(v, position);
        boolean isEditInfo = AppSharePreferences.getBoolValue(mContext, AppSharePreferences.USER_INFO);
        if (!AppUtils.isLogin()) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
        } else if (!isEditInfo) {
            mContext.startActivity(new Intent(mContext, UserInfoEditActivity.class));
        } else {
            Intent intent = new Intent(mContext, PersonDetailActivity.class);
            PersonInfo info = mPersons.get(position);
            if (info != null)
//                intent.putExtra(Config.USER_INFO_KEY, info);
            mContext.startActivity(intent);
        }

    }
}
