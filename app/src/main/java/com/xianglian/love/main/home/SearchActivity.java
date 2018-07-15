package com.xianglian.love.main.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.hawk.Hawk;
import com.xianglian.love.BaseEditUserInfoActivity;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.config.Keys;
import com.xianglian.love.user.adapter.UserInfoEditAdapter;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.utils.UserUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by wl on 2017/8/4.
 * 条件筛选
 *
 * 省市联动 先开二级
 */
public class SearchActivity extends BaseEditUserInfoActivity implements
        BaseQuickAdapter.OnItemClickListener, View.OnClickListener {

    @InjectView(R.id.save)
    TextView mSaveView;

    @InjectView(R.id.reset)
    TextView mResetView;

    private UserInfoEditAdapter mAdapter;

    private List<ItemInfo> mItemInfo;

    public static Intent getIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity);
        setupCommonTitle(getString(R.string.selected));
        ButterKnife.inject(this);
        setupRecyclerView();
        mSaveView.setOnClickListener(this);
        mResetView.setOnClickListener(this);
    }

    private List<ItemInfo> getData() {
        List<ItemInfo> data = new ArrayList<>();

        //年龄
        data.add(getInfo(getString(R.string.age), ItemInfo.Type.AGE, UserUtils.getAge(), UserUtils.getSubAge(this)));

        //身高
        data.add(getInfo(getString(R.string.height), ItemInfo.Type.HEIGHT, UserUtils.getHighData(),
                UserUtils.getSubHeight(this)));

        //居住地
        data.add(getInfo(getString(R.string.apartment), ItemInfo.Type.APARTMENT, null));

        //家乡
        data.add(getInfo(getString(R.string.home_town), ItemInfo.Type.HOMETOWN, null));

        //学历
        data.add(getInfo(getString(R.string.education), ItemInfo.Type.EDUCATION, UserUtils.getEduData(this), UserUtils.getSubEdu(this)));

        //收入
//        data.add(getInfo(getString(R.string.income), ItemInfo.Type.INCOME, UserUtils.getComingData(this)));

        return data;

    }

    @Override
    public void onSelectComplete(int options1, int option2, int options3, Date date, View v) {
        String text = null;
        switch (mEntity.getType()) {
            case ItemInfo.Type.AGE:
                text = dealAge(options1, option2);
                mEntity.setMin_age(mItem.getMin_age());
                mEntity.setMax_age(mItem.getMax_age());
                break;
            case ItemInfo.Type.HEIGHT:
                text = dealHeight(options1, option2);
                mEntity.setMin_height(mItem.getMin_height());
                mEntity.setMax_height(mItem.getMax_height());
                break;
            case ItemInfo.Type.EDUCATION:
                text = dealEdu(options1, option2);
                mEntity.setMin_education(mItem.getMin_education());
                mEntity.setMax_education(mItem.getMax_education());
                break;
            case ItemInfo.Type.HOMETOWN:
                text = dealRegion(options1, option2, options3, false);
                mEntity.setBorn_area_code(mItem.getBorn_area_code());
                break;
            case ItemInfo.Type.APARTMENT:
                text = dealRegion(options1, option2, options3, false);
                mEntity.setWork_area_code(mItem.getWork_area_code());
                break;
        }
        mEntity.setRightText(text);
        mAdapter.notifyDataSetChanged();
    }

    public void setupRecyclerView() {
        super.setupRecyclerView();
        mItemInfo = Hawk.get(Keys.SEARCH_INFO_LIST, mItemInfo);
        if (mItemInfo == null) mItemInfo = new ArrayList<>();
        mAdapter = new UserInfoEditAdapter(this, mItemInfo);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        if (mItemInfo.isEmpty()) {
            addData();
        }
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
                if (!mItemInfo.isEmpty()) {
                    for (ItemInfo item : mItemInfo) {
                        if (item == null) continue;
                        item.setRightText(null);
                        item.setMin_age(null);
                        item.setMax_age(null);
                        item.setMin_height(null);
                        item.setMax_height(null);
                        item.setMin_education(-1);
                        item.setMax_education(-1);
                        item.setWork_area_code(null);
                        item.setBorn_area_code(null);
                    }
                    Hawk.put(Keys.SEARCH_INFO_LIST, null);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.save:
                Hawk.put(Keys.SEARCH_INFO_LIST, mItemInfo);
                Intent intent = new Intent();
                intent.putExtra(Config.EXTRA_SEARCH_TYPE, 1);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    public ItemInfo getInfo(String text, int type, List<String> list) {
        return getInfo(text, type, list, null);
    }

    public ItemInfo getInfo(String text, int type, List<String> list, List<List<String>> list2) {
        return getInfo(text, null, type, list, list2);
    }

    /**
     * @param type 0默认 1：带5dp的分割线
     */
    public ItemInfo getInfo(String text, String rightText, int type, List<String> list,
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
        if (ItemInfo.ViewType.PICK_SELECT == mEntity.getViewType()) {
            switch (mEntity.getType()) {
                //二级
                case ItemInfo.Type.HEIGHT:
                case ItemInfo.Type.EDUCATION:
                case ItemInfo.Type.AGE:
                    pvCustomOptions.setPicker(mEntity.getItems(), mEntity.getSubItems());//添加数据
                    pvCustomOptions.show();
                    break;
                case ItemInfo.Type.HOMETOWN:
                case ItemInfo.Type.APARTMENT: {
                    showRegion(false);
                    break;
                }
                //一级
                case ItemInfo.Type.INCOME:
                case ItemInfo.Type.PROFESSION: {
                    pvCustomOptions.setPicker(mEntity.getItems());//添加数据
                    pvCustomOptions.show();
                    break;
                }
            }
        }
    }
}
