package com.xianglian.love;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.xianglian.love.model.JsonBean;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.utils.LoadRegionTask;
import com.xianglian.love.utils.UserUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wanglong on 17/9/2.
 * 用户编辑-基类
 */

public abstract class BaseEditUserInfoActivity extends BaseListActivity {

    public ItemInfo mEntity;

    public ItemInfo mItem;

    public OptionsPickerView pvCustomOptions;

    public TimePickerView pvCustomTime;

    public List<JsonBean> mOptions1Items;

    public List<List<JsonBean.CityBean>> mOptions2Items;

    public List<List<List<JsonBean.CityBean>>> mOptions3Items;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = new ItemInfo();
        initCustomOptionPicker();
        initCustomTimePicker();
    }

    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                onSelectComplete(options1, option2, options3, null, v);
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

    private void initCustomTimePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                onSelectComplete(-1, -1, -1, date, v);
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time_options, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final Button tvSubmit = (Button) v.findViewById(R.id.tv_finish);
                        Button ivCancel = (Button) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                                pvCustomTime.returnData();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .isDialog(true)
                .build();

    }

    @NonNull
    public String dealAge(int options1, int option2) {
        String minAge = UserUtils.getAge().get(options1);
        String maxAge = UserUtils.getSubAge(this).get(options1).get(option2);
        mItem.min_age = minAge;
        mItem.max_age = maxAge;
        return minAge + "-" + maxAge;
    }

    @NonNull
    public String dealHeight(int options1, int option2) {
        String min = UserUtils.getHighData().get(options1);
        String max = UserUtils.getSubHeight(this).get(options1).get(option2);
        mItem.min_height = min;
        mItem.max_height = max;
        return min + "-" + max;
    }

    @NonNull
    public String dealEdu(int options1, int option2) {
        String min = UserUtils.getEduData(this).get(options1);
        String max = UserUtils.getSubEdu(this).get(options1).get(option2);
        return min + "-" + max;
    }

    @NonNull
    public String dealRegion(int options1, int option2, int option3, boolean hasOption3) {
        String one = mOptions1Items.get(options1).getName();
        JsonBean.CityBean cityBean = mOptions2Items.get(options1).get(option2);
        JsonBean.CityBean city2Bean = mOptions3Items.get(options1).get(option2).get(option3);
        String two = cityBean.getName();
        String three =city2Bean.getName();
        switch (mEntity.getType()) {
            case ItemInfo.Type.HOMETOWN:
                mItem.born_area_code = !hasOption3 ? cityBean.getCode() : city2Bean.getCode();
                break;
            case ItemInfo.Type.APARTMENT:
                mItem.work_area_code = !hasOption3 ? cityBean.getCode() : city2Bean.getCode();
                break;
        }
        if (hasOption3)
            return one + "/" + two + "/" + three;
        return one + "/" + two;
    }

    public void showRegion(final boolean hasOptions3) {
        new LoadRegionTask(this, new LoadRegionTask.LoadFileListener() {
            @Override
            public void onRegionResult(List<JsonBean> options1Items,
                                       List<List<JsonBean.CityBean>> options2Items,
                                       List<List<List<JsonBean.CityBean>>> options3Items) {
                mOptions1Items = options1Items;
                mOptions2Items = options2Items;
                mOptions3Items = options3Items;
                if (hasOptions3) {
                    pvCustomOptions.setPicker(options1Items, options2Items, options3Items);
                } else {
                    pvCustomOptions.setPicker(options1Items, options2Items);
                }
                pvCustomOptions.show();
            }
        }).execute();
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

    public abstract void onSelectComplete(int options1, int option2, int options3, Date date, View v);
}
