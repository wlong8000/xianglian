package com.wl.lianba.main.home;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lvfq.pickerview.TimePickerView;
import com.wl.lianba.R;
import com.wl.lianba.dialog.FirstChooseDialog;
import com.wl.lianba.dialog.LocationSettingDialog;
import com.wl.lianba.user.BaseUserInfoActivity;
import com.wl.lianba.user.adapter.UserInfoEditAdapter;
import com.wl.lianba.user.been.ItemInfo;
import com.wl.lianba.utils.AppUtils;
import com.wl.lianba.utils.CommonLinearLayoutManager;
import com.wl.lianba.utils.UserUtils;
import com.wl.lianba.utils.Util;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by wl on 2017/8/4.
 * 条件筛选
 */
public class SelectActivity extends BaseUserInfoActivity implements View.OnClickListener {


    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.reset)
    TextView mResetView;

    @InjectView(R.id.save)
    TextView mSaveView;

    private CommonLinearLayoutManager mLayoutManager;

    private UserInfoEditAdapter mAdapter;

    private ItemInfo mEntity;

    private int mType = -1;

    private View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = mRecyclerView.getChildAdapterPosition(view);
            mEntity = mAdapter.getItem(position);
            if (mEntity == null) return;
            mType = mEntity.getType();
            if (ItemInfo.ViewType.PICK_SELECT == mEntity.getViewType()) {
                switch (mType) {
                    case ItemInfo.Type.BIRTHDAY: {
                        showDateDialog(mEntity);
                        break;
                    }
                    case ItemInfo.Type.HOMETOWN:
                    case ItemInfo.Type.APARTMENT: {
                        showOptions(mEntity);
                        break;
                    }
                    case ItemInfo.Type.INCOME:
                    case ItemInfo.Type.EDUCATION:
                    case ItemInfo.Type.PROFESSION:
                    case ItemInfo.Type.HEIGHT: {
                        showBottomDialog(mEntity);
                        break;
                    }
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity);
        setupCommonTitle(getString(R.string.selected));
        ButterKnife.inject(this);
        initView();
        mSaveView.setOnClickListener(this);
        mResetView.setOnClickListener(this);
    }


    /**
     * 单项选择
     *
     */
    private void showBottomDialog(final ItemInfo entity) {
        if (entity == null || entity.getItems() == null) return;
        FirstChooseDialog dialog = new FirstChooseDialog(this, entity.getItems()) {
            @Override
            public void onConfirm(String data) {
                entity.setRightText(data);
                mAdapter.notifyDataSetChanged();
                saveDate(data);
            }
        };
        dialog.show();
    }

    /**
     * 时间选择
     */
    private void showDateDialog(final ItemInfo entity) {
        Util.alertTimerPicker(this, TimePickerView.Type.YEAR_MONTH_DAY, "yyyy-MM-dd", new Util.TimerPickerCallBack() {
            @Override
            public void onTimeSelect(String date) {
                if (TextUtils.isEmpty(date)) return;
                entity.setRightText(date);
                mAdapter.notifyDataSetChanged();
                saveDate(date);
            }
        });
    }

    /**
     * 多项选择
     *
     */
    private void showOptions(final ItemInfo entity) {
        LocationSettingDialog dialog = new LocationSettingDialog(this,
                AppUtils.getRegionFromCache(this)) {
            @Override
            public void onConfirm(String region, int position) {
                entity.setRightText(region);
                mAdapter.notifyDataSetChanged();
                saveDate(region);
            }
        };
        dialog.show();
    }

    private void saveDate(String data) {
        if (TextUtils.isEmpty(data)) return;
        switch (mType) {
            case ItemInfo.Type.INTRODUCE: {
                break;
            }
            case ItemInfo.Type.BIRTHDAY: {
                mPersonInfo.setBirthday(data);
                break;
            }
            case ItemInfo.Type.APARTMENT: {
                mPersonInfo.setApartment(data);
                break;
            }
            case ItemInfo.Type.HOMETOWN: {
                mPersonInfo.setNative_place(data);
                break;
            }
            case ItemInfo.Type.HEIGHT: {
                try {
                    int height = Integer.parseInt(data);
                    mPersonInfo.setHeight(height);
                } catch (NumberFormatException e) {
                    mPersonInfo.setHeight(0);
                    e.printStackTrace();
                }
                break;
            }
            case ItemInfo.Type.EDUCATION: {
                mPersonInfo.setEducation(data);
                break;
            }
            case ItemInfo.Type.PROFESSION: {
                mPersonInfo.setJobs(data);
                break;
            }
            case ItemInfo.Type.INCOME: {
                mPersonInfo.setIncome(data);
                break;
            }
        }
    }

    private void initView() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mLayoutManager = new CommonLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new UserInfoEditAdapter(this, itemClickListener);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reset:
                break;
            case R.id.save:
                break;
        }
    }

    private List<ItemInfo> getData() {
        List<ItemInfo> data = new ArrayList<>();

        //年龄
        data.add(getInfo(getString(R.string.age), ItemInfo.Type.AGE, null));

        //身高
        data.add(getInfo(getString(R.string.height), ItemInfo.Type.HEIGHT, UserUtils.getHighData()));

        //居住地
        data.add(getInfo(getString(R.string.apartment), ItemInfo.Type.APARTMENT, null));

        //家乡
        data.add(getInfo(getString(R.string.home_town), ItemInfo.Type.HOMETOWN, null));

        //学历
        data.add(getInfo(getString(R.string.education), ItemInfo.Type.EDUCATION, UserUtils.getEduData()));

        //收入
        data.add(getInfo(getString(R.string.income), ItemInfo.Type.INCOME, UserUtils.getComingData()));

        return data;

    }

    public ItemInfo getInfo(String text, int type, ArrayList<String> list) {
        return getInfo(text, null, type, list);
    }

    /**
     * @param type      0默认 1：带5dp的分割线
     */
    public ItemInfo getInfo(String text, String rightText, int type, ArrayList<String> list) {
        if (TextUtils.isEmpty(rightText))
            rightText = getResources().getString(R.string.no_limit);
        ItemInfo data = new ItemInfo();
        data.setViewType(ItemInfo.ViewType.PICK_SELECT);
        data.setText(text);
        data.setRightText(rightText);
        data.setType(type);
        data.setItems(list);
        return data;
    }
}
