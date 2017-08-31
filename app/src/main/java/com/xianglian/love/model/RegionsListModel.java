
package com.xianglian.love.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hujinghui on 15/2/7.
 */
public class RegionsListModel implements Parcelable {

    public static final Parcelable.Creator<RegionsListModel> CREATOR = new Parcelable.Creator<RegionsListModel>() {
        public RegionsListModel createFromParcel(Parcel source) {
            return new RegionsListModel(source);
        }

        public RegionsListModel[] newArray(int size) {
            return new RegionsListModel[size];
        }
    };
    public int position;
    public List<RegionGsonModel> regions;

    public RegionsListModel() {
    }

    private RegionsListModel(Parcel in) {
        this.position = in.readInt();
        in.readTypedList(regions, RegionGsonModel.CREATOR);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<RegionGsonModel> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionGsonModel> regions) {
        this.regions = regions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.position);
        dest.writeTypedList(regions);
    }
}
