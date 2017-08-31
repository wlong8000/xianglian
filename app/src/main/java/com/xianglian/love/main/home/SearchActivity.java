package com.xianglian.love.main.home;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.dialog.LocationSettingDialog;
import com.xianglian.love.user.BaseUserInfoActivity;
import com.xianglian.love.user.adapter.UserInfoEditAdapter;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.CommonLinearLayoutManager;
import com.xianglian.love.utils.UserUtils;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by wl on 2017/8/4.
 * 条件筛选
 */
public class SearchActivity extends BaseUserInfoActivity implements
        BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.save)
    TextView mSaveView;

    private CommonLinearLayoutManager mLayoutManager;

    private UserInfoEditAdapter mAdapter;

    private ItemInfo mEntity;

    private TimePickerView mPvTime;

    private int mType = -1;

    private OptionsPickerView pvCustomOptions;

    private List<ItemInfo> mItemInfo = new ArrayList<>();

    private ItemInfo mItem;

    public static Intent getIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity);
        setupCommonTitle(getString(R.string.selected));
        ButterKnife.inject(this);
        mItem = new ItemInfo();
        initView();
        mSaveView.setOnClickListener(this);
        initTimePicker();
        initCustomOptionPicker();
    }

    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String text = dealAge(options1, option2);
                mEntity.setRightText(text);
                mAdapter.notifyDataSetChanged();
            }
        }).setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
            @Override
            public void customLayout(View v) {
                final Button tvSubmit = (Button) v.findViewById(R.id.tv_finish);
                Button ivCancel = (Button) v.findViewById(R.id.iv_cancel);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvCustomOptions.dismiss();
                        pvCustomOptions.returnData();
                    }
                });
                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvCustomOptions.dismiss();
                    }
                });
            }
        })
                .isDialog(true)
                .build();

    }

    @NonNull
    private String dealAge(int options1, int option2) {
        String minAge = UserUtils.getAge().get(options1);
        String maxAge = UserUtils.getSubAge().get(options1).get(option2);

        mItem.min_age = minAge;
        mItem.max_age = maxAge;

        return minAge + "-" + maxAge;
    }

    private void initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1950, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 11, 28);
        //时间选择器
        mPvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (date == null) return;
                mEntity.setRightText(getTime(date));
                mAdapter.notifyDataSetChanged();
//                saveDate(getTime(date));

            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(date);
    }

    /**
     * 多项选择
     */
    private void showOptions(final ItemInfo entity) {
        LocationSettingDialog dialog = new LocationSettingDialog(this,
                AppUtils.getRegionFromCache(this)) {
            @Override
            public void onConfirm(String region, int position) {
                entity.setRightText(region);
                mAdapter.notifyDataSetChanged();
            }
        };
        dialog.show();
    }

    private void initView() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mLayoutManager = new CommonLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new UserInfoEditAdapter(this, mItemInfo);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        addData();
    }

    private void addData() {
        addDataByType(ItemInfo.ViewType.PICK_SELECT);
    }

    private void addDataByType(int type) {
        switch (type) {
            case ItemInfo.ViewType.PICK_SELECT: {
                mItemInfo.clear();
                mItemInfo.addAll(getData());
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
                Intent intent = new Intent();
                intent.putExtra(Config.EXTRA_SEARCH_ENTITY, mItem);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private List<ItemInfo> getData() {
        List<ItemInfo> data = new ArrayList<>();

        //年龄
        data.add(getInfo(getString(R.string.age), ItemInfo.Type.AGE, UserUtils.getAge(), UserUtils.getSubAge()));

        //身高
        data.add(getInfo(getString(R.string.height), ItemInfo.Type.HEIGHT, UserUtils.getHighData()));

        //居住地
        data.add(getInfo(getString(R.string.apartment), ItemInfo.Type.APARTMENT, null));

        //家乡
        data.add(getInfo(getString(R.string.home_town), ItemInfo.Type.HOMETOWN, null));

        //学历
        data.add(getInfo(getString(R.string.education), ItemInfo.Type.EDUCATION, UserUtils.getEduData(this)));

        //收入
        data.add(getInfo(getString(R.string.income), ItemInfo.Type.INCOME, UserUtils.getComingData()));

        return data;

    }

    public ItemInfo getInfo(String text, int type, ArrayList<String> list) {
        return getInfo(text, type, list, null);
    }

    public ItemInfo getInfo(String text, int type, ArrayList<String> list, List<List<String>> list2) {
        return getInfo(text, null, type, list, list2);
    }

    /**
     * @param type 0默认 1：带5dp的分割线
     */
    public ItemInfo getInfo(String text, String rightText, int type, ArrayList<String> list,
                            List<List<String>> list2) {
        if (TextUtils.isEmpty(rightText))
            rightText = getResources().getString(R.string.no_limit);
        ItemInfo data = new ItemInfo();
        data.setViewType(ItemInfo.ViewType.PICK_SELECT);
        data.setText(text);
        data.setRightText(rightText);
        data.setType(type);
        data.setItems(list);
        data.setSubItems(list2);
        return data;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mEntity = mAdapter.getItem(position);
        if (mEntity == null) return;
        mType = mEntity.getType();
        if (ItemInfo.ViewType.PICK_SELECT == mEntity.getViewType()) {
            switch (mType) {
                case ItemInfo.Type.BIRTHDAY: {
                    mPvTime.show();
                    break;
                }
                case ItemInfo.Type.AGE:
                    pvCustomOptions.setPicker(mEntity.getItems(), mEntity.getSubItems());//添加数据
                    pvCustomOptions.show();
                    break;
                case ItemInfo.Type.HOMETOWN:
                case ItemInfo.Type.APARTMENT: {
                    showOptions(mEntity);
                    break;
                }
                case ItemInfo.Type.INCOME:
                case ItemInfo.Type.EDUCATION:
                case ItemInfo.Type.PROFESSION:
                case ItemInfo.Type.HEIGHT: {
                    pvCustomOptions.setPicker(mEntity.getItems());//添加数据
                    pvCustomOptions.show();
                    break;
                }
            }
        }
    }
}
