package com.wl.lianba.main.home.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wl.lianba.R;
import com.wl.lianba.main.home.been.UserDetailEntity;
import com.wl.lianba.view.AlumView;

import java.util.List;


/**
 * Created by wanglong on 17/3/12.
 * 详情页
 */

public class PersonDetailAdapter extends BaseMultiItemQuickAdapter<UserDetailEntity, BaseViewHolder> {

    private Context mContext;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PersonDetailAdapter(Context context, List<UserDetailEntity> data) {
        super(data);
        this.mContext = context;
        addItemType(UserDetailEntity.ViewType.TOP_INFO, R.layout.item_home_detail_info);
        addItemType(UserDetailEntity.ViewType.INTRODUCE, R.layout.item_home_detail_introduce);
        addItemType(UserDetailEntity.ViewType.EXPERIENCE_EMOTION, R.layout.item_home_detail_introduce);
        addItemType(UserDetailEntity.ViewType.MARK, R.layout.item_home_detail_fav);
        addItemType(UserDetailEntity.ViewType.FAVORITE, R.layout.item_home_detail_fav);
        addItemType(UserDetailEntity.ViewType.ALBUM, R.layout.item_home_detail_alum);
        addItemType(UserDetailEntity.ViewType.BASE_INFO, R.layout.item_home_detail_base_info);
        addItemType(UserDetailEntity.ViewType.LEAVE_MESSAGE, R.layout.item_leave_message);
        addItemType(UserDetailEntity.ViewType.TITLE, R.layout.item_detail_title);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserDetailEntity result) {
        UserDetailEntity item = result.getResult();
        switch (helper.getItemViewType()) {
            case UserDetailEntity.ViewType.TOP_INFO:
                helper.setText(R.id.name, item.getNickname());
                helper.setText(R.id.tv_locate, item.getWork_area_name());
                helper.setText(R.id.tv_id, item.getAccount());
                helper.setImageVisible(R.id.ver, item.isIdentity_verified());
                setBaseInfo(helper, item);
                break;
            case UserDetailEntity.ViewType.INTRODUCE:
                helper.setText(R.id.introduce, item.getPerson_intro());
                break;
            case UserDetailEntity.ViewType.EXPERIENCE_EMOTION:
                helper.setText(R.id.introduce, item.getRelationship_desc());
                break;
            case UserDetailEntity.ViewType.MARK:
                break;
            case UserDetailEntity.ViewType.FAVORITE:
                break;
            case UserDetailEntity.ViewType.ALBUM:
                AlumView alumView = helper.getView(R.id.alum_layout);
                alumView.initAdapter(4);
                alumView.setData(item.getAlbums());
                break;
            case UserDetailEntity.ViewType.BASE_INFO:
                break;
            case UserDetailEntity.ViewType.TITLE:
                break;
            case UserDetailEntity.ViewType.LEAVE_MESSAGE:
                break;
        }
    }

    private void setBaseInfo(BaseViewHolder helper, UserDetailEntity item) {
        StringBuilder builder = new StringBuilder();
        builder.append(item.getAge()).append(mContext.getString(R.string.age2)).append(" ");
        builder.append(mContext.getString(R.string.height2)).append(" ").append(item.getHeight()).append(" ");
        builder.append(item.getCareer()).append(" ");
        builder.append(mContext.getString(R.string.income)).append(" ").append(item.getIncome());
        helper.setText(R.id.tv_user_head_info, builder.toString());
    }

//    private List<UserDetailEntity> mInfoList;
//
//    private List<PhotoInfo> mInfo;
//
//    public PersonDetailAdapter(Context context) {
//        this.mContext = context;
//    }
//
//    public List<UserDetailEntity> getInfo() {
//        if (mInfoList == null)
//            mInfoList = new ArrayList<>();
//        return mInfoList;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view;
//        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        switch (viewType) {
//            case UserDetailEntity.ViewType.TOP_INFO: {
//                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_detail_info, null);
//                view.setLayoutParams(lp);
//                return new TopInfoHolder(view);
//            }
//            case UserDetailEntity.ViewType.INTRODUCE: {
//                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_detail_introduce, null);
//                view.setLayoutParams(lp);
//                return new IntroduceHolder(view);
//            }
//            case UserDetailEntity.ViewType.EXPERIENCE_EMOTION: {
//                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_detail_introduce, null);
//                view.setLayoutParams(lp);
//                return new IntroduceHolder(view);
//            }
//            case UserDetailEntity.ViewType.MARK: {
//                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_detail_fav, null);
//                view.setLayoutParams(lp);
//                return new FavHolder(view);
//            }
//            case UserDetailEntity.ViewType.FAVORITE: {
//                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_detail_fav, null);
//                view.setLayoutParams(lp);
//                return new FavHolder(view);
//            }
//            case UserDetailEntity.ViewType.ALBUM: {
//                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_detail_alum, null);
//                view.setLayoutParams(lp);
//                return new AlumHolder(view);
//            }
//            case UserDetailEntity.ViewType.BASE_INFO: {
//                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_detail_base_info, null);
//                view.setLayoutParams(lp);
//                return new BaseInfoHolder(view);
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        switch (getItemViewType(position)) {
//            case UserDetailEntity.ViewType.TOP_INFO: {
//                break;
//            }
//            case UserDetailEntity.ViewType.INTRODUCE: {
//                UserDetailEntity info = getItem(position);
//                IntroduceHolder introduceHolder = (IntroduceHolder) holder;
//                if (info == null || TextUtils.isEmpty(info.getIntroduce())) {
//
//                } else {
//                    introduceHolder.mName.setText(info.getIntroduce());
//                }
//                break;
//            }
//            case UserDetailEntity.ViewType.EXPERIENCE_EMOTION: {
//                UserDetailEntity info = getItem(position);
//                IntroduceHolder introduceHolder = (IntroduceHolder) holder;
//                if (info == null || TextUtils.isEmpty(info.getExperience())) {
//
//                } else {
//                    introduceHolder.mName.setText(info.getExperience());
//                }
//                break;
//            }
//            case UserDetailEntity.ViewType.MARK: {
////                UserDetailEntity info = getItem(position);
////                if (info == null || info.getMark() == null) return;
////                FavHolder favHolder = (FavHolder) holder;
////                favHolder.mFavoriteView.initAdapter(0);
////                favHolder.mFavoriteView.setData(info.getMark());
//                break;
//            }
//            case UserDetailEntity.ViewType.FAVORITE: {
////                UserDetailEntity info = getItem(position);
////                if (info == null || info.getHobby() == null) return;
////                FavHolder favHolder = (FavHolder) holder;
////                favHolder.mFavoriteView.initAdapter(0);
////                favHolder.mFavoriteView.setData(info.getHobby());
//                break;
//            }
//            case UserDetailEntity.ViewType.ALBUM: {
//                UserDetailEntity info = getItem(position);
//                if (info == null || info.getAlbum() == null) return;
//                AlumHolder alumHolder = (AlumHolder) holder;
//                alumHolder.alumView.setOnItemClickListener(this);
//                alumHolder.alumView.initAdapter(4);
//                mInfo = info.getAlbum();
//                alumHolder.alumView.setData(info.getAlbum());
//                break;
//            }
//            case UserDetailEntity.ViewType.BASE_INFO: {
//                BaseInfoHolder baseInfoHolder = (BaseInfoHolder) holder;
//                List<String> mList = new ArrayList<>();
//                mList.add("出生日期 / 1989-11-15");
//                mList.add("星座 / 金牛座");
//                mList.add("身高 / 158");
//                mList.add("年收入 / 10w-20w");
//                mList.add("工作生活在 / 廊坊");
//                TagBaseAdapter mAdapter = new TagBaseAdapter(mContext, mList);
//                baseInfoHolder.mTagLayout.setAdapter(mAdapter);
//                break;
//            }
//        }
//
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (getItem(position) == null)
//            return -1;
//        return getItem(position).getViewType();
//
//    }
//
//    public UserDetailEntity getItem(int position) {
//        if (position < 0 || position >= mInfoList.size())
//            return null;
//        return mInfoList.get(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mInfoList != null ? mInfoList.size() : 0;
//    }
//
//    @Override
//    public void onItemClick(int position, PhotoInfo data) {
//        if (mInfo == null) return;
//        String[] arr = new String[mInfo.size()];
//        for (int i = 0; i < mInfo.size(); i++) {
//            arr[i] = mInfo.get(i).getPhoto_url();
//        }
//        ShowPicActivity.showPictures(mContext, arr, position);
//    }
//
//    private static class TopInfoHolder extends RecyclerView.ViewHolder {
//        private TextView mName;
//
//        public TopInfoHolder(View itemView) {
//            super(itemView);
//        }
//    }
//
//    private static class IntroduceHolder extends RecyclerView.ViewHolder {
//        private TextView mName;
//
//        public IntroduceHolder(View itemView) {
//            super(itemView);
//            mName = (TextView) itemView.findViewById(R.id.introduce);
//        }
//    }
//
//    private static class FavHolder extends RecyclerView.ViewHolder {
//        private FavoriteView mFavoriteView;
//
//        FavHolder(View itemView) {
//            super(itemView);
//            mFavoriteView = (FavoriteView) itemView.findViewById(R.id.favorite_layout);
//        }
//    }
//
//    private static class AlumHolder extends RecyclerView.ViewHolder {
//        private AlumView alumView;
//
//        public AlumHolder(View itemView) {
//            super(itemView);
//            alumView = (AlumView) itemView.findViewById(R.id.alum_layout);
//        }
//    }
//
//    private class BaseInfoHolder extends RecyclerView.ViewHolder {
//        TagCloudLayout mTagLayout;
//
//        public BaseInfoHolder(View itemView) {
//            super(itemView);
//            mTagLayout = (TagCloudLayout) itemView.findViewById(R.id.tab_container);
//        }
//    }
}
