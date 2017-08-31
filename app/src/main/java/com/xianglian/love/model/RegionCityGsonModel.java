
package com.xianglian.love.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hujinghui on 15/2/4.
 */
public class RegionCityGsonModel implements Parcelable {

    public static final Creator<RegionCityGsonModel> CREATOR = new Creator<RegionCityGsonModel>() {
        public RegionCityGsonModel createFromParcel(Parcel source) {
            return new RegionCityGsonModel(source);
        }

        public RegionCityGsonModel[] newArray(int size) {
            return new RegionCityGsonModel[size];
        }
    };
    public int id;
    public String city;

    public RegionCityGsonModel(int id, String city) {
        this.id = id;
        this.city = city;
    }

    public RegionCityGsonModel() {
    }

    private RegionCityGsonModel(Parcel in) {
        this.id = in.readInt();
        this.city = in.readString();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.city);
    }

    @Override
    public String toString() {
        return city;
    }
}
