
package com.wl.lianba.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.wl.lianba.R;
import com.wl.lianba.model.RegionCityGsonModel;
import com.wl.lianba.model.RegionGsonModel;
import com.wl.lianba.utils.AppSharePreferences;
import com.wl.lianba.wheel.BaseDialog;
import com.wl.lianba.wheel.adapters.AbstractWheelTextAdapter;
import com.wl.lianba.wheel.widget.OnWheelChangedListener;
import com.wl.lianba.wheel.widget.WheelView;

import java.util.List;

public abstract class LocationSettingDialog extends BaseDialog implements View.OnClickListener {

    private WheelView mProvince;

    private WheelView mCity;

    private List<RegionGsonModel> mData;

    private ProvinceAdapter mProvinceAdapter;

    private CityAdapter mCityAdapter;

    private OnWheelChangedListener onWheelChangedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            mCityAdapter.setData(mData.get(newValue).list);
            mCity.invalidateWheel(true);
            mCity.setCurrentItem(0, true);
        }
    };

    public LocationSettingDialog(Context context, List<RegionGsonModel> data) {
        super(context);
        this.mData = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_location_wheel);
        mProvince = (WheelView) findViewById(R.id.province);
        mCity = (WheelView) findViewById(R.id.city);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.confirm).setOnClickListener(this);
        mProvinceAdapter = new ProvinceAdapter(getContext(), mData);
        mCityAdapter = new CityAdapter(getContext(), mData.get(0).list);

        mProvince.setViewAdapter(mProvinceAdapter);
        mCity.setViewAdapter(mCityAdapter);

        mProvince.setVisibleItems(5);
        mCity.setVisibleItems(5);

        mProvince.addChangingListener(onWheelChangedListener);

        mProvince.post(new Runnable() {
            @Override
            public void run() {
                int position = AppSharePreferences.getRegionPosition(getContext());
                if (position > 0) {
                    for (int i = 0, j = mData.size(); i < j; i++) {
                        for (int a = 0, b = mData.get(i).list.size(); a < b; a++) {
                            if (mData.get(i).list.get(a).id == position) {
                                mProvince.setCurrentItem(i, true);
                                mCityAdapter.setData(mData.get(i).list);
                                mCity.setCurrentItem(a, true);
                            }
                        }
                    }
                }
            }
        });
        resetWidth();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                cancel();
                RegionGsonModel model = mData.get(mProvince.getCurrentItem());
                String region = model.province + " " + model.list.get(mCity.getCurrentItem()).city;
                onConfirm(region, model.list.get(mCity.getCurrentItem()).id);
                break;
            case R.id.cancel:
                cancel();
                break;
        }
    }

    public abstract void onConfirm(String region, int position);

    public class ProvinceAdapter extends AbstractWheelTextAdapter {
        private List<RegionGsonModel> data;

        public ProvinceAdapter(Context context, List<RegionGsonModel> data) {
            super(context, R.layout.item_location_wheel);
            this.data = data;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return data.get(index).province;
        }

        @Override
        public int getItemsCount() {
            return data == null ? 0 : data.size();
        }
    }

    public class CityAdapter extends AbstractWheelTextAdapter {
        private List<RegionCityGsonModel> data;

        public CityAdapter(Context context, List<RegionCityGsonModel> data) {
            super(context, R.layout.item_location_wheel);
            this.data = data;
        }

        public void setData(List<RegionCityGsonModel> data) {
            this.data = data;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return data.get(index).city;
        }

        @Override
        public int getItemsCount() {
            return data == null ? 0 : data.size();
        }
    }

}
