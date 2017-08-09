package com.wl.lianba.main.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wl.lianba.R;
import com.wl.lianba.ShowPicActivity;
import com.wl.lianba.library.tag.TagBaseAdapter;
import com.wl.lianba.library.tag.TagCloudLayout;
import com.wl.lianba.main.home.been.PersonInfo;
import com.wl.lianba.main.home.been.PhotoInfo;
import com.wl.lianba.view.AlumView;
import com.wl.lianba.view.FavoriteView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wanglong on 17/3/12.
 * 详情页
 */

public class PersonDetailAdapter extends RecyclerView.Adapter implements AlumView.OnItemClickListener{

    private Context mContext;

    private List<PersonInfo> mInfoList;

    private List<PhotoInfo> mInfo;

    public PersonDetailAdapter(Context context) {
        this.mContext = context;
    }

    public List<PersonInfo> getInfo() {
        if (mInfoList == null)
            mInfoList = new ArrayList<>();
        return mInfoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        switch (viewType) {
            case PersonInfo.ViewType.TOP_INFO: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_detail_info, null);
                view.setLayoutParams(lp);
                return new TopInfoHolder(view);
            }
            case PersonInfo.ViewType.INTRODUCE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_detail_introduce, null);
                view.setLayoutParams(lp);
                return new IntroduceHolder(view);
            }
            case PersonInfo.ViewType.EXPERIENCE_EMOTION: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_detail_introduce, null);
                view.setLayoutParams(lp);
                return new IntroduceHolder(view);
            }
            case PersonInfo.ViewType.MARK: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_detail_fav, null);
                view.setLayoutParams(lp);
                return new FavHolder(view);
            }
            case PersonInfo.ViewType.FAVORITE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_detail_fav, null);
                view.setLayoutParams(lp);
                return new FavHolder(view);
            }
            case PersonInfo.ViewType.ALBUM: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_detail_alum, null);
                view.setLayoutParams(lp);
                return new AlumHolder(view);
            }
            case PersonInfo.ViewType.BASE_INFO: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_detail_base_info, null);
                view.setLayoutParams(lp);
                return new BaseInfoHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case PersonInfo.ViewType.TOP_INFO: {
                break;
            }
            case PersonInfo.ViewType.INTRODUCE: {
                PersonInfo info = getItem(position);
                IntroduceHolder introduceHolder = (IntroduceHolder) holder;
                if (info == null || TextUtils.isEmpty(info.getIntroduce())) {

                } else {
                    introduceHolder.mName.setText(info.getIntroduce());
                }
                break;
            }
            case PersonInfo.ViewType.EXPERIENCE_EMOTION: {
                PersonInfo info = getItem(position);
                IntroduceHolder introduceHolder = (IntroduceHolder) holder;
                if (info == null || TextUtils.isEmpty(info.getExperience())) {

                } else {
                    introduceHolder.mName.setText(info.getExperience());
                }
                break;
            }
            case PersonInfo.ViewType.MARK: {
//                PersonInfo info = getItem(position);
//                if (info == null || info.getMark() == null) return;
//                FavHolder favHolder = (FavHolder) holder;
//                favHolder.mFavoriteView.initAdapter(0);
//                favHolder.mFavoriteView.setData(info.getMark());
                break;
            }
            case PersonInfo.ViewType.FAVORITE: {
//                PersonInfo info = getItem(position);
//                if (info == null || info.getHobby() == null) return;
//                FavHolder favHolder = (FavHolder) holder;
//                favHolder.mFavoriteView.initAdapter(0);
//                favHolder.mFavoriteView.setData(info.getHobby());
                break;
            }
            case PersonInfo.ViewType.ALBUM: {
                PersonInfo info = getItem(position);
                if (info == null || info.getAlbum() == null) return;
                AlumHolder alumHolder = (AlumHolder) holder;
                alumHolder.alumView.setOnItemClickListener(this);
                alumHolder.alumView.initAdapter(4);
                mInfo = info.getAlbum();
                alumHolder.alumView.setData(info.getAlbum());
                break;
            }
            case PersonInfo.ViewType.BASE_INFO: {
                BaseInfoHolder baseInfoHolder = (BaseInfoHolder) holder;
                List<String> mList = new ArrayList<>();
                mList.add("出生日期 / 1989-11-15");
                mList.add("星座 / 金牛座");
                mList.add("身高 / 158");
                mList.add("年收入 / 10w-20w");
                mList.add("工作生活在 / 廊坊");
                TagBaseAdapter mAdapter = new TagBaseAdapter(mContext, mList);
                baseInfoHolder.mTagLayout.setAdapter(mAdapter);
                break;
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) == null)
            return -1;
        return getItem(position).getViewType();

    }

    public PersonInfo getItem(int position) {
        if (position < 0 || position >= mInfoList.size())
            return null;
        return mInfoList.get(position);
    }

    @Override
    public int getItemCount() {
        return mInfoList != null ? mInfoList.size() : 0;
    }

    @Override
    public void onItemClick(int position, PhotoInfo data) {
        if (mInfo == null) return;
        String[] arr = new String[mInfo.size()];
        for (int i = 0; i < mInfo.size(); i++) {
            arr[i] = mInfo.get(i).getPath();
        }
        ShowPicActivity.showPictures(mContext, arr, position);
    }

    private static class TopInfoHolder extends RecyclerView.ViewHolder {
        private TextView mName;

        public TopInfoHolder(View itemView) {
            super(itemView);
        }
    }

    private static class IntroduceHolder extends RecyclerView.ViewHolder {
        private TextView mName;

        public IntroduceHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.introduce);
        }
    }

    private static class FavHolder extends RecyclerView.ViewHolder {
        private FavoriteView mFavoriteView;

        FavHolder(View itemView) {
            super(itemView);
            mFavoriteView = (FavoriteView) itemView.findViewById(R.id.favorite_layout);
        }
    }

    private static class AlumHolder extends RecyclerView.ViewHolder {
        private AlumView alumView;

        public AlumHolder(View itemView) {
            super(itemView);
            alumView = (AlumView) itemView.findViewById(R.id.alum_layout);
        }
    }

    private class BaseInfoHolder extends RecyclerView.ViewHolder {
        TagCloudLayout mTagLayout;

        public BaseInfoHolder(View itemView) {
            super(itemView);
            mTagLayout = (TagCloudLayout) itemView.findViewById(R.id.tab_container);
        }
    }
}
