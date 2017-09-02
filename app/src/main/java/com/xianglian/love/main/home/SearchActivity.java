package com.xianglian.love.main.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.model.JsonBean;
import com.xianglian.love.user.BaseUserInfoActivity;
import com.xianglian.love.user.adapter.UserInfoEditAdapter;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.utils.CommonLinearLayoutManager;
import com.xianglian.love.utils.LoadRegionTask;
import com.xianglian.love.utils.UserUtils;
import com.xianglian.love.model.JsonBean.CityBean;
import java.util.ArrayList;
import java.util.List;

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
        initCustomOptionPicker();
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
        data.add(getInfo(getString(R.string.income), ItemInfo.Type.INCOME, UserUtils.getComingData(this)));

        return data;

    }

    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String text = null;
                switch (mEntity.getType()) {
                    case ItemInfo.Type.AGE:
                        text = dealAge(options1, option2);
                        break;
                    case ItemInfo.Type.HEIGHT:
                        text = dealHeight(options1, option2);
                        break;
                    case ItemInfo.Type.EDUCATION:
                        text = dealEdu(options1, option2);
                        break;
                }
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
        String maxAge = UserUtils.getSubAge(this).get(options1).get(option2);
        mItem.min_age = minAge;
        mItem.max_age = maxAge;
        return minAge + "-" + maxAge;
    }

    @NonNull
    private String dealHeight(int options1, int option2) {
        String min = UserUtils.getHighData().get(options1);
        String max = UserUtils.getSubHeight(this).get(options1).get(option2);
        mItem.min_height = min;
        mItem.max_height = max;
        return min + "-" + max;
    }

    @NonNull
    private String dealEdu(int options1, int option2) {
        String min = UserUtils.getEduData(this).get(options1);
        String max = UserUtils.getSubEdu(this).get(options1).get(option2);
        return min + "-" + max;
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
                    new LoadRegionTask(this, new LoadRegionTask.LoadFileListener() {
                        @Override
                        public void onRegionResult(List<JsonBean> options1Items,
                                                   List<List<CityBean>> options2Items,
                                                   List<List<List<CityBean>>> options3Items) {
                            pvCustomOptions.setPicker(options1Items, options2Items, options3Items);
                            pvCustomOptions.show();
                        }
                    }).execute();
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
