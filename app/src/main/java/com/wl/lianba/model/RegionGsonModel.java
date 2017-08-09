
package com.wl.lianba.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hujinghui on 15/2/4.
 */
public class RegionGsonModel implements Parcelable {

    public static final Creator<RegionGsonModel> CREATOR = new Creator<RegionGsonModel>() {
        public RegionGsonModel createFromParcel(Parcel source) {
            return new RegionGsonModel(source);
        }

        public RegionGsonModel[] newArray(int size) {
            return new RegionGsonModel[size];
        }
    };
    public String province;
    public List<RegionCityGsonModel> list;

    public RegionGsonModel() {
    }

    private RegionGsonModel(Parcel in) {
        this.province = in.readString();
        in.readTypedList(list, RegionCityGsonModel.CREATOR);
    }

    public List<RegionCityGsonModel> getList() {
        return list;
    }

    public void setList(List<RegionCityGsonModel> list) {
        this.list = list;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void addData(RegionCityGsonModel model) {
        if (list == null)
            list = new ArrayList<>();
        list.add(model);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.province);
        dest.writeTypedList(list);
    }

    @Override
    public String toString() {
        return province;
    }
}
