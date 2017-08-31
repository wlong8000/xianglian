package com.xianglian.love.main.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;


import com.xianglian.love.BaseActivity;
import com.xianglian.love.R;
import com.xianglian.love.dialog.EditDialog;
import com.xianglian.love.dialog.FirstChooseDialog;
import com.xianglian.love.dialog.LocationSettingDialog;
import com.xianglian.love.main.home.been.PersonInfo;
import com.xianglian.love.main.me.adapter.MyInfoAdapter;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.CommonLinearLayoutManager;
import com.xianglian.love.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 基本资料&择偶要求
 */
public class BaseInfoActivity extends BaseActivity {

    private CommonLinearLayoutManager mLayoutManager;

    private MyRecyclerView mRecyclerView;

    private List<PersonInfo> mEntities;

    private MyInfoAdapter mAdapter;

    private ItemInfo mEntity;

    private int mType = -1;

    private PersonInfo mInfo;

//    private PersonInfo getInfo() {
//        if (mInfo == null)
////            mInfo = AppUtils.getOwnerInfo(this);
//        return mInfo;
//    }


    private View.OnClickListener mItemClickListener = new View.OnClickListener() {
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
                    case ItemInfo.MyInfoType.RANKING:
                    case ItemInfo.MyInfoType.HAS_CHILD:
                    case ItemInfo.MyInfoType.WEIGHT:
                    case ItemInfo.MyInfoType.HEIGHT: {
                        showBottomDialog(mEntity);
                        break;
                    }
                    case ItemInfo.MyInfoType.NICK_NAME: {
                        showEditDialog(mEntity);
                        break;
                    }
                }
            } else if (ItemInfo.ViewType.COMPLETE == mEntity.getViewType()) {
                dialogShow();
//                getInfo().update(AppUtils.getObjectId(BaseInfoActivity.this), new UpdateListener() {
//                    @Override
//                    public void done(BmobException e) {
//                        dialogDisMiss();
//                        if (e == null) {
//                            toast(R.string.save_success);
//                            ACache.get(BaseInfoActivity.this).put(Config.SAVE_USER_KEY, getInfo());
//                            finish();
//                        } else {
//                            toast(R.string.save_fail);
//                        }
//                    }
//                });
            }

        }
    };

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, BaseInfoActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_info_fragment);
        setupCommonTitle(getString(R.string.base_info));
        init();
    }

    private void init() {
        mEntities = new ArrayList<>();
        mAdapter = new MyInfoAdapter(this, mItemClickListener);
        this.setupViews();

    }

    private void setupViews() {
        mRecyclerView = (MyRecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new CommonLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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
//                mAdapter.getInfo().addAll(getData());
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    /**
     * 编辑器
     *
     * @param entity
     */
    private void showEditDialog(final ItemInfo entity) {
        if (entity == null) return;
        EditDialog dialog = new EditDialog(this) {
            @Override
            public void onConfirm(String data) {
                entity.setRightText(data);
                mAdapter.notifyDataSetChanged();
//                getInfo().setNick_name(data);
            }
        };
        dialog.show();
    }

    /**
     * 单项选择
     *
     * @param entity
     */
    private void showBottomDialog(final ItemInfo entity) {
        if (entity == null || entity.getItems() == null) return;
        FirstChooseDialog dialog = new FirstChooseDialog(this, entity.getItems()) {
            @Override
            public void onConfirm(String data) {
                entity.setRightText(data);
                mAdapter.notifyDataSetChanged();
//                saveSingleDate(data, entity.getType());
            }
        };
        dialog.show();
    }

//    private void saveSingleDate(String data, int type) {
//        switch (type) {
//            case ItemInfo.MyInfoType.INCOME: {
//                getInfo().setIncome(data);
//                break;
//            }
//            case ItemInfo.MyInfoType.EDUCATION: {
//                getInfo().setEducation(data);
//                break;
//            }
//            case ItemInfo.MyInfoType.PROFESSION: {
//                getInfo().setJobs(data);
//                break;
//            }
//            case ItemInfo.MyInfoType.NATION: {
//                getInfo().setNative_place(data);
//                break;
//            }
//            case ItemInfo.MyInfoType.MARRY_STATE: {
//                getInfo().setMarryState(data);
//                break;
//            }
//            case ItemInfo.MyInfoType.WEIGHT: {
//                getInfo().setWeight(Integer.parseInt(data));
//                break;
//            }
//            case ItemInfo.MyInfoType.HEIGHT: {
//                getInfo().setHeight(Integer.parseInt(data));
//                break;
//            }
//        }
//    }

    /**
     * 时间选择
     */
    private void showDateDialog(final ItemInfo entity) {
//        Util.alertTimerPicker(this, TimePickerView.Type.YEAR_MONTH_DAY, "yyyy-MM-dd", new Util.TimerPickerCallBack() {
//            @Override
//            public void onTimeSelect(String date) {
//                if (TextUtils.isEmpty(date)) return;
//                entity.setRightText(date);
//                mAdapter.notifyDataSetChanged();
//                getInfo().setBirthday(date);
//            }
//        });
    }

    /**
     * 多项选择
     *
     * @param entity
     */
    private void showOptions(final ItemInfo entity) {
        LocationSettingDialog dialog = new LocationSettingDialog(this,
                AppUtils.getRegionFromCache(this)) {
            @Override
            public void onConfirm(String region, int position) {
                entity.setRightText(region);
                mAdapter.notifyDataSetChanged();
                switch (entity.getType()) {
                    case ItemInfo.MyInfoType.HOMETOWN: {
//                        getInfo().setNative_place(region);
                        break;
                    }
                    case ItemInfo.MyInfoType.APARTMENT: {
//                        getInfo().setApartment(region);
                        break;
                    }
                }
            }
        };
        dialog.show();
    }



//    private List<ItemInfo> getData() {
//        List<ItemInfo> data = new ArrayList<>();
//        PersonInfo info = AppUtils.getOwnerInfo(this);
//        //昵称
//        data.add(getInfo(getString(R.string.nick_name), info.getNick_name(), ItemInfo.MyInfoType.NICK_NAME, null));
//
//        //出生日期
//        data.add(getInfo(getString(R.string.birth), info.getBirthday(), ItemInfo.MyInfoType.BIRTHDAY, null));
//
//        //居住地
//        data.add(getInfo(getString(R.string.apartment), info.getApartment(), ItemInfo.MyInfoType.APARTMENT, null));
//
//        //家乡
//        data.add(getInfo(getString(R.string.home_town), info.getNative_place(), ItemInfo.MyInfoType.HOMETOWN, null));
//
//        //身高
//        data.add(getInfo(getString(R.string.height), info.getHeight() > 0 ? info.getHeight() + "" :
//                null, ItemInfo.MyInfoType.HEIGHT, UserUtils.getHighData()));
//
//        //学历
//        data.add(getInfo(getString(R.string.education), info.getEducation(), ItemInfo.MyInfoType.EDUCATION, UserUtils.getEduData()));
//
//        //职业
//        data.add(getInfo(getString(R.string.profession), info.getJobs(), ItemInfo.MyInfoType.PROFESSION, UserUtils.getProfessionData()));
//
//        //年收入
//        data.add(getInfo(getString(R.string.income), info.getIncome(), ItemInfo.MyInfoType.INCOME, UserUtils.getComingData()));
//
//
//        //婚姻状况
//        data.add(getInfo(getString(R.string.marry_state), info.getMarryState(), ItemInfo.MyInfoType.MARRY_STATE, UserUtils.getMarryState()));
//
//        //体重(单位：kg)
//        data.add(getInfo(getString(R.string.weight), info.getWeight() > 0 ? info.getWeight() + "" :
//                null, ItemInfo.MyInfoType.WEIGHT, UserUtils.getWeight()));
//
//        //完成
//        ItemInfo complete = new ItemInfo();
//        complete.setViewType(ItemInfo.ViewType.COMPLETE);
//        data.add(complete);
//
//        return data;
//
//    }


    public ItemInfo getInfo(String text, int type, ArrayList<String> list) {
        return getInfo(text, null, type, list);
    }

    /**
     * @param text
     * @param rightText
     * @param type      0默认 1：带5dp的分割线
     * @return
     */
    public ItemInfo getInfo(String text, String rightText, int type, ArrayList<String> list) {
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


}
