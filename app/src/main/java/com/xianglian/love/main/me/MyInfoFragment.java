
package com.xianglian.love.main.me;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.xianglian.love.BaseListFragment;
import com.xianglian.love.R;
import com.xianglian.love.dialog.FirstChooseDialog;
import com.xianglian.love.dialog.LocationSettingDialog;
import com.xianglian.love.main.home.been.PersonInfo;
import com.xianglian.love.main.me.adapter.MyInfoAdapter;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.CommonLinearLayoutManager;
import com.xianglian.love.utils.UserUtils;
import com.xianglian.love.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 基本资料
 */
public class MyInfoFragment extends BaseListFragment implements OnClickListener{
    private CommonLinearLayoutManager mLayoutManager;

    private MyRecyclerView mRecyclerView;

    private List<PersonInfo> mEntities;

    private MyInfoAdapter mAdapter;

    private ItemInfo mEntity;

    private int mType = -1;


    private OnClickListener mItemClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mEntities == null) return;
            int position = mRecyclerView.getChildAdapterPosition(v);
            mEntity = mAdapter.getItem(position);
            if (mEntity == null) return;
            mType = mEntity.getType();
            if (ItemInfo.ViewType.PICK_SELECT == mEntity.getViewType()) {
                switch (mType) {
                    case ItemInfo.MyInfoType.BIRTHDAY: {
                        showDateDialog(mEntity);
                        break;
                    }
                    case ItemInfo.MyInfoType.HOMETOWN:
                    case ItemInfo.MyInfoType.APARTMENT: {
                        showOptions(mEntity);
                        break;
                    }
                    case ItemInfo.MyInfoType.INCOME:
                    case ItemInfo.MyInfoType.EDUCATION:
                    case ItemInfo.MyInfoType.PROFESSION:
                    case ItemInfo.MyInfoType.HOPE_MARRY:
                    case ItemInfo.MyInfoType.NATION:
                    case ItemInfo.MyInfoType.MARRY_STATE:
                    case ItemInfo.MyInfoType.BROTHER_STATE:
                    case ItemInfo.MyInfoType.HAS_CHILD:
                    case ItemInfo.MyInfoType.WEIGHT:
                    case ItemInfo.MyInfoType.HEIGHT: {
                        showBottomDialog(mEntity);
                        break;
                    }
                }
            }

        }
    };

    public static MyInfoFragment newInstance() {
        MyInfoFragment fragment = new MyInfoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_info_fragment, null);
        mEntities = new ArrayList<>();
        mAdapter = new MyInfoAdapter(getActivity(), mItemClickListener);
        this.setupViews(view);
        return view;
    }

    private void setupViews(View view) {
        mRecyclerView = (MyRecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new CommonLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        addData();
    }

    private void addData() {
        addDataByType(ItemInfo.ViewType.PICK_SELECT);

    }

    private void addDataByType(int type) {
        switch (type) {
            case ItemInfo.ViewType.PICK_SELECT: {
                mAdapter.getInfo().addAll(getData());
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 单项选择
     *
     * @param entity
     */
    private void showBottomDialog(final ItemInfo entity) {
        if (entity == null || entity.getItems() == null) return;
        FirstChooseDialog dialog = new FirstChooseDialog(getContext(), entity.getItems()) {
            @Override
            public void onConfirm(String data) {
                entity.setRightText(data);
                mAdapter.notifyDataSetChanged();
//                saveDate(data);
            }
        };
        dialog.show();
    }

    /**
     * 时间选择
     */
    private void showDateDialog(final ItemInfo entity) {
//        Util.alertTimerPicker(getContext(), TimePickerView.Type.YEAR_MONTH_DAY, "yyyy-MM-dd", new Util.TimerPickerCallBack() {
//            @Override
//            public void onTimeSelect(String date) {
//                if (TextUtils.isEmpty(date)) return;
//                entity.setRightText(date);
//                mAdapter.notifyDataSetChanged();
////                saveDate(date);
//            }
//        });
    }

    /**
     * 多项选择
     *
     * @param entity
     */
    private void showOptions(final ItemInfo entity) {
        LocationSettingDialog dialog = new LocationSettingDialog(getContext(),
                AppUtils.getRegionFromCache(getContext())) {
            @Override
            public void onConfirm(String region, int position) {
                entity.setRightText(region);
                mAdapter.notifyDataSetChanged();
//                saveDate(region);
            }
        };
        dialog.show();
    }



    private List<ItemInfo> getData() {
        List<ItemInfo> data = new ArrayList<>();
        //昵称
        data.add(getInfo(getString(R.string.nick_name), ItemInfo.MyInfoType.NICK_NAME, null));

        //出生日期
        data.add(getInfo(getString(R.string.birth), ItemInfo.MyInfoType.BIRTHDAY, null));

        //居住地
        data.add(getInfo(getString(R.string.apartment), ItemInfo.MyInfoType.APARTMENT, null));

        //家乡
        data.add(getInfo(getString(R.string.home_town), ItemInfo.MyInfoType.HOMETOWN, null));

        //身高
        data.add(getInfo(getString(R.string.height), ItemInfo.MyInfoType.HEIGHT, UserUtils.getHighData()));

        //学历
        data.add(getInfo(getString(R.string.education), ItemInfo.MyInfoType.EDUCATION, UserUtils.getEduList()));

        //职业
        data.add(getInfo(getString(R.string.profession), ItemInfo.MyInfoType.PROFESSION, UserUtils.getProfessions()));

        //年收入
        data.add(getInfo(getString(R.string.income), ItemInfo.MyInfoType.INCOME, UserUtils.getInComingList()));


        //期望结婚时间
        data.add(getInfo(getString(R.string.hope_marry), ItemInfo.MyInfoType.HOPE_MARRY, UserUtils.getHopeMarries()));

        //民族
        data.add(getInfo(getString(R.string.nation), ItemInfo.MyInfoType.NATION, UserUtils.getNations()));

        //婚姻状况
        data.add(getInfo(getString(R.string.marry_state), ItemInfo.MyInfoType.MARRY_STATE, UserUtils.getMarryStates()));

        //家中排行
        data.add(getInfo(getString(R.string.raking), ItemInfo.MyInfoType.BROTHER_STATE, UserUtils.getBrotherStates()));

        //有无子女
        data.add(getInfo(getString(R.string.has_child), ItemInfo.MyInfoType.HAS_CHILD, UserUtils.getRight()));

        //体重(单位：kg)
        data.add(getInfo(getString(R.string.weight), ItemInfo.MyInfoType.WEIGHT, UserUtils.getWeight()));

        //完成
        ItemInfo complete = new ItemInfo();
        complete.setViewType(ItemInfo.ViewType.COMPLETE);
        data.add(complete);

        return data;

    }


    public ItemInfo getInfo(String text, int type, List<String> list) {
        return getInfo(text, null, type, list);
    }

    /**
     * @param text
     * @param rightText
     * @param type      0默认 1：带5dp的分割线
     * @return
     */
    public ItemInfo getInfo(String text, String rightText, int type, List<String> list) {
        if (TextUtils.isEmpty(rightText))
            rightText = getResources().getString(R.string.please_select);
        ItemInfo data = new ItemInfo();
        data.setViewType(ItemInfo.ViewType.PICK_SELECT);
        data.setText(text);
        data.setRightText(rightText);
        data.setType(type);
        data.setItems(list);
        return data;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mEntities != null) {
            mEntities.clear();
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onRefresh2(boolean refresh) {

    }
}
